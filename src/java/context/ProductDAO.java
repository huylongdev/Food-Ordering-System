/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package context;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Product;

/**
 *
 * @author phuct
 */
public class ProductDAO {

    private DBContext dbContext;

    // Constructor to initialize DBContext
    public ProductDAO() {
        dbContext = new DBContext();
    }

    // Check the database connection
    public boolean checkConnection() throws Exception {
        try (Connection conn = dbContext.getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Connection failed
        }
    }

    // CREATE: Insert a new product into the database
    public boolean addProduct(Product product) throws Exception {
        String query = "INSERT INTO Product (Name, Description, Price, Status, ShopID, CategoryID, PurchaseCount, Rating) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setBoolean(4, product.isStatus());
            ps.setInt(5, product.getShopID());
            ps.setInt(6, product.getCategoryID());
            ps.setInt(7, product.getPurchaseCount());
            ps.setDouble(8, product.getRating());
            ps.setString(9, product.getProductImg());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error adding product: " + e.getMessage());
        }
    }

    // READ: Get a product by ID
    public Product getProductById(int productId) throws Exception {
        String query = "SELECT * FROM Product WHERE ProductID = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                            rs.getInt("ProductID"),
                            rs.getString("Name"),
                            rs.getString("Description"),
                            rs.getDouble("Price"),
                            rs.getBoolean("Status"),
                            rs.getInt("ShopID"),
                            rs.getInt("CategoryID"),
                            rs.getInt("PurchaseCount"),
                            rs.getDouble("Rating"),
                            rs.getString("ProductImg")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error retrieving product: " + e.getMessage());
        }
        return null;
    }

    // UPDATE: Update an existing product
    public boolean updateProduct(Product product) throws Exception {
        String query = "UPDATE Product SET Name = ?, Description = ?, Price = ?, Status = ?, ShopID = ?, CategoryID = ?, PurchaseCount = ?, Rating = ?, ProductImg = ?  WHERE ProductID = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setBoolean(4, product.isStatus());
            ps.setInt(5, product.getShopID());
            ps.setInt(6, product.getCategoryID());
            ps.setInt(7, product.getPurchaseCount());
            ps.setDouble(8, product.getRating());
            ps.setString(9, product.getProductImg());
            ps.setInt(10, product.getProductID());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error updating product: " + e.getMessage());
        }
    }

    // DELETE: Delete a product by ID
    public boolean deleteProduct(int productId) throws Exception {
        String query = "DELETE FROM Product WHERE ProductID = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, productId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error deleting product: " + e.getMessage());
        }
    }

    // READ: Get all products
    public List<Product> getAllProducts() throws Exception {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Product";
        try (Connection conn = dbContext.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("ProductID"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getDouble("Price"),
                        rs.getBoolean("Status"),
                        rs.getInt("ShopID"),
                        rs.getInt("CategoryID"),
                        rs.getInt("PurchaseCount"),
                        rs.getDouble("Rating"),
                        rs.getString("ProductImg")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error retrieving products: " + e.getMessage());
        }
        return products;
    }
}
