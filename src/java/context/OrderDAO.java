/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package context;

/**
 *
 * @author phuct
 */
import context.DBContext;
import model.OrderDTO;
import model.OrderItem;
import model.Account;
import model.CartItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Product;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class OrderDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public void addOrder(Account account, List<CartItem> cartItems, String paymentOption, String address) {
        LocalDate curDate = LocalDate.now();
        String createdDate = curDate.toString();

        String insertOrderSql = "INSERT INTO [Order] (UserID, Status, Address, CreatedDate, TotalAmount, PaymentOption) VALUES (?, ?, ?, ?, ?, ?)";
        String selectOrderIdSql = "SELECT TOP 1 OrderID FROM [Order] ORDER BY OrderID DESC";
        String insertOrderItemSql = "INSERT INTO OrderItem (OrderID, ProductID, Quantity, TotalPrice) VALUES (?, ?, ?, ?)";

        Connection conn = null;

        try {
            conn = new DBContext().getConnection();
            conn.setAutoCommit(false);

            double totalAmount = 0;
            for (CartItem item : cartItems) {
                totalAmount += item.getQuantity() * item.getProduct().getPrice();
            }

            try (PreparedStatement ps = conn.prepareStatement(insertOrderSql)) {
                ps.setInt(1, account.getUserID());
                ps.setString(2, "Pending"); 
                ps.setString(3, address);
                ps.setString(4, createdDate);
                ps.setDouble(5, totalAmount);
                ps.setString(6, paymentOption);
                ps.executeUpdate();
            }

            int orderId;
            try (PreparedStatement ps = conn.prepareStatement(selectOrderIdSql); ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    orderId = rs.getInt(1);
                } else {
                    throw new RuntimeException("Unable to retrieve OrderID.");
                }
            }

            for (CartItem item : cartItems) {
                try (PreparedStatement ps = conn.prepareStatement(insertOrderItemSql)) {
                    ps.setInt(1, orderId);
                    ps.setInt(2, item.getProduct().getProductId());
                    ps.setInt(3, item.getQuantity());
                    ps.setDouble(4, item.getQuantity() * item.getProduct().getPrice());
                    ps.executeUpdate();
                }
            }

            conn.commit(); 

        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();  
                } catch (Exception rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);  
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public OrderDTO createOrder(int orderID, Account account, List<CartItem> cartItems, String paymentOption, String address, String status) {
        LocalDate curDate = LocalDate.now();
        String createdDate = curDate.toString();

        String insertOrderSql = "INSERT INTO [Order] (OrderID, UserID, Status, Address, CreatedDate, TotalAmount, PaymentOption) VALUES (?, ?, ?, ?, ?, ?, ?)";
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

            try (PreparedStatement ps = conn.prepareStatement(insertOrderSql)) {
                ps.setInt(1, orderID);
                ps.setInt(2, account.getUserID());
                ps.setString(3, status);
                ps.setString(4, address);
                ps.setString(5, createdDate);
                ps.setDouble(6, totalAmount);
                ps.setString(7, paymentOption);
                ps.executeUpdate();
            }

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

            order = new OrderDTO(orderID, account, totalAmount, paymentOption, status, address, createdDate);

        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (Exception rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return order;
    }

    public OrderDTO createOrder(int orderID, Account account, List<CartItem> cartItems, String paymentOption,
            String address, String status, String deliveryOption, String timePickupString) throws Exception {

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

        String insertOrderSql = "INSERT INTO [Order] (OrderID, UserID, Status, Address, CreatedDate, TotalAmount, PaymentOption, DeliveryOption, TimePickup) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
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

            try (PreparedStatement ps = conn.prepareStatement(insertOrderSql)) {
                ps.setInt(1, orderID);
                ps.setInt(2, account.getUserID());
                ps.setString(3, status);
                ps.setString(4, address);
                ps.setTimestamp(5, new java.sql.Timestamp(createdDate.getTime()));
                ps.setDouble(6, totalAmount);
                ps.setString(7, paymentOption);
                ps.setString(8, deliveryOption);
                ps.setTimestamp(9, timePickup != null ? new java.sql.Timestamp(timePickup.getTime()) : null);

                ps.executeUpdate();
            }

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
            order = new OrderDTO(orderID, account, totalAmount, paymentOption, status, address, createdDate.toString());

        } catch (SQLException e) {
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
                list.add(new OrderDTO(rs.getInt(1), account, rs.getString(3), rs.getDate(4), rs.getDouble(5), rs.getString(6)));
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

    public List<OrderItem> getOrderItems(int orderId) {
        List<OrderItem> list = new ArrayList<>();
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
                list.add(new OrderItem(orderId, product, rs.getInt(3), rs.getDouble(4)));
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

    public void updateOrderPaymentStatus(int orderId, String status) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = new DBContext().getConnection();
            String sql = "UPDATE [Order] SET Status = ? WHERE OrderID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, orderId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

}
