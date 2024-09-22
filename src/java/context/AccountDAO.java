package context;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Account;

public class AccountDAO {

    private DBContext dbContext;
    private List<Account> accounts = new ArrayList<>();

    public AccountDAO() {
        dbContext = new DBContext();
    }

    // Cập nhật thông tin người dùng
    public void updateUser(int userId, String username, String fullName, String phoneNumber, String email, String address) {
        String sql = "UPDATE Users SET UserName=?, FullName=?, PhoneNumber=?, Email=?, Address=? WHERE UserID=?";
        try (Connection con = dbContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, fullName);
            ps.setString(3, phoneNumber);
            ps.setString(4, email);
            ps.setString(5, address);
            ps.setInt(6, userId);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User updated successfully!");
            } else {
                System.out.println("No user found with the given ID.");
            }
        } catch (Exception e) {
            System.out.println("Error while updating user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Lấy người dùng theo ID
    public Account getUserById(int userId) {
        Account user = null;
        String sql = "SELECT * FROM Users WHERE UserID=?";
        try (Connection con = dbContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new Account();
                user.setUserID(rs.getInt("UserID"));
                user.setUserName(rs.getString("UserName"));
                user.setFullName(rs.getString("FullName"));
                user.setPhoneNumber(rs.getString("PhoneNumber"));
                user.setEmail(rs.getString("Email"));
                user.setAddress(rs.getString("Address"));
                user.setAvtImg(rs.getString("AvtImg"));
                user.setShopID(rs.getInt("ShopID"));
                user.setRole(rs.getInt("Role"));
            }
        } catch (Exception e) {
            System.out.println("Error while retrieving user: " + e.getMessage());
            e.printStackTrace();
        }
        return user;
    }

    // Kiểm tra tài khoản bằng tên người dùng
    public Account checkAccountByUserName(String userName) {
        String sql = "SELECT * FROM Users WHERE UserName=?";
        try (Connection con = dbContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Account(
                        rs.getInt("UserID"),
                        rs.getString("UserName"),
                        rs.getString("Pass"),
                        rs.getString("FullName"),
                        rs.getString("PhoneNumber"),
                        rs.getString("Email"),
                        rs.getString("Address"),
                        rs.getString("AvtImg"),
                        rs.getInt("ShopID"),
                        rs.getInt("Role")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Kiểm tra tài khoản bằng email
    public Account checkAccountByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE Email=?";
        try (Connection con = dbContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Account(
                        rs.getInt("UserID"),
                        rs.getString("UserName"),
                        rs.getString("Pass"),
                        rs.getString("FullName"),
                        rs.getString("PhoneNumber"),
                        rs.getString("Email"),
                        rs.getString("Address"),
                        rs.getString("AvtImg"),
                        rs.getInt("ShopID"),
                        rs.getInt("Role")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật OTP
    public boolean updateOtp(String email, String otp) {
        String query = "UPDATE Users SET code = ? WHERE Email = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, otp);
            ps.setString(2, email);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("OTP successfully updated.");
                return true;
            } else {
                System.out.println("No rows updated. Email might not exist in the database.");
            }
        } catch (Exception e) {
            System.out.println("Error updating OTP: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Lấy OTP theo email
    public String getOtpByEmail(String email) {
        String query = "SELECT code FROM Users WHERE Email = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("code");
            }
        } catch (Exception e) {
            System.out.println("Error retrieving OTP: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Tạo tài khoản mới
    public boolean createAccount(Account account) {
        String query = "INSERT INTO Users (UserName, Pass, FullName, PhoneNumber, Email, Address, Role) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, account.getUserName());
            ps.setString(2, account.getPassword());
            ps.setString(3, account.getFullName());
            ps.setString(4, account.getPhoneNumber());
            ps.setString(5, account.getEmail());
            ps.setString(6, account.getAddress());
            ps.setInt(7, account.getRole());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật thông tin tài khoản
    public boolean updateAccount(Account account) {
        String query = "UPDATE Users SET UserName = ?, Pass = ?, FullName = ?, PhoneNumber = ?, Email = ?, Address = ? WHERE UserId = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, account.getUserName());
            ps.setString(2, account.getPassword());
            ps.setString(3, account.getFullName());
            ps.setString(4, account.getPhoneNumber());
            ps.setString(5, account.getEmail());
            ps.setString(6, account.getAddress());
            ps.setInt(7, account.getUserID());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Danh sách tài khoản
    public List<Account> getAccountList() {
        List<Account> accountList = new ArrayList<>();
        String query = "SELECT * FROM Users";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Account account = new Account(
                        rs.getInt("UserID"),
                        rs.getString("UserName"),
                        rs.getString("Pass"),
                        rs.getString("FullName"),
                        rs.getString("PhoneNumber"),
                        rs.getString("Email"),
                        rs.getString("Address"),
                        rs.getString("AvtImg"),
                        rs.getInt("ShopID"),
                        rs.getInt("Role")
                );
                accountList.add(account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accountList;
    }

    // Xóa tài khoản theo ID
    public boolean deleteUser(int userID) {
        String query = "DELETE FROM Users WHERE UserID = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }
}
