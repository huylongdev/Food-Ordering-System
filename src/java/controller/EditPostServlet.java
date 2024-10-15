/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import context.PostDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.nio.file.Paths;
import model.Post;
/**
 *
 * @author phuct
 */
@WebServlet(name = "EditPostServlet", urlPatterns = {"/editPost"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10,      // 10MB
                 maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class EditPostServlet extends HttpServlet {
    
    private static final String SAVE_DIR = "blogImg";
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
            out.println("<title>Servlet EditPostServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditPostServlet at " + request.getContextPath() + "</h1>");
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
        
          try {
        // Retrieve the form data
        Part filePart = request.getPart("imgPost");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        int postId = Integer.parseInt(request.getParameter("postId"));
        int userId = Integer.parseInt(request.getParameter("userID"));

        // Ensure that all fields are filled and file is uploaded
        if (filePart != null && title != null && description != null) {
            // Get the path to save the uploaded image
            String appPath = request.getServletContext().getRealPath("/");
            String savePath = appPath + File.separator + SAVE_DIR;

            // Create the directory if it doesn't exist
            File fileSaveDir = new File(savePath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdir();
            }

            // Generate a unique file name
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
            String filePath = savePath + File.separator + uniqueFileName;

            // Save the file to the server
            filePart.write(filePath);

            // Get the relative path to store in the database
            String relativePath = SAVE_DIR + File.separator + uniqueFileName;

            // Create a new Post object and set the updated values
            Post updatedPost = new Post();
            updatedPost.setPostID(postId);           // Set post ID for the post to be updated
            updatedPost.setUserID(userId);           // Set the user ID
            updatedPost.setImgURL(relativePath);     // Set the new image path
            updatedPost.setHeading(title);           // Set the updated title
            updatedPost.setContent(description);     // Set the updated description

            // Call the updatePost method in PostDAO to update the post in the database
            PostDAO postDAO = new PostDAO();
            boolean success = postDAO.updatePost(updatedPost);

            if (success) {
                // Redirect to the blog page or updated post
                response.sendRedirect("blogdetails?postId=" + postId);
            } else {
                // Handle failure in updating the post
                request.setAttribute("error", "Failed to update the post.");
                request.getRequestDispatcher("/OrderingSystem/blog").forward(request, response);
            }
        } else {
            // Handle missing fields or file
            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("/OrderingSystem/blog").forward(request, response);
        }
    } catch (Exception e) {
        // Handle any exceptions and display an error message
        e.printStackTrace();
        request.setAttribute("error", "An error occurred while processing your request.");
        request.getRequestDispatcher("/OrderingSystem/blog").forward(request, response);
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
