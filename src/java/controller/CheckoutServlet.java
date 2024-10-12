package controller;

import util.Config;
import context.OrderDAO;
import context.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import model.Account;
import model.CartItem;
import model.CartItemDTO;
import model.OrderDTO;
import model.Product;

@WebServlet(name = "CheckoutServlet", urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {

    public CheckoutServlet() {
        super();
        System.out.println("CheckoutServlet initialized!");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        try {
            HttpSession session = request.getSession(true);
            List<CartItemDTO> cartDTO = (List<CartItemDTO>) session.getAttribute("cart");

            Account acc = (Account) session.getAttribute("user");

            if (acc == null) {
                response.sendRedirect("/OrderingSystem/login");
                return;
            }

            String payment_method = request.getParameter("payment_method");
            String address = request.getParameter("address");
            String deliveryOption = request.getParameter("shipping_method");
            Date timePickup = null;
            if ("pickup".equals(deliveryOption)) {
                String timePickupString = request.getParameter("pickup_time");

                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");

                try {
                    timePickup = dateFormat.parse(timePickupString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
            String phone = request.getParameter("phone");

            if (cartDTO != null && !cartDTO.isEmpty()) {
                OrderDAO dao = new OrderDAO();
                String payment = determinePaymentMethod(payment_method);

                String orderID = Config.getRandomNumber(8);

                List<CartItem> cart = new ArrayList<>();
                for (CartItemDTO itemDTO : cartDTO) {
                    Product product = itemDTO.getProduct(); // Ensure this returns a valid Product object
                    CartItem item = new CartItem(product, itemDTO.getQuantity());
                    cart.add(item);
                }

                // Create a temporary order with payment status PENDING and set orderID
//                Order order = dao.createOrder(Integer.parseInt(orderID), acc, cart, payment, address, "PENDING");
                OrderDTO order = dao.createOrder(Integer.parseInt(orderID), acc, cart, payment, address, "PENDING", deliveryOption, timePickup);
                // Handle COD payment
                if ("cod".equals(payment_method)) {
                    // For COD, set payment status to PAID immediately
                    dao.updateOrderPaymentStatus(Integer.parseInt(orderID), "PAID");
                    clearCart(session);
                    response.sendRedirect("/OrderingSystem/");
                } else if ("vnpay".equals(payment_method)) {
                    // Process VNPAY payment and set vnp_TxnRef to orderID
                    if (order != null) {
                        processVNPAY(request, response, order, orderID);
                    } else {
                        request.getRequestDispatcher("WEB-INF/view/error.jsp").forward(request, response);
                    }
                }
            } else {
                response.sendRedirect("/OrderingSystem/");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Consider logging instead of printing stack trace
            request.getRequestDispatcher("WEB-INF/view/404.jsp").forward(request, response);
        }
    }

    private String determinePaymentMethod(String paymentMethod) {
        switch (paymentMethod) {
            case "vnpay":
                return "VNPAY";
            case "cod":
                return "COD";
            default:
                return "";
        }
    }

    private void clearCart(HttpSession session) {
        session.removeAttribute("cart");
        session.setAttribute("size", 0);
    }

    private void processVNPAY(HttpServletRequest request, HttpServletResponse response, OrderDTO order, String orderID) throws IOException {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = (long) (Math.round(order.getTotalAmount()) * 100);

        // Set vnp_TxnRef as the orderID
        String vnp_TxnRef = orderID;
        String vnp_IpAddr = Config.getIpAddress(request);
        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang: " + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = request.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }

        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();

        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && fieldValue.length() > 0) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString())).append('=')
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

        // Redirect to VNPAY payment URL
        response.sendRedirect(paymentUrl);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<CartItemDTO> cartDTO = new ArrayList<>();

        HttpSession session = request.getSession(true);
        ProductDAO pDAO = new ProductDAO();
        String[] selected = request.getParameterValues("isSelected");

        if (selected == null) {
            session.setAttribute("cartStatus", "Choose product to order!");
            response.sendRedirect("cart");

        } else {

            for (String productID : selected) {
                int id;
                try {
                    id = Integer.parseInt(productID);
                } catch (NumberFormatException e) {
                    throw new ServletException("invalid id");
                }
                int quantity = Integer.parseInt(request.getParameter("quantity_" + productID));
                CartItemDTO cDTO = new CartItemDTO();
                cDTO.setProduct(pDAO.getProductByID(id));
                cDTO.setQuantity(quantity);
                cartDTO.add(cDTO);

            }
            session.setAttribute("cart", cartDTO);
            Object u = session.getAttribute("user");
            if (u != null) {
                request.getRequestDispatcher("WEB-INF/view/checkout.jsp").forward(request, response);
            } else {
                response.sendRedirect("/OrderingSystem/login");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Checkout Servlet handles the payment and order process.";
    }
}
