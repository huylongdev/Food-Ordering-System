/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package context;

/**
 *
 * @author LENOVO
 */
import java.sql.Time;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Shop;

import java.util.ArrayList;
import java.util.List;
import model.Shop;

public class ShopDAO {

    private DBContext dbContext;
    private List<Shop> accounts = new ArrayList<>();

    public ShopDAO() {
        dbContext = new DBContext();
    }
    
    
     public boolean createShop(Shop s) {
        String query = "INSERT INTO Shop (Name, Description, Status, ShopImage, Address, TimeOpen, TimeClose) VALUES (?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = dbContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

        ps.setString(1, s.getName());
        ps.setString(2, s.getDescription());
        ps.setBoolean(3, s.getStatus()); 
        ps.setString(4, s.getShopImage());
        ps.setString(5, s.getAddress());
        ps.setTime(6, Time.valueOf(s.getTimeOpen())); 
        ps.setTime(7, Time.valueOf(s.getTimeClose()));

        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
    }
    
    
    
    
    
}
