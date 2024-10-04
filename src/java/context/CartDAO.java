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
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

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
    
     public List <CartItem> getCartByUserID(int id) {
        List <CartItem> cart = new ArrayList<>();
        String query = "SELECT * FROM CartItem where UserID = ? ";
        try (Connection con = dbContext.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CartItem c = new CartItem(
                        rs.getInt("CartItemID"),
                        rs.getInt("UserID"),
                        rs.getInt("ProductID"),
                        rs.getInt("Quantity"),
                        rs.getInt("ShopID")
                );
                cart.add(c);

            }
            return cart;

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("not found products");
        return null;
    }
     
     
     
    public boolean deleteCartProduct(int productID, int userID) {
        String query = "DELETE FROM CartItem WHERE ProductID = ? AND UserID = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, productID);
            ps.setInt(2, userID);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
