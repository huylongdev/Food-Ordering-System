/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.CartItem;
import model.CartItemDTO;
import model.OrderItem;
import model.Product;
import org.apache.catalina.User;

/**
 *
 * @author Lenovo
 */
public class OrderServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet OrderServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Trang OrderServlet nè</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "getOrderFromCart";
        }

        switch (action) {
            case "getOrderFromCart" ->
                getOrderFromCart(request, response);
            case "deleteOrder" ->
                deleteOrder(request, response);
            case "checkVoucher" ->
                checkVoucher(request, response);
            case "placeAnOrder" ->
                placeAnOrder(request, response);
        }
    }

    // load dữ liệu từ cart
    private void getOrderFromCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Kiểm tra session hợp lệ
        if (session == null || session.getAttribute("cart") == null) {
            response.sendRedirect("cart.jsp");  // Điều hướng về trang giỏ hàng nếu không có session hoặc cart
            return;
        }

        // Lấy thông tin giỏ hàng từ session
        List<CartItemDTO> cart = (List<CartItemDTO>) session.getAttribute("cart");
        List<CartItemDTO> selectedItems = new ArrayList<>();

        // Lấy danh sách các productId đã được đánh dấu là isSelected từ request
        String[] selectedProductIds = request.getParameterValues("isSelected");

        if (selectedProductIds != null) {
            // Duyệt qua giỏ hàng và lọc các đối tượng được đánh dấu là isSelected
            for (CartItemDTO item : cart) {
                for (String id : selectedProductIds) {
                    if (Integer.toString(item.getProduct().getProductId()).equals(id)) {
                        selectedItems.add(item);
                        break;  // Dừng vòng lặp nếu tìm thấy sản phẩm được chọn
                    }
                }
            }
        }

        // Chuyển đổi selectedItems từ List<CartItemDTO> thành List<OrderItem>
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItemDTO selectedItem : selectedItems) {
            Product product = selectedItem.getProduct(); // Lấy đối tượng Product từ CartItemDTO
            int quantity = selectedItem.getQuantity();
            double totalPrice = product.getPrice() * quantity; // Tính toán tổng giá

            // Tạo OrderItem từ Product, quantity và totalPrice
            OrderItem orderItem = new OrderItem(product, quantity, totalPrice);
            orderItems.add(orderItem);
        }

        // Đặt orderItems vào request attribute để hiển thị qua order-view.jsp
        request.setAttribute("orderItems", orderItems);

        // Điều hướng đến trang order-view.jsp để hiển thị các sản phẩm đã được chọn
        request.getRequestDispatcher("WEB-INF/view/order-view.jsp").forward(request, response);
    }

    // delete orrder
    private void deleteOrder(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    if (session != null) {
        session.removeAttribute("orderItems");  // Xóa orderItems khỏi session
        System.out.println("Đã xóa orderItems");
    }
    response.sendRedirect("./cart");
}


    // checkVoucher và thay đổi thông tin page   => in ra total bill
    private void checkVoucher(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    // checkout lưu dữ liệu xuống database xong chuyển về view order history
    // có lỗi thì back lại order
    private void placeAnOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
