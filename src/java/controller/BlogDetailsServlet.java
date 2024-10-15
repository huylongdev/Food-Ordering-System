package controller;

import context.AccountDAO;
import context.CommentDAO;
import context.PostDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import model.Comment;
import model.Post;

@WebServlet(name = "BlogDetailsServlet", urlPatterns = {"/blogdetails"})
public class BlogDetailsServlet extends HttpServlet {

    private PostDAO postDAO = new PostDAO();
    private CommentDAO commentDAO = new CommentDAO();
    private AccountDAO accountDAO = new AccountDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String postIdParam = request.getParameter("postId");
            if (postIdParam != null) {
                int postId = Integer.parseInt(postIdParam);
                Post post = postDAO.getPostById(postId);

                if (post != null) {
                    String fullName = postDAO.getFullNameByPostId(postId);
                    String avtURL = postDAO.getAvatarByPostId(postId);

                    request.setAttribute("postId", postIdParam);
                    request.setAttribute("post", post);
                    request.setAttribute("fullName", fullName);
                    request.setAttribute("avtURL", avtURL);

                    List<Comment> comments = commentDAO.getCommentsByPostID(postId);
                    request.setAttribute("comments", comments);

                    request.getRequestDispatcher("/WEB-INF/view/blogdetails.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String commentText = request.getParameter("commentInput");
            String postIdParam = request.getParameter("postID");
            String userIdParam = request.getParameter("userID");
            if (postIdParam == null || postIdParam.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing post ID");
                return;
            }
            if (userIdParam == null || userIdParam.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing user ID");
                return;
            }
            int postId = Integer.parseInt(postIdParam);
            int userID = Integer.parseInt(userIdParam);
            String userFullName = accountDAO.getFullNameByUserId(userID);
            String userAvtURL = accountDAO.getAvatarByUserId(userID);

            Comment comment = new Comment(userID, userFullName, userAvtURL, postId, commentText);
            comment.setCreatedDate(new Date());
            boolean isAdded = commentDAO.addComment(comment);

            if (isAdded) {
                List<Comment> comments = commentDAO.getCommentsByPostID(postId);
                request.setAttribute("comments", comments);

                Post post = postDAO.getPostById(postId);
                request.setAttribute("post", post);
                request.setAttribute("postId", postIdParam);
                request.setAttribute("fullName", userFullName); 
                request.setAttribute("avtURL", userAvtURL); 

                request.getRequestDispatcher("/WEB-INF/view/blogdetails.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Failed to post comment. Please try again.");
                request.getRequestDispatcher("/WEB-INF/view/blogdetails.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/blog");
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles blog details and comments";
    }
}
