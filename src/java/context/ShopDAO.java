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

public class ShopDAO {

    private DBContext dbContext;

    public ShopDAO() {
        dbContext = new DBContext();
    }

    // Create a new restaurant
    public boolean createRestaurant(Shop r) {
        String query = "INSERT INTO Shop (Name, Description, Status, ShopImage, Address, TimeOpen, TimeClose) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, r.getName());
            ps.setString(2, r.getDescription());
            ps.setBoolean(3, r.getStatus());
            ps.setString(4, r.getShopImage());
            ps.setString(5, r.getAddress());
            ps.setTime(6, Time.valueOf(r.getTimeOpen()));
            ps.setTime(7, Time.valueOf(r.getTimeClose()));

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update a restaurant
    public boolean updateRestaurant(Shop r) {
        String query = "UPDATE Shop SET Name = ?, Description = ?, Status = ?, ShopImage = ?, Address = ?, TimeOpen = ?, TimeClose = ? WHERE ShopID = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, r.getName());
            ps.setString(2, r.getDescription());
            ps.setBoolean(3, r.getStatus());
            ps.setString(4, r.getShopImage());
            ps.setString(5, r.getAddress());
            ps.setTime(6, Time.valueOf(r.getTimeOpen()));
            ps.setTime(7, Time.valueOf(r.getTimeClose()));
            ps.setInt(8, r.getShopID());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a restaurant by ShopID
    public boolean deleteRestaurant(int shopID) {
        String query = "DELETE FROM Shop WHERE ShopID = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, shopID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get a restaurant by ShopID
    public Shop getRestaurantByID(int shopID) {
        String query = "SELECT * FROM Shop WHERE ShopID = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, shopID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Shop(
                            rs.getInt("ShopID"),
                            rs.getString("Name"),
                            rs.getString("Description"),
                            rs.getBoolean("Status"),
                            rs.getString("ShopImage"),
                            rs.getString("Address"),
                            rs.getTime("TimeOpen").toLocalTime(),
                            rs.getTime("TimeClose").toLocalTime()
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get all restaurants
    public List<Shop> getAllRestaurants() {
        List<Shop> restaurants = new ArrayList<>();
        String query = "SELECT * FROM Shop";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Shop restaurant = new Shop(
                        rs.getInt("ShopID"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getBoolean("Status"),
                        rs.getString("ShopImage"),
                        rs.getString("Address"),
                        rs.getTime("TimeOpen").toLocalTime(),
                        rs.getTime("TimeClose").toLocalTime()
                );
                restaurants.add(restaurant);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restaurants;
    }

    //PAGINATION
    public List<Shop> getShops(int page, int size) {
        List<Shop> shops = new ArrayList<>();
        int offset = (page - 1) * size;
        String query = "SELECT * FROM Shop ORDER BY ShopID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, offset);
            ps.setInt(2, size);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Shop shop = new Shop();
                shop.setShopID(rs.getInt("ShopID"));
                shop.setName(rs.getString("Name"));
                shop.setDescription(rs.getString("Description"));
                shop.setStatus(rs.getBoolean("Status"));
                shop.setShopImage(rs.getString("ShopImage"));
                shop.setAddress(rs.getString("Address"));
                shop.setTimeOpen(rs.getTime("TimeOpen").toLocalTime());
                shop.setTimeClose(rs.getTime("TimeClose").toLocalTime());

                shops.add(shop);
            }

            System.out.println("Total shops retrieved: " + shops.size()); // Kiểm tra số lượng cửa hàng
        } catch (Exception e) {
            e.printStackTrace();
        }

        return shops;
    }

    public int getTotalShops() {
        int totalShops = 0;
        String query = "SELECT COUNT(*) AS Total FROM Shop";

        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                totalShops = rs.getInt("Total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalShops;
    }

    //Search with pagination
    public List<Shop> searchShop(String keyword, int page, int size) {
        List<Shop> shops = new ArrayList<>();
        int offset = (page - 1) * size;
        String query = "SELECT * FROM Shop WHERE Name LIKE ? OR Description LIKE ? ORDER BY ShopID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            String searchValue = "%" + keyword + "%";
            ps.setString(1, searchValue);
            ps.setString(2, searchValue);
            ps.setInt(3, offset);
            ps.setInt(4, size);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Shop shop = new Shop();
                shop.setShopID(rs.getInt("ShopID"));
                shop.setName(rs.getString("Name"));
                shop.setDescription(rs.getString("Description"));
                shop.setStatus(rs.getBoolean("Status"));
                shop.setShopImage(rs.getString("ShopImage"));
                shop.setAddress(rs.getString("Address"));
                shop.setTimeOpen(rs.getTime("TimeOpen").toLocalTime());
                shop.setTimeClose(rs.getTime("TimeClose").toLocalTime());

                shops.add(shop);
            }

            System.out.println("Total shops found for keyword '" + keyword + "': " + shops.size()); // Kiểm tra số lượng cửa hàng tìm thấy
        } catch (Exception e) {
            e.printStackTrace();
        }

        return shops;
    }

    public List<Shop> searchShop(String keyword) {
        List<Shop> shops = new ArrayList<>();
        String query = "SELECT * FROM Shop WHERE Name LIKE ? OR Description LIKE ?";

        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            String searchValue = "%" + keyword + "%";
            ps.setString(1, searchValue);
            ps.setString(2, searchValue);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Shop shop = new Shop();
                shop.setShopID(rs.getInt("ShopID"));
                shop.setName(rs.getString("Name"));
                shop.setDescription(rs.getString("Description"));
                shop.setStatus(rs.getBoolean("Status"));
                shop.setShopImage(rs.getString("ShopImage"));
                shop.setAddress(rs.getString("Address"));
                shop.setTimeOpen(rs.getTime("TimeOpen").toLocalTime());
                shop.setTimeClose(rs.getTime("TimeClose").toLocalTime());

                shops.add(shop);
            }

            System.out.println("Total shops found: " + shops.size()); // Kiểm tra số lượng cửa hàng tìm thấy
        } catch (Exception e) {
            e.printStackTrace();
        }

        return shops;
    }

    public List<Shop> getShopsSortedByTimeOpen(int page, int size) {
        List<Shop> shops = new ArrayList<>();
        int offset = (page - 1) * size;
        String query = "SELECT * FROM Shop ORDER BY TimeOpen OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, offset);
            ps.setInt(2, size);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Shop shop = new Shop();
                shop.setShopID(rs.getInt("ShopID"));
                shop.setName(rs.getString("Name"));
                shop.setDescription(rs.getString("Description"));
                shop.setStatus(rs.getBoolean("Status"));
                shop.setShopImage(rs.getString("ShopImage"));
                shop.setAddress(rs.getString("Address"));
                shop.setTimeOpen(rs.getTime("TimeOpen").toLocalTime());
                shop.setTimeClose(rs.getTime("TimeClose").toLocalTime());

                shops.add(shop);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return shops;
    }

    public List<Shop> getShopsSortedByTimeClose(int page, int size) {
        List<Shop> shops = new ArrayList<>();
        int offset = (page - 1) * size;
        String query = "SELECT * FROM Shop ORDER BY TimeClose DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, offset);
            ps.setInt(2, size);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Shop shop = new Shop();
                shop.setShopID(rs.getInt("ShopID"));
                shop.setName(rs.getString("Name"));
                shop.setDescription(rs.getString("Description"));
                shop.setStatus(rs.getBoolean("Status"));
                shop.setShopImage(rs.getString("ShopImage"));
                shop.setAddress(rs.getString("Address"));
                shop.setTimeOpen(rs.getTime("TimeOpen").toLocalTime());
                shop.setTimeClose(rs.getTime("TimeClose").toLocalTime());

                shops.add(shop);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return shops;
    }

    public boolean createShop(Shop s) {
        String query = "INSERT INTO Shop (Name, Description, Status, ShopImage, Address, TimeOpen, TimeClose) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

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

    public Shop getShopByID(int shopID) {
        String query = "SELECT * FROM Shop WHERE ShopID = ?";
        try (Connection con = dbContext.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, shopID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Shop shop = new Shop(
                        rs.getInt("ShopID"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getBoolean("Status"),
                        rs.getString("ShopImage"),
                        rs.getString("Address"),
                        rs.getTime("TimeOpen").toLocalTime(),
                        rs.getTime("TimeClose").toLocalTime()
                );
                return shop;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Shop not found");
        return null;
    }

}
