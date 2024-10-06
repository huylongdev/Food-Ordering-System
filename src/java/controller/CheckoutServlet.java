package controller;

import com.vnpay.common.Config;
import context.BillDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import model.Account;
import model.CartItem;

@WebServlet(name = "CheckoutServlet", urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        try {
            HttpSession session = request.getSession(true);
            CartItem cart = (CartItem) session.getAttribute("cart");
            Account acc = (Account) session.getAttribute("user");
            
            if (acc == null) {
                response.sendRedirect("/OrderingSystem/login");
                return;
            }

            String payment_method = request.getParameter("payment_method");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");

            // Kiểm tra cart
            if (cart != null) {
                BillDAO dao = new BillDAO();
                String payment = "";

                if ("momo".equals(payment_method)) {
                    payment = "MOMO";
                } else if ("vnpay".equals(payment_method)) {
                    payment = "VNPAY";
                } else if ("cod".equals(payment_method)) {
                    payment = "COD";
                }

                int phonenumber = Integer.parseInt(phone);
                dao.addOrder(acc, cart, payment, address, phonenumber);
                session.removeAttribute("cart");
                session.setAttribute("size", 0);

                if ("cod".equals(payment_method)) {
                    response.sendRedirect("/OrderingSystem/");
                } else {
                    model.Bill bill = dao.getBill();
                    int total = Math.round(bill.getTotal());

                    if ("momo".equals(payment_method)) {
                        request.setAttribute("total", total);
                        request.setAttribute("bill", bill);
                        request.getRequestDispatcher("WEB-INF/view/qrcode.jsp").forward(request, response);
                    } else if ("vnpay".equals(payment_method)) {
                        // Xử lý thanh toán VNPAY
                        processVNPAY(request, response, bill);
                    }
                }
            } else {
                response.sendRedirect("/OrderingSystem/");
            }
        } catch (Exception e) {
            e.printStackTrace(); // In ra lỗi cho debug
            request.getRequestDispatcher("WEB-INF/view/404.jsp").forward(request, response);
        }
    }

    private void processVNPAY(HttpServletRequest request, HttpServletResponse response, model.Bill bill) throws IOException {
        String vnp_Version = "2.0.0";
        String vnp_Command = "pay";
        String vnp_OrderInfo = "Thanh toan don hang " + bill.getBill_id();
        String orderType = "billpayment";
        String vnp_TxnRef = bill.getBill_id() + "";
        String vnp_IpAddr = Config.getIpAddress(request);
        String vnp_TmnCode = Config.vnp_TmnCode;
        int amount = Math.round(bill.getTotal()) * 100;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vi");
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        String dateString = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        vnp_Params.put("vnp_CreateDate", dateString);

        // Build data to hash and querystring
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();

        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && fieldValue.length() > 0) {
                hashData.append(fieldName).append('=').append(fieldValue);
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString())).append('=')
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        String queryUrl = query.toString();
        String vnp_SecureHash = Config.Sha256(Config.vnp_HashSecret + hashData.toString());
        queryUrl += "&vnp_SecureHashType=SHA256&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

        response.sendRedirect(paymentUrl);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Object u = session.getAttribute("user");
        if (u != null) {
            request.getRequestDispatcher("WEB-INF/view/checkout.jsp").forward(request, response);
        } else {
            response.sendRedirect("/OrderingSystem/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
