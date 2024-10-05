<<<<<<< HEAD
<<<<<<< HEAD:src/java/util/SendMailServlet.java
///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
// */
//package util;
//
//import jakarta.mail.Message;
//import jakarta.mail.MessagingException;
//import jakarta.mail.PasswordAuthentication;
//import jakarta.mail.Session;
//import jakarta.mail.Transport;
//import jakarta.mail.util.StreamProvider;
//import jakarta.mail.internet.InternetAddress;
//import jakarta.mail.internet.MimeMessage;
//import java.io.IOException;
//import java.io.PrintWriter;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.util.Properties;
//
///**
// *
// * @author LENOVO
// */
//public class SendMailServlet extends HttpServlet {
//
//    /**
//     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
//     * methods.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet SendMailServlet</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet SendMailServlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request,
//            HttpServletResponse response)
//            throws ServletException, IOException {
////        String to = request.getParameter("to");
////        String subject = request.getParameter("subject");
////        String messageText = request.getParameter("message");
//        String to = "nhs211306@gmail.com";
//        String subject = "Hi";
//        String messageText = "test";
//
//        // Cấu hình SMTP
//        String host = "smtp.gmail.com"; // SMTP server
//        final String username = "nguyenhuesuong211306@gmail.com"; // email gửi
//        final String password = "ypstwaazmrqflhcp"; // mật khẩu email gửi
//
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", host);
//        props.put("mail.smtp.port", "587");
//        
//
//        // Tạo session với email gửi và mật khẩu
//        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(username, password);
//            }
//        });
//
//        try {
//            // Tạo email
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(username));
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//            message.setSubject(subject);
//            message.setText(messageText);
//
//            // Gửi email
//            Transport.send(message);
//
//            response.getWriter().println("Email sent successfully!");
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /**
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
//
//}
=======
=======
>>>>>>> 2bee1e92edb910b65689712aaf1d5bca6787b54c
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.authenticate;

<<<<<<< HEAD
import context.AccountDAO;
import static controller.authenticate.RegisterServlet.isValidPassword;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.PasswordUtil;
=======
<<<<<<<< HEAD:src/java/controller/FoodDetailServlet.java
import context.ProductDAO;
import context.ProductImageDAO;

import context.ShopDAO;

========
import context.AccountDAO;
import static controller.authenticate.RegisterServlet.isValidPassword;
<<<<<<<< HEAD:src/java/controller/FoodDetailServlet.java
>>>>>>>> 2bee1e92edb910b65689712aaf1d5bca6787b54c:src/java/controller/authenticate/ResetPasswordServlet.java
========
>>>>>>>> 2bee1e92edb910b65689712aaf1d5bca6787b54c:src/java/controller/authenticate/ResetPasswordServlet.java
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Category;
import model.Product;
import model.ProductImage;

import model.Shop;

>>>>>>> 2bee1e92edb910b65689712aaf1d5bca6787b54c

/**
 *
 * @author LENOVO
 */
<<<<<<< HEAD
public class ResetPasswordServlet extends HttpServlet {
=======
@WebServlet(name = "FoodDetailServlet", urlPatterns = {"/food-detail"})
public class FoodDetailServlet extends HttpServlet {
>>>>>>> 2bee1e92edb910b65689712aaf1d5bca6787b54c

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
<<<<<<< HEAD
            out.println("<title>Servlet ResetPasswordServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ResetPasswordServlet at " + request.getContextPath() + "</h1>");
=======
            out.println("<title>Servlet FoodDetailServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FoodDetailServlet at " + request.getContextPath() + "</h1>");
>>>>>>> 2bee1e92edb910b65689712aaf1d5bca6787b54c
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
<<<<<<< HEAD
        String token = request.getParameter("token");

        AccountDAO userDAO = new AccountDAO();
        if (userDAO.isValidToken(token)) {

            request.setAttribute("token", token);
            request.getRequestDispatcher("WEB-INF/view/reset-password-form.jsp").forward(request, response);
        } else {
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {

                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet ResetPasswordServlet</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Invalid Token</h1>");
                out.println("</body>");
                out.println("</html>");
            }
        }
=======

        int id = Integer.parseInt(request.getParameter("productId"));
            ProductDAO pDAO = new ProductDAO();
            ProductImageDAO iDAO = new ProductImageDAO();
            Product p = pDAO.getProductByID(id);
            List<ProductImage> images = iDAO.getListImageByProductID(id);
            request.setAttribute("images", images);
            String cateName = pDAO.getCategoryNameByID(id);
            
            ShopDAO sDAO = new ShopDAO();
            Shop shop = sDAO.getShopByID(p.getShopId());
            
            
            request.setAttribute("shop", shop);

            request.setAttribute("cateName", cateName);
            request.setAttribute("p", p);
            
        request.getRequestDispatcher("WEB-INF/view/food-detail.jsp").forward(request, response);
>>>>>>> 2bee1e92edb910b65689712aaf1d5bca6787b54c
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
<<<<<<< HEAD
        String token = request.getParameter("token");
        String newPassword = request.getParameter("newPassword");

        AccountDAO userDAO = new AccountDAO();
        
        if (!isValidPassword(newPassword)) {
            request.setAttribute("message", "Invalid password. Need to have both number and letter and more than 8 characters.");
            request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
            return;
        }
        
        if (userDAO.isValidToken(token)) {
            // Cập nhật mật khẩu mới
            userDAO.updatePassword(token, PasswordUtil.hashPassword(newPassword));

            response.sendRedirect("login");
        } else {

            request.setAttribute("message", "Failed to reset password.");
            request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
        }
=======
        processRequest(request, response);
>>>>>>> 2bee1e92edb910b65689712aaf1d5bca6787b54c
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
<<<<<<< HEAD
>>>>>>> origin/main:src/java/controller/ResetPasswordServlet.java
=======
>>>>>>> 2bee1e92edb910b65689712aaf1d5bca6787b54c
