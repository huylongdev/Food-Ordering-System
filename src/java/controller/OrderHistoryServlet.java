/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import context.OrderDAO;
import context.OrderItemDAO;
import context.ProductDAO;
import context.ProductImageDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.Order;
import model.OrderHistoryDTO;
import model.OrderItem;
import model.OrderItemHistoryDTO;
import model.Product;
import util.Utility;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "OrderHistoryServlet", urlPatterns = {"/order-history"})
public class OrderHistoryServlet extends HttpServlet {

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
            out.println("<title>Servlet OrderHistoryServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderHistoryServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        OrderDAO oDAO = new OrderDAO();
        Account u = (Account) session.getAttribute("user");
        List<Order> orderList = oDAO.getOrderListByUserID(u.getUserID());
        List<OrderHistoryDTO> oDTOList = new ArrayList<>();
        for (Order order : orderList) {
            OrderItemDAO oiDAO = new OrderItemDAO();
            List<OrderItem> oiList = oiDAO.getOrderItemByOrderID(order.getOrderId());
            List<OrderItemHistoryDTO> oihDTOList = new ArrayList<>();
            ProductDAO pDAO = new ProductDAO();
            ProductImageDAO pid = new ProductImageDAO();
            if (order.getTimePickup()!= null){
                order.setAddress(Utility.getShopAddressByOrderID(order.getOrderId()) + " (At Restaurant)");
                
            }
            for (OrderItem oi : oiList) {
                Product p = pDAO.getProductByID(oi.getProductId());
                p.setPrice(oi.getTotalPrice() / oi.getQuantity());
                OrderItemHistoryDTO oihDTO = new OrderItemHistoryDTO(p, oi.getQuantity(), pid.getAvatarProductImageByID(p.getProductId()).getImgURL());
                oihDTOList.add(oihDTO);
            }
            OrderHistoryDTO ohDTO = new OrderHistoryDTO(order, oihDTOList);
            oDTOList.add(ohDTO);
        }
        request.setAttribute("orderList", oDTOList);
        request.getRequestDispatcher("WEB-INF/view/order-history.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
