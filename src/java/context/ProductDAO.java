/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package context;

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

}