/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package context;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Post;

/**
 *
 * @author phuct
 */
public class PostDAO {

    AccountDAO userDAO = new AccountDAO();
    private DBContext dbContext;

    public PostDAO() {
        dbContext = new DBContext();
    }

    public boolean checkConnection() throws Exception {
        try (Connection conn = dbContext.getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Connection failed
        }
    }

    // Get all posts
    public List<Post> getAllPosts() throws Exception {
        List<Post> posts = new ArrayList<>();
        String query = "SELECT * FROM Post ORDER BY CreatedDate DESC";
        System.out.println("Executing query: " + query);
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Post post = new Post(
                        rs.getInt("PostID"),
                        rs.getInt("UserID"),
                        rs.getString("ImgURL"),
                        rs.getString("Heading"),
                        rs.getString("Content"),
                        rs.getTimestamp("CreatedDate")
                );
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error retrieving posts: " + e.getMessage());
        }
        System.out.println("Number of posts retrieved: " + posts.size()); // Ghi lại số lượng bài viết
        return posts;
    }

    // Get all posts have FullName
    public List<Post> getAllPostsHaveFullName() throws Exception {
        List<Post> posts = new ArrayList<>();
        String query = "SELECT * FROM Post ORDER BY CreatedDate DESC";
        System.out.println("Executing query: " + query);

        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Lấy thông tin bài viết
                Post post = new Post(
                        rs.getInt("PostID"),
                        rs.getInt("UserID"),
                        rs.getString("ImgURL"),
                        rs.getString("Heading"),
                        rs.getString("Content"),
                        rs.getTimestamp("CreatedDate")
                );

                // Lấy tên đầy đủ của người dùng theo UserID
                String fullName = userDAO.getFullNameByUserId(post.getUserID());
                post.setUserFullName(fullName); // Giả sử Post có setter cho FullName

                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error retrieving posts: " + e.getMessage());
        }
        System.out.println("Number of posts retrieved: " + posts.size()); // Ghi lại số lượng bài viết
        return posts;
    }

    // Get post by ID
    public Post getPostById(int postID) throws Exception {
        Post post = null;
        String query = "SELECT * FROM Post WHERE PostID = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, postID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    post = new Post(
                            rs.getInt("PostID"),
                            rs.getInt("UserID"),
                            rs.getString("ImgURL"),
                            rs.getString("Heading"),
                            rs.getString("Content"),
                            rs.getTimestamp("CreatedDate")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    // Create new post
    public boolean createPost(Post post) throws Exception {
        String query = "INSERT INTO Post (UserID, ImgURL, Heading, Content, CreatedDate) VALUES (?, ?, ?, ?, GETDATE())";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, post.getUserID());
            ps.setString(2, post.getImgURL());
            ps.setString(3, post.getHeading());
            ps.setString(4, post.getContent());
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update post
    public boolean updatePost(Post post) throws Exception {
        String query = "UPDATE Post SET UserID = ?, ImgURL = ?, Heading = ?, Content = ?, CreatedDate = ? WHERE PostID = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, post.getUserID());
            ps.setString(2, post.getImgURL());
            ps.setString(3, post.getHeading());
            ps.setString(4, post.getContent());
            ps.setTimestamp(5, new java.sql.Timestamp(post.getCreatedDate().getTime()));
            ps.setInt(6, post.getPostID());
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete post by ID
    public boolean deletePost(int postID) throws Exception {
        String query = "DELETE FROM Post WHERE PostID = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, postID);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get posts ordered by latest (newest to oldest)
    public List<Post> getPostsOrderedByLatest() throws Exception {
        List<Post> posts = new ArrayList<>();
        String query = "SELECT * FROM Post ORDER BY CreatedDate DESC";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Post post = new Post(
                        rs.getInt("PostID"),
                        rs.getInt("UserID"),
                        rs.getString("ImgURL"),
                        rs.getString("Heading"),
                        rs.getString("Content"),
                        rs.getTimestamp("CreatedDate")
                );
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    // Get the latest post
    public Post getLatestPost() throws Exception {
        Post post = null;
        String query = "SELECT TOP 1 * FROM Post ORDER BY CreatedDate DESC";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    post = new Post(
                            rs.getInt("PostID"),
                            rs.getInt("UserID"),
                            rs.getString("ImgURL"),
                            rs.getString("Heading"),
                            rs.getString("Content"),
                            rs.getTimestamp("CreatedDate")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    // Get the full name of the user based on the UserID of the post
    public String getFullNameByPostId(int postID) throws Exception {
        String fullName = null;
        String query = "SELECT u.FullName FROM Post p INNER JOIN [Users] u ON p.UserID = u.UserID WHERE p.PostID = ?";

        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, postID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    fullName = rs.getString("FullName");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fullName;
    }

    // Get the avatar image URL of the user based on the UserID
    public String getAvatarByUserId(int userID) throws Exception {
        String avatarImg = null;
        String query = "SELECT AvatarImg FROM [Users] WHERE UserID = ?";

        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    avatarImg = rs.getString("AvatarImg");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avatarImg;
    }
   
}
