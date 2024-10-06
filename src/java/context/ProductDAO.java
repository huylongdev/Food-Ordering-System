/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package context;

import java.beans.Statement;
import model.Product;

import java.sql.Time;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Shop;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */


public class ProductDAO {
    private DBContext dbContext;

    public ProductDAO() {
        dbContext = new DBContext();
    }
    
    public boolean checkConnection() throws Exception {
        try (Connection conn = dbContext.getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Connection failed
        }
    }
    
    public boolean createProduct(Product p) {
        String query = "INSERT INTO Product (Name, Description, Price, Status, ShopID, CategoryID, Rating) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
             
            ps.setString(1, p.getName());
            ps.setString(2, p.getDescription());
            ps.setDouble(3, p.getPrice()); 
            ps.setBoolean(4, p.isStatus());
            ps.setInt(5, p.getShopId());
            ps.setInt(6, p.getCategoryId());
            ps.setDouble(7, p.getRating());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Product";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

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
                    rs.getDouble("Rating")
                );
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }
    
    public Product getProductByID(int productID) {
    Product product = null;
    String query = "SELECT * FROM Product WHERE ProductID = ?";
    try (Connection conn = dbContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

        ps.setInt(1, productID); 
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                product = new Product(
                    rs.getInt("ProductID"),
                    rs.getString("Name"),
                    rs.getString("Description"),
                    rs.getDouble("Price"),
                    rs.getBoolean("Status"),
                    rs.getInt("ShopID"),
                    rs.getInt("CategoryID"),
                    rs.getInt("PurchaseCount"),
                    rs.getDouble("Rating")
                );
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return product;
}

    public String getCategoryNameByID(int categoryID) {
    String categoryName = null;
    String query = "SELECT Type FROM Categories WHERE CategoryID = ?";
    try (Connection conn = dbContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

        ps.setInt(1, categoryID); // Đặt giá trị CategoryID vào câu truy vấn
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                categoryName = rs.getString("Type");
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return categoryName;
}
    
    public boolean updateProduct(Product product) throws Exception {
        String query = "UPDATE Product SET Name = ?, Description = ?, Price = ?, Status = ?, ShopID = ?, CategoryID = ?  WHERE ProductID = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setBoolean(4, product.isStatus());
            ps.setInt(5, product.getShopId());
            ps.setInt(6, product.getCategoryId());
            ps.setInt(7, product.getProductId());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
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
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error deleting product: " + e.getMessage());
        }
    }
    
    
    
    
    public List<Product> getProductByShopID(int shopID) {
    List<Product> products = new ArrayList<>();
    String query = "SELECT * FROM Product WHERE ShopID = ?";
    try (Connection conn = dbContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

        ps.setInt(1, shopID); 
        try (ResultSet rs = ps.executeQuery()) {
            
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
                    rs.getDouble("Rating")
                );
                products.add(product);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return products;
}
    
    
    
    public int createProductGetID(Product p) {
    String query = "INSERT INTO Product (Name, Description, Price, Status, ShopID, CategoryID, Rating) VALUES (?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = dbContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
         
        ps.setString(1, p.getName());
        ps.setString(2, p.getDescription());
        ps.setDouble(3, p.getPrice());
        ps.setBoolean(4, p.isStatus());
        ps.setInt(5, p.getShopId());
        ps.setInt(6, p.getCategoryId());
        ps.setDouble(7, p.getRating());

        int affectedRows = ps.executeUpdate();

        if (affectedRows > 0) {
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);  // Trả về ProductID tự động tạo
                }
            }
        }
        return -1;  
    } catch (Exception e) {
        e.printStackTrace();
        return -1;  
    }
}


}
