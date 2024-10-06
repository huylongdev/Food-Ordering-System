package context;

import context.DBContext;
import model.Bill;
import model.BillDetail;
import model.CartItemDTO;
import model.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.CartItem;
import model.Product;

public class BillDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public Bill getBill() {
        Bill bill = null;
        String sql = "SELECT b.bill_id, a.userName, b.phone, b.address, b.date, b.total, b.payment "
                + "FROM bill b INNER JOIN Account a ON b.user_id = a.userID "
                + "ORDER BY b.date DESC LIMIT 1";  // Giả sử bạn muốn lấy hóa đơn mới nhất

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                Account account = new Account(rs.getString("userName"));
                bill = new Bill(rs.getInt("bill_id"), account, rs.getFloat("total"), rs.getString("payment"),
                        rs.getString("address"), rs.getDate("date"), rs.getInt("phone"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bill;
    }

    public void addOrder(Account account, CartItem cartItem, String payment, String address, int phoneNumber) {
        LocalDate curDate = LocalDate.now();
        String date = curDate.toString();

        // Tính tổng tiền cho sản phẩm trong giỏ hàng
        double totalAmount = cartItem.getQuantity() * cartItem.getProduct().getPrice();

        // SQL cho việc chèn hóa đơn và chi tiết hóa đơn
        String insertBillSql = "INSERT INTO [bill] (user_id, total, payment, address, date, phone) VALUES (?, ?, ?, ?, ?, ?)";
        String selectBillIdSql = "SELECT TOP 1 bill_id FROM [bill] ORDER BY bill_id DESC";
        String insertBillDetailSql = "INSERT INTO [bill_detail] (bill_id, product_id, quantity, total) VALUES (?, ?, ?, ?)";
        String updateProductSql = "UPDATE product SET quantity = quantity - ? WHERE product_id = ?";

        Connection conn = null;  // Khởi tạo kết nối ngoài try-catch để sử dụng trong rollback

        try {
            conn = new DBContext().getConnection();
            conn.setAutoCommit(false);  // Bắt đầu transaction

            // Chèn hóa đơn vào bảng bill
            try (PreparedStatement ps = conn.prepareStatement(insertBillSql)) {
                ps.setInt(1, account.getUserID());  // Lấy user_id từ account
                ps.setDouble(2, totalAmount);       // Tổng tiền hóa đơn
                ps.setString(3, payment);           // Phương thức thanh toán
                ps.setString(4, address);           // Địa chỉ giao hàng
                ps.setString(5, date);              // Ngày tạo hóa đơn
                ps.setInt(6, phoneNumber);          // Số điện thoại
                ps.executeUpdate();                 // Thực hiện câu lệnh SQL
            }

            // Lấy bill_id vừa được tạo
            int bill_id;
            try (PreparedStatement ps = conn.prepareStatement(selectBillIdSql); ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    bill_id = rs.getInt(1);  // Lấy bill_id mới nhất
                } else {
                    throw new RuntimeException("Không thể lấy bill_id đã tạo.");
                }
            }

            // Chèn chi tiết của hóa đơn vào bảng bill_detail
            try (PreparedStatement ps = conn.prepareStatement(insertBillDetailSql)) {
                double total = cartItem.getQuantity() * cartItem.getProduct().getPrice();  // Tính tổng cho sản phẩm
                ps.setInt(1, bill_id);  // Gán bill_id
                ps.setInt(2, cartItem.getProduct().getProductId());  // Gán product_id
                ps.setInt(3, cartItem.getQuantity());  // Gán số lượng sản phẩm
                ps.setDouble(4, total);  // Gán tổng tiền cho sản phẩm này
                ps.executeUpdate();  // Thực hiện chèn chi tiết hóa đơn
            }

            // Cập nhật lại số lượng sản phẩm trong bảng product
            try (PreparedStatement ps = conn.prepareStatement(updateProductSql)) {
                ps.setInt(1, cartItem.getQuantity());  // Số lượng sản phẩm mua
                ps.setInt(2, cartItem.getProduct().getProductId());  // ID của sản phẩm
                ps.executeUpdate();  // Cập nhật số lượng sản phẩm
            }

            conn.commit();  // Xác nhận transaction nếu tất cả thành công

        } catch (Exception e) {
            e.printStackTrace();
            // Nếu xảy ra lỗi, rollback tất cả các thay đổi
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (Exception rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
        } finally {
            // Đóng kết nối
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);  // Khôi phục lại trạng thái auto-commit
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Bill> getBillInfo() {
        List<Bill> list = new ArrayList<>();
        String sql = "SELECT b.bill_id, a.userName, b.phone, b.address, b.date, b.total, b.payment "
                + "FROM bill b INNER JOIN Account a ON b.user_id = a.userID";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Account account = new Account(rs.getString(2));
                list.add(new Bill(rs.getInt(1), account, rs.getFloat(6), rs.getString(7), rs.getString(4), rs.getDate(5), rs.getInt(3)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List<BillDetail> getDetail(int bill_id) {
        List<BillDetail> list = new ArrayList<>();
        String sql = "SELECT d.detail_id, d.bill_id, p.product_id, p.product_name, d.quantity, d.price "
                + "FROM bill_detail d "
                + "INNER JOIN product p ON d.product_id = p.product_id "
                + "WHERE d.bill_id = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, bill_id);  // Sử dụng bill_id để lọc theo mã hóa đơn
            rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product(rs.getInt(3), rs.getString(4));

                CartItem cartItem = new CartItem(product, rs.getInt(6));

                list.add(new BillDetail(rs.getInt(1), rs.getInt(2), cartItem, product, rs.getInt(6), rs.getDouble(7)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public void updatePayment(String payment, int bill_id) {
        String sql = "UPDATE bill SET payment = ? WHERE bill_id = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, payment);
            ps.setInt(2, bill_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<Bill> getBillByID(int user_id) {
        List<Bill> list = new ArrayList<>();
        String sql = "SELECT bill_id, date, total, payment, address, phone FROM bill WHERE user_id = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, user_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Bill(rs.getInt(1), rs.getFloat(3), rs.getString(4), rs.getString(5), rs.getDate(2), rs.getInt(6)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List<Bill> getBillByDay() {
        List<Bill> list = new ArrayList<>();
        String sql = "SELECT b.bill_id, a.userName, b.phone, b.address, b.date, b.total, b.payment "
                + "FROM bill b INNER JOIN Account a ON b.user_id = a.userID WHERE date = CAST(GETDATE() AS Date)";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Account account = new Account(rs.getString(2));
                list.add(new Bill(rs.getInt(1), account, rs.getFloat(6), rs.getString(7), rs.getString(4), rs.getDate(5), rs.getInt(3)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
