/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.authenticate;

import context.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import util.PasswordUtil;

/**
 *
 * @author LENOVO
 */
public class LoginServlet extends HttpServlet {

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
        request.getRequestDispatcher("WEB-INF/view/login.jsp").forward(request, response);
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
        processRequest(request, response);
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

        AccountDAO accountDAO = new AccountDAO();
        String u = request.getParameter("user");

        String p = request.getParameter("pass");

        HttpSession session = request.getSession();
        Account acc = accountDAO.checkAccountByUserName(u);
        
        if (acc != null && PasswordUtil.checkPassword(p, acc.getPassword())) {
            session.setAttribute("username", u);
            session.setAttribute("user", acc);
            if (acc.getRole() == 1) {
                session.setAttribute("role", "customer");
                session.setMaxInactiveInterval(10 * 24 * 60 * 60);
                response.sendRedirect("/OrderingSystem");
            }
            if (acc.getRole() == 2) {
                session.setAttribute("role", "shop");
                session.setMaxInactiveInterval(10 * 24 * 60 * 60);
                response.sendRedirect("/OrderingSystem/restaurant-detail?shopId="+acc.getShopID());
            }
        } else {
            request.setAttribute("message", "Error name and password");
            request.getRequestDispatcher("WEB-INF/view/login.jsp").forward(request, response);
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