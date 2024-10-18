package controller;

import com.vnpay.common.Config;
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
import java.text.SimpleDateFormat;
import java.util.*;
import model.Account;
import model.CartItem;
import model.CartItemDTO;
import model.OrderDTO;
import model.Product;

@WebServlet(name = "CheckoutServlet", urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {
    // Generate a new PaymentID for this request

    String paymentID = Config.getRandomNumber(8);

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
            List<CartItemDTO> cartItems = (List<CartItemDTO>) session.getAttribute("cart");

            Account acc = (Account) session.getAttribute("user");

            if (acc == null) {
                response.sendRedirect("/OrderingSystem/login");
                return;
            }

            String payment_method = request.getParameter("payment_method");
            String address = request.getParameter("address");
            String deliveryOption = request.getParameter("shipping_method");
            String timePickup = request.getParameter("pickup_time");
            String phone = request.getParameter("phone");

            if (cartItems != null && !cartItems.isEmpty()) {
                OrderDAO orderDAO = new OrderDAO();
                double totalAmount = 0;
                List<OrderDTO> allOrders = new ArrayList<>();

                Map<Integer, List<CartItemDTO>> groupedCartItems = groupCartItemsByShop(cartItems);

                for (Map.Entry<Integer, List<CartItemDTO>> entry : groupedCartItems.entrySet()) {
                    List<CartItemDTO> shopCartItems = entry.getValue();
                    String shopOrderID = Config.getRandomNumber(8);

                    List<CartItem> cartItemsForShop = new ArrayList<>();
                    double shopTotalAmount = 0;

                    for (CartItemDTO itemDTO : shopCartItems) {
                        Product product = itemDTO.getProduct();
                        CartItem cartItem = new CartItem(product, itemDTO.getQuantity());
                        cartItemsForShop.add(cartItem);
                        shopTotalAmount += product.getPrice() * itemDTO.getQuantity();
                    }

                    OrderDTO shopOrder = orderDAO.createOrder(Integer.parseInt(shopOrderID),
                            Integer.parseInt(paymentID), 
                            acc,
                            cartItemsForShop,
                            determinePaymentMethod(payment_method),
                            address,
                            "PENDING",
                            "PENDING",
                            deliveryOption,
                            timePickup);
                    shopOrder.setTotalAmount(shopTotalAmount);
                    allOrders.add(shopOrder);

                    totalAmount += shopTotalAmount;
                }

                if ("cod".equals(payment_method)) {
                    for (OrderDTO order : allOrders) {
                        int paymentID = orderDAO.getPaymentIDByOrderID(order.getOrderId());
                        System.out.println("Updating PaymentID: " + paymentID + " to PAID");
                        orderDAO.updateOrderPaymentStatus(paymentID, "PAID");
                    }
                    clearCart(session);
                    response.sendRedirect("/OrderingSystem/order-history");
                } else if ("vnpay".equals(payment_method)) {
                    if (!allOrders.isEmpty()) {
                        clearCart(session);
                        processVNPAY(request, response, allOrders, totalAmount);
                    } else {
                        request.getRequestDispatcher("WEB-INF/view/error.jsp").forward(request, response);
                    }
                }
            } else {
                response.sendRedirect("/OrderingSystem/");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("WEB-INF/view/404.jsp").forward(request, response);
        }
    }

    private Map<Integer, List<CartItemDTO>> groupCartItemsByShop(List<CartItemDTO> cartItems) {
        Map<Integer, List<CartItemDTO>> groupedItems = new HashMap<>();

        for (CartItemDTO item : cartItems) {
            int shopId = item.getProduct().getShopId();
            groupedItems.computeIfAbsent(shopId, k -> new ArrayList<>()).add(item);
        }

        return groupedItems;
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

    private void processVNPAY(HttpServletRequest request, HttpServletResponse response, List<OrderDTO> allOrders, double totalAmount) throws IOException {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = (long) (Math.round(totalAmount) * 100);

        String vnp_TxnRef = paymentID; // Unique transaction reference
        String vnp_IpAddr = Config.getIpAddress(request);
        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang");
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
            return;
        }

        for (String productID : selected) {
            try {
                int id = Integer.parseInt(productID);
                int quantity = Integer.parseInt(request.getParameter("quantity_" + productID));

                CartItemDTO cDTO = new CartItemDTO();
                cDTO.setProduct(pDAO.getProductByID(id));
                cDTO.setQuantity(quantity);
                cartDTO.add(cDTO);
            } catch (NumberFormatException e) {
                throw new ServletException("Invalid product ID or quantity", e);
            }
        }

        session.setAttribute("cart", cartDTO);
        session.setAttribute("size", cartDTO.size());

        Object user = session.getAttribute("user");
        if (user != null) {
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
        return "Checkout Servlet handles the payment and order process.";
    }
}
