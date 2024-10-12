package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Post;
import context.PostDAO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;

/**
 *
 * @author phuct
 */
@WebServlet(name = "AddPostServlet", urlPatterns = {"/addPost"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class AddPostServlet extends HttpServlet {

    private static final String SAVE_DIR = "blogImg";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/OrderingSystem/blog").forward(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Part filePart = request.getPart("imgPost");
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            int userId = Integer.parseInt(request.getParameter("userID"));

            if (filePart != null && title != null && description != null) {
                String appPath = request.getServletContext().getRealPath("/");
                String savePath = appPath + File.separator + SAVE_DIR;

                File fileSaveDir = new File(savePath);
                if (!fileSaveDir.exists()) {
                    fileSaveDir.mkdir();
                }

                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
                String filePath = savePath + File.separator + uniqueFileName;

                filePart.write(filePath);

                String relativePath = SAVE_DIR + File.separator + uniqueFileName;

                Post newPost = new Post();
                newPost.setUserID(userId);
                newPost.setImgURL(relativePath);
                newPost.setHeading(title);
                newPost.setContent(description);

                PostDAO postDAO = new PostDAO();
                postDAO.createPost(newPost);

                response.sendRedirect("blog");
            } else {
                request.setAttribute("error", "All fields are required.");
                request.getRequestDispatcher("WEB-INF/view/blog.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while processing your request.");
            request.getRequestDispatcher("WEB-INF/view/blog.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for adding new blog posts";
    }
}
