/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package context;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.CartItem;

/**
 *
 * @author LENOVO
 */
public class CartDAO {
    
    private DBContext dbContext;

    public CartDAO() {
        dbContext = new DBContext();
    }
    
    
    public boolean addToCart(CartItem c) {
    String query = "INSERT INTO CartItem (UserID, ProductID, Quantity, ShopID) VALUES (?, ?, ?, ?)";
    try (Connection conn = dbContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

        ps.setInt(1, c.getUserID()); 
        ps.setInt(2, c.getProductID()); 
        ps.setInt(3, c.getQuantity()); 
        ps.setInt(4, c.getShopID()); 

        int rowsAffected = ps.executeUpdate(); 
        return rowsAffected > 0; 
    } catch (Exception e) {
        e.printStackTrace();
        return false; 
    }
}
}
