package context;

import model.Discount;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiscountDAO {

    private DBContext dbContext;

    public DiscountDAO() {
        dbContext = new DBContext(); 
    }

    public void createDiscount(Discount discount) {
        String sql = "INSERT INTO Discount (UserID, DiscountName, StartDate, EndDate, DiscountPercentage, Type) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = dbContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, discount.getUserID());
            ps.setString(2, discount.getDiscountName());
            ps.setDate(3, new java.sql.Date(discount.getStartDate().getTime()));
            ps.setDate(4, new java.sql.Date(discount.getEndDate().getTime()));
            ps.setDouble(5, discount.getDiscountPercentage());
            ps.setString(6, discount.getType());
            ps.executeUpdate(); 
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace(); 
        } finally {
            dbContext.closeConnection(conn); 
        }
    }

   
    public List<Discount> getAllDiscounts() {
        List<Discount> discounts = new ArrayList<>();
        String sql = "SELECT * FROM Discount"; 
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = dbContext.getConnection(); 
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(); 

            while (rs.next()) {
                Discount discount = new Discount();
                discount.setDiscountID(rs.getInt("DiscountID"));
                discount.setUserID(rs.getInt("UserID"));
                discount.setDiscountName(rs.getString("DiscountName"));
                discount.setStartDate(rs.getDate("StartDate"));
                discount.setEndDate(rs.getDate("EndDate"));
                discount.setDiscountPercentage(rs.getDouble("DiscountPercentage"));
                discount.setType(rs.getString("Type"));
                discounts.add(discount); 
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        } catch (Exception e) {
            e.printStackTrace(); 
        } finally {
            dbContext.closeConnection(conn); 
        }
        return discounts; 
    }

    public void deleteDiscount(int discountID) throws Exception {
        String sql = "DELETE FROM Discount WHERE DiscountID = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dbContext.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, discountID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error deleting discount: " + e.getMessage());
        } finally {
            dbContext.closeConnection(conn);
        }
    }

    public void updateDiscount(Discount discount) throws Exception {
    String sql = "UPDATE Discount SET UserID = ?, DiscountName = ?, StartDate = ?, EndDate = ?, DiscountPercentage = ?, Type = ? WHERE DiscountID = ?";
    Connection conn = null;
    PreparedStatement ps = null;

    try {
        conn = dbContext.getConnection(); 
        ps = conn.prepareStatement(sql);
        ps.setInt(1, discount.getUserID());
        ps.setString(2, discount.getDiscountName());
        ps.setDate(3, new java.sql.Date(discount.getStartDate().getTime()));
        ps.setDate(4, new java.sql.Date(discount.getEndDate().getTime()));
        ps.setDouble(5, discount.getDiscountPercentage());
        ps.setString(6, discount.getType());
        ps.setInt(7, discount.getDiscountID()); 
        ps.executeUpdate(); 
    } catch (SQLException e) {
        e.printStackTrace(); 
        throw new Exception("Error updating discount: " + e.getMessage());
    } finally {
        dbContext.closeConnection(conn); 
    }
}

}
