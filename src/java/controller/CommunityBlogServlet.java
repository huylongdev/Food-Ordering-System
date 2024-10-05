/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import context.AccountDAO;
import context.PostDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Post;

/**
 *
 * @author phuct
 */
@WebServlet(name = "CommunityBlog", urlPatterns = {"/blog"})
public class CommunityBlogServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

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
        request.getRequestDispatcher("WEB-INF/view/blog.jsp").forward(request, response);

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PostDAO postDAO = new PostDAO();
        AccountDAO userDAO = new AccountDAO();
        List<Post> postList = null;
        Post newPost = null;
        String fullNameNewPost = null;

        try {
            boolean isConnected = postDAO.checkConnection();
            if (isConnected) {
                System.out.println("Connected to database.");

                // Lấy tất cả các bài viết
                postList = postDAO.getAllPostsHaveFullName();
                newPost = postDAO.getLatestPost();

                if (newPost != null) {
                    System.out.println("Latest post found: " + newPost.getHeading());
                    fullNameNewPost = userDAO.getFullNameByUserId(newPost.getUserID());
                    System.out.println("Full name of the user for the latest post: " + fullNameNewPost);
                } else {
                    System.out.println("No posts found.");
                }

            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }

        request.setAttribute("postList", postList);
        request.setAttribute("newPost", newPost);
        request.setAttribute("fullNameNewPost", fullNameNewPost);

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/view/blog.jsp"); // Thay thế đường dẫn chính xác đến JSP
        dispatcher.forward(request, response);
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
