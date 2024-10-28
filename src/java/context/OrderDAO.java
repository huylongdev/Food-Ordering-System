/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package context;

/**
 *
 * @author phuct
 */
import model.OrderDTO;
import model.OrderItemDTO;
import model.Account;
import model.CartItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Order;
import model.Product;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class OrderDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public OrderDTO createOrder(int orderID, String paymentID, Account account, List<CartItem> cartItems, String paymentOption,
            String address, String paymentStatus, String deliveryStatus, String deliveryOption, String timePickupString, String phone) throws Exception {
        Date timePickup = null;

        try {
            if (timePickupString != null && !timePickupString.isEmpty()) {
                timePickup = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(timePickupString);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new Exception("Invalid time format: " + timePickupString, e);
        }

        java.util.Date createdDate = new java.util.Date();

        String insertOrderSql = "INSERT INTO [Order] (OrderID, PaymentID, UserID, PaymentStatus, DeliveryStatus, Address, CreatedDate, TotalAmount, PaymentOption, DeliveryOption, TimePickup, Phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String insertOrderItemSql = "INSERT INTO OrderItem (OrderID, ProductID, Quantity, TotalPrice) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        OrderDTO order = null;

        try {
            conn = new DBContext().getConnection();
            conn.setAutoCommit(false);

            double totalAmount = 0;
            for (CartItem item : cartItems) {
                totalAmount += item.getQuantity() * item.getProduct().getPrice();
            }

            // Ghi đơn hàng vào cơ sở dữ liệu
            try (PreparedStatement ps = conn.prepareStatement(insertOrderSql)) {
                ps.setInt(1, orderID);
                ps.setString(2, paymentID);
                ps.setInt(3, account.getUserID());
                ps.setString(4, paymentStatus);
                ps.setString(5, deliveryStatus);
                ps.setString(6, address);
                ps.setTimestamp(7, new java.sql.Timestamp(createdDate.getTime()));
                ps.setDouble(8, totalAmount); // Đảm bảo tổng tiền đã được cập nhật
                ps.setString(9, paymentOption);
                ps.setString(10, deliveryOption);
                ps.setTimestamp(11, timePickup != null ? new java.sql.Timestamp(timePickup.getTime()) : null);
                ps.setString(12, phone);

                ps.executeUpdate();
            }

            // Ghi các mục đơn hàng vào cơ sở dữ liệu
            for (CartItem item : cartItems) {
                try (PreparedStatement ps = conn.prepareStatement(insertOrderItemSql)) {
                    ps.setInt(1, orderID);
                    ps.setInt(2, item.getProduct().getProductId());
                    ps.setInt(3, item.getQuantity());
                    ps.setDouble(4, item.getQuantity() * item.getProduct().getPrice());
                    ps.executeUpdate();
                }
            }

            conn.commit();
            order = new OrderDTO(orderID, account, totalAmount, paymentOption, paymentStatus, deliveryStatus, address, createdDate.toString(), phone);

        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return order;
    }

    public void updateOrderTotalAmount(int orderID, double totalAmount) throws SQLException, Exception {
        String sql = "UPDATE [Order] SET TotalAmount = ? WHERE OrderID = ?";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, totalAmount);
            ps.setInt(2, orderID);
            ps.executeUpdate();
        }
    }

    public List<OrderDTO> getOrderInfo() {
        List<OrderDTO> list = new ArrayList<>();
        String sql = "SELECT o.OrderID, u.UserName, o.Address, o.CreatedDate, o.TotalAmount, o.PaymentOption "
                + "FROM [Order] o INNER JOIN Users u ON o.UserID = u.UserID";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Account account = new Account(rs.getString(2));
                list.add(new OrderDTO(rs.getInt(1), account, rs.getString(3), rs.getTimestamp(4), rs.getDouble(5), rs.getString(6)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the connection
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

    public List<OrderItemDTO> getOrderItems(int orderId) {
        List<OrderItemDTO> list = new ArrayList<>();
        String sql = "SELECT oi.ProductID, p.ProductName, oi.Quantity, oi.TotalPrice "
                + "FROM OrderItem oi INNER JOIN Product p ON oi.ProductID = p.ProductID "
                + "WHERE oi.OrderID = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product(rs.getInt(1), rs.getString(2));
                list.add(new OrderItemDTO(orderId, product, rs.getInt(3), rs.getDouble(4)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the connection
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

    public OrderDTO getLatestOrder(int userId) {
        OrderDTO latestOrder = null;
        String sql = "SELECT TOP 1 * FROM [Order] WHERE UserID = ? ORDER BY CreatedDate DESC";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                latestOrder = new OrderDTO();
                latestOrder.setOrderId(rs.getInt("OrderID"));
                latestOrder.setUser(new Account());
                latestOrder.getUser().setUserID(userId);
                latestOrder.setAddress(rs.getString("Address"));
                latestOrder.setCreatedDate(rs.getTimestamp("CreatedDate"));
                latestOrder.setTotalAmount(rs.getDouble("TotalAmount"));
                latestOrder.setPaymentOption(rs.getString("PaymentOption"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return latestOrder;
    }

    public void updateOrderPaymentStatus(String paymentID, String status) throws Exception {
        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement("UPDATE [Order] SET PaymentStatus = ? WHERE PaymentID = ?")) {

            ps.setString(1, status);
            ps.setString(2, paymentID);
            int updatedRows = ps.executeUpdate();

            if (updatedRows == 0) {
                System.out.println("No order found with PaymentID: " + paymentID);
            } else {
                System.out.println("Updated PaymentID: " + paymentID + " to status: " + status);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateOrderPaymentStatusByOrderID(int orderID, String status) throws Exception {
        String sql = "UPDATE [Order] SET DeliveryStatus = ? WHERE OrderID = ?";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, orderID);
            int updatedRows = ps.executeUpdate();

            if (updatedRows == 0) {
                System.out.println("No order found with OrderID: " + orderID);
            } else {
                System.out.println("Updated OrderID: " + orderID + " to status: " + status);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateOrderDeliveryStatus(String paymentID, String status) throws Exception {
        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement("UPDATE [Order] SET DeliveryStatus = ? WHERE PaymentID = ?")) {

            ps.setString(1, status);
            ps.setString(2, paymentID);
            int updatedRows = ps.executeUpdate();

            if (updatedRows == 0) {
                System.out.println("No order found with PaymentID: " + paymentID);
            } else {
                System.out.println("Updated PaymentID: " + paymentID + " to status: " + status);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateRefundStatus(String paymentID, int status) throws Exception {
        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement("UPDATE [Order] SET isRefund = ? WHERE PaymentID = ?")) {

            ps.setInt(1, status);
            ps.setString(2, paymentID);
            int updatedRows = ps.executeUpdate();

            if (updatedRows == 0) {
                System.out.println("No order found with PaymentID: " + paymentID);
            } else {
                System.out.println("Updated PaymentID: " + paymentID + " to status: " + status);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private DBContext dbContext;

    public OrderDAO() {
        dbContext = new DBContext();
    }

    public List<Order> getOrderListByUserID(int userId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM [Order] WHERE UserID = ? ORDER BY CreatedDate DESC";
        try (Connection con = dbContext.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("OrderID"),
                        rs.getInt("UserID"),
                        rs.getString("PaymentStatus"),
                        rs.getString("DeliveryStatus"),
                        rs.getString("Address"),
                        rs.getDate("CreatedDate"),
                        rs.getString("DeliveryOption"),
                        rs.getTimestamp("TimePickup"),
                        rs.getDouble("TotalAmount"),
                        rs.getInt("DiscountID"),
                        rs.getString("PaymentOption"),
                        rs.getInt("isRefund"),
                        rs.getString("Phone")
                );
                orders.add(order);
            }
            return orders;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("No orders found for the user");
        return null;
    }

    public List<Order> getOrderListByShopID(int shopId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT DISTINCT o.* "
                + "FROM [Order] o "
                + "JOIN OrderItems oi ON o.OrderID = oi.OrderID "
                + "JOIN Product p ON oi.ProductID = p.ProductID "
                + "WHERE p.ShopID = ? "
                + "ORDER BY o.CreatedDate DESC";
        try (Connection con = dbContext.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, shopId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("OrderID"),
                        rs.getInt("UserID"),
                        rs.getString("PaymentStatus"),
                        rs.getString("DeliveryStatus"),
                        rs.getString("Address"),
                        rs.getDate("CreatedDate"),
                        rs.getString("DeliveryOption"),
                        rs.getTimestamp("TimePickup"),
                        rs.getDouble("TotalAmount"),
                        rs.getInt("DiscountID"),
                        rs.getString("PaymentOption"),
                        rs.getInt("isRefund"),
                        rs.getString("Phone")
                );
                orders.add(order);
            }
            return orders;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("No orders found for the shop");
        return null;
    }

    public List<Order> getOrderListByShopIDAndStatus(int shopId, String status) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT DISTINCT o.* "
                + "FROM [Order] o "
                + "JOIN OrderItem oi ON o.OrderID = oi.OrderID "
                + "JOIN Product p ON oi.ProductID = p.ProductID "
                + "WHERE p.ShopID = ? AND o.DeliveryStatus = ? AND o.PaymentStatus LIKE 'PAID'"
                + "ORDER BY o.CreatedDate DESC";
        try (Connection con = dbContext.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, shopId);
            ps.setString(2, status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("OrderID"),
                        rs.getInt("UserID"),
                        rs.getString("PaymentStatus"),
                        rs.getString("DeliveryStatus"),
                        rs.getString("Address"),
                        rs.getDate("CreatedDate"),
                        rs.getString("DeliveryOption"),
                        rs.getTimestamp("TimePickup"),
                        rs.getDouble("TotalAmount"),
                        rs.getInt("DiscountID"),
                        rs.getString("PaymentOption"),
                        rs.getInt("isRefund"),
                        rs.getString("Phone")
                );
                orders.add(order);
            }
            return orders;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("No orders found for the shop with the specified status");
        return null;
    }

    public Order getOrderByOrderID(int orderId) {
        String query = "SELECT * FROM [Order] WHERE OrderID = ?";
        try (Connection con = dbContext.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Order order = new Order(
                        rs.getInt("OrderID"),
                        rs.getInt("UserID"),
                        rs.getString("PaymentStatus"),
                        rs.getString("DeliveryStatus"),
                        rs.getString("Address"),
                        rs.getDate("CreatedDate"),
                        rs.getString("DeliveryOption"),
                        rs.getTimestamp("TimePickup"),
                        rs.getDouble("TotalAmount"),
                        rs.getInt("DiscountID"),
                        rs.getString("PaymentOption"),
                        rs.getInt("isRefund"),
                        rs.getString("Phone")
                );
                return order;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("No order found with the specified OrderID");
        return null;
    }

    public int getUserIDByOrderID(int orderId) {
        int UserId = 0;
        String query = "SELECT UserID FROM [Order] WHERE OrderID = ?";
        try (Connection con = dbContext.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                UserId = rs.getInt("UserID");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("No order found with the specified OrderID");
        return UserId;
    }
    
    public int getTotalByOrderID(int orderId) {
        int TotalAmount = 0;
        String query = "SELECT TotalAmount FROM [Order] WHERE OrderID = ?";
        try (Connection con = dbContext.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                TotalAmount = rs.getInt("TotalAmount");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("No order found with the specified OrderID");
        return TotalAmount;
    }

    public boolean updateOrderStatus(int orderId, String status) {
        String query = "UPDATE [Order] SET DeliveryStatus = ? WHERE OrderID = ?";
        try (Connection con = dbContext.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getPaymentIDByOrderID(int orderId) {
        String paymentID = null;
        String sql = "SELECT PaymentID FROM [Order] WHERE OrderID = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();

            if (rs.next()) {
                paymentID = rs.getString("PaymentID");
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

        return paymentID;
    }

    public int getOrderIDByPaymentID(String paymentID) {
        int orderID = 0;
        String sql = "SELECT OrderID FROM [Order] WHERE PaymentID = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, paymentID);
            rs = ps.executeQuery();

            if (rs.next()) {
                orderID = rs.getInt("OrderID");
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

        return orderID;
    }

    public String getEmailByOrderID(int orderID) {
        String email = null;
        String sql = "SELECT Email FROM Users WHERE UserID = (SELECT UserID FROM [Order] WHERE OrderID = ?)";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    email = rs.getString("Email");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return email;
    }

    public String getDeliveryStatusByOrderID(int orderId) {
        String deliveryStatus = null;
        String sql = "SELECT DeliveryStatus FROM [Order] WHERE OrderID = ?";

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();

            if (rs.next()) {
                deliveryStatus = rs.getString("DeliveryStatus");
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

        return deliveryStatus;
    }

}
