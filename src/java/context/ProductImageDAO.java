/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package context;

import java.util.ArrayList;
import java.util.List;
import model.ProductImage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author LENOVO
 */
public class ProductImageDAO {
    private DBContext dbContext;

    public ProductImageDAO() {
        dbContext = new DBContext();
    }
    
    public List<ProductImage> getListImageByProductID(int productID) {
    List<ProductImage> images = new ArrayList<>();
    String query = "SELECT * FROM ProductImage WHERE ProductID = ?";
    try (Connection conn = dbContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

        ps.setInt(1, productID); 
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ProductImage image = new ProductImage(
                    rs.getInt("ImageID"),
                    rs.getInt("ProductID"),
                    rs.getBoolean("IsAvatar"),
                    rs.getString("ImgURL")
                );
                images.add(image);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return images;
}
    
    public ProductImage getAvatarProductImageByID(int productID) {
    ProductImage image = null;
    String query = "SELECT * FROM ProductImage WHERE ProductID = ? AND IsAvatar = 1";
    try (Connection conn = dbContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

        ps.setInt(1, productID); 
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) { 
                image = new ProductImage(
                    rs.getInt("ImageID"),
                    rs.getInt("ProductID"),
                    rs.getBoolean("IsAvatar"),
                    rs.getString("ImgURL")
                );
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return image; 
}
    
    public boolean insertProductImage(ProductImage img) {
    String query = "INSERT INTO ProductImage (ProductID, IsAvatar, ImgURL) VALUES (?, ?, ?)";
    try (Connection conn = dbContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {
         
        ps.setInt(1, img.getProductID());
        ps.setBoolean(2, img.isAvatar());
        ps.setString(3, img.getImgURL());

        return ps.executeUpdate() > 0;  
    } catch (Exception e) {
        e.printStackTrace();
        return false;  
    }
}


}
