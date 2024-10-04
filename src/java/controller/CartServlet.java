/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import model.CartItemDTO;
import context.CartDAO;
import context.ProductDAO;
import context.ProductImageDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.CartItem;
import model.Product;
import model.ProductImage;

/**
 *
 * @author LENOVO
 */
public class CartServlet extends HttpServlet {

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
            out.println("<title>Servlet CartServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CartServlet at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        if (!session.getAttribute("role").equals("customer")) {
            response.sendRedirect("login");
        } else {
            ProductDAO pDAO = new ProductDAO();
            CartDAO cartDAO = new CartDAO();
            Account user = (Account) session.getAttribute("user");
            
            List<CartItem> cart = cartDAO.getCartByUserID(user.getUserID());
            
            List<CartItemDTO> cartList = new ArrayList<>();
            ProductImageDAO pid= new ProductImageDAO();
            for (CartItem c : cart) {
                Product p = pDAO.getProductByID(c.getProductID());
                
                CartItemDTO cid = new CartItemDTO(p, c.getQuantity(),pid.getAvatarProductImageByID(c.getProductID()).getImgURL());
                cartList.add(cid);
                
            }
            session.setAttribute("cart", cartList);

            request.getRequestDispatcher("WEB-INF/view/cart.jsp").forward(request, response);
        }
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
        if (request.getParameter("isAdd") != null) {
            addProduct(request, response);
        } else {
            deleteProduct(request, response);
        }

    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        int productID = Integer.parseInt(request.getParameter("productID"));
        int shopID = Integer.parseInt(request.getParameter("shopID"));
        int userID = Integer.parseInt(request.getParameter("userID"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        CartItem c = new CartItem(userID, productID, quantity, shopID);

        CartDAO cartDAO = new CartDAO();
        if (cartDAO.addToCart(c)) {
            session.setAttribute("alert", "Added to cart successfully!");
        } else {
            session.setAttribute("alert", "Failed to add product!");
        }
        response.sendRedirect("food-detail?productId=" + productID);
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String[] selected = request.getParameterValues("isSelected");
        if (selected == null) {
            session.setAttribute("cartStatus", "Choose product to delete!");
            response.sendRedirect("cart");

        } else {
            for (String productID : selected) {
                int id, userID;
                try {
                    userID = Integer.parseInt(request.getParameter("userID"));
                    id = Integer.parseInt(productID);
                    CartDAO cartDAO = new CartDAO();
                    if (!cartDAO.deleteCartProduct(id, userID)) {
                        session.setAttribute("cartStatus", "Cannot delete!");
                    }
                } catch (NumberFormatException e) {
                    throw new ServletException("invalid id");
                }

            }
            session.setAttribute("cartStatus", "Delete products successfully!");
            response.sendRedirect("cart");
        }
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
