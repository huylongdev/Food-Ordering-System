package controller;

import com.google.gson.JsonObject;
import com.vnpay.common.Config;
import context.OrderDAO;
import context.VNPayBillDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import model.VNPay_Bill;

@WebServlet(name = "RefundServlet", urlPatterns = {"/refund"})
public class RefundServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RefundServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RefundServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int orderID = Integer.parseInt(req.getParameter("orderId"));

            OrderDAO orderDAO = new OrderDAO();
            VNPayBillDAO vnpaybillDAO = new VNPayBillDAO();
            String paymentID = orderDAO.getPaymentIDByOrderID(orderID);
            VNPay_Bill bill = vnpaybillDAO.getBill(paymentID);

            // Prepare parameters for refund
            String vnp_RequestId = Config.getRandomNumber(8);
            String vnp_Version = "2.1.0";
            String vnp_Command = "refund";
            String vnp_TmnCode = Config.vnp_TmnCode;
            String vnp_TransactionType = "02";
            String vnp_TxnRef = String.valueOf(bill.getVnpTxnRef());
            String vnp_Amount = String.valueOf((int)bill.getVnpAmount());
            String vnp_OrderInfo = "Refund transaction Foodie OrderID :" + orderID;
            String vnp_TransactionNo = "";
            String vnp_TransactionDate = bill.getVnpPayDate();
            String vnp_CreateBy = "Tran Phuc Tien";

            // Get current time for CreateDate
            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(cld.getTime());

            String vnp_IpAddr = Config.getIpAddress(req);

            // Build JSON object for the request
            JsonObject vnp_Params = new JsonObject();
            vnp_Params.addProperty("vnp_RequestId", vnp_RequestId);
            vnp_Params.addProperty("vnp_Version", vnp_Version);
            vnp_Params.addProperty("vnp_Command", vnp_Command);
            vnp_Params.addProperty("vnp_TmnCode", vnp_TmnCode);
            vnp_Params.addProperty("vnp_TransactionType", vnp_TransactionType);
            vnp_Params.addProperty("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.addProperty("vnp_Amount", vnp_Amount);
            vnp_Params.addProperty("vnp_OrderInfo", vnp_OrderInfo);
            if (vnp_TransactionNo != null && !vnp_TransactionNo.isEmpty()) {
                vnp_Params.addProperty("vnp_TransactionNo", vnp_TransactionNo);
            }
            vnp_Params.addProperty("vnp_TransactionDate", vnp_TransactionDate);
            vnp_Params.addProperty("vnp_CreateBy", vnp_CreateBy);
            vnp_Params.addProperty("vnp_CreateDate", vnp_CreateDate);
            vnp_Params.addProperty("vnp_IpAddr", vnp_IpAddr);

            // Generate Secure Hash
            String hash_Data = String.join("|", vnp_RequestId, vnp_Version, vnp_Command, vnp_TmnCode,
                    vnp_TransactionType, vnp_TxnRef, vnp_Amount, vnp_TransactionNo, vnp_TransactionDate,
                    vnp_CreateBy, vnp_CreateDate, vnp_IpAddr, vnp_OrderInfo);
            String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hash_Data);
            vnp_Params.addProperty("vnp_SecureHash", vnp_SecureHash);

            // Send request to VNPay
            URL url = new URL(Config.vnp_ApiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.writeBytes(vnp_Params.toString());
                wr.flush();
            }

            int responseCode = con.getResponseCode();
            System.out.println("Sending 'POST' request to URL: " + url);
            System.out.println("Post Data: " + vnp_Params);
            System.out.println("Response Code: " + responseCode);

            // Read response
            StringBuilder responseContent = new StringBuilder();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String output;
                while ((output = in.readLine()) != null) {
                    responseContent.append(output);
                }
            }

            // Log response content
            System.out.println(responseContent.toString());
            //Update refund Status
            orderDAO.updateRefundStatus(paymentID, 1);

            // Return response to client
            response.setContentType("application/json");
            response.getWriter().write(responseContent.toString());

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid order ID format");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred during the refund process");
        }
    }

    @Override
    public String getServletInfo() {
        return "RefundServlet handles refund requests to VNPay.";
    }
}
