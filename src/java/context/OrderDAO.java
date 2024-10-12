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
import model.OrderItemDTO;
import model.Account;
import model.CartItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Order;
import model.Product;

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
            conn.setAutoCommit(false);  // Begin transaction

            // Calculate total amount for the order
            double totalAmount = 0;
            for (CartItem item : cartItems) {
                totalAmount += item.getQuantity() * item.getProduct().getPrice();
            }

            // Insert order into the Order table
            try (PreparedStatement ps = conn.prepareStatement(insertOrderSql)) {
                ps.setInt(1, account.getUserID());
                ps.setString(2, "Pending");  // Set initial status
                ps.setString(3, address);
                ps.setString(4, createdDate);
                ps.setDouble(5, totalAmount);
                ps.setString(6, paymentOption);
                ps.executeUpdate();
            }

            // Get the generated OrderID
            int orderId;
            try (PreparedStatement ps = conn.prepareStatement(selectOrderIdSql); ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    orderId = rs.getInt(1);
                } else {
                    throw new RuntimeException("Unable to retrieve OrderID.");
                }
            }

            // Insert order items into the OrderItem table
            for (CartItem item : cartItems) {
                try (PreparedStatement ps = conn.prepareStatement(insertOrderItemSql)) {
                    ps.setInt(1, orderId);
                    ps.setInt(2, item.getProduct().getProductId());
                    ps.setInt(3, item.getQuantity());
                    ps.setDouble(4, item.getQuantity() * item.getProduct().getPrice());
                    ps.executeUpdate();
                }
            }

            conn.commit();  // Commit transaction

        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();  // Rollback transaction on error
                } catch (Exception rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
        } finally {
            // Close the connection
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);  // Restore auto-commit mode
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

    public OrderDTO createOrder(int orderID, Account account, List<CartItem> cartItems, String paymentOption, String address, String status, String deliveryOption, Date timePickup) {
        LocalDate curDate = LocalDate.now();
        String createdDate = curDate.toString();

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
                ps.setString(5, createdDate);
                ps.setDouble(6, totalAmount);
                ps.setString(7, paymentOption);
                ps.setString(8, deliveryOption);
                ps.setDate(9, (java.sql.Date) timePickup);

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
                // Assuming you have a constructor in Product that takes ProductID and ProductName
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
                    rs.getString("Status"),
                    rs.getString("Address"),
                    rs.getDate("CreatedDate"),
                    rs.getString("DeliveryOption"),
                    rs.getDate("TimePickup"),
                    rs.getDouble("TotalAmount"),
                    rs.getInt("DiscountID"),
                    rs.getString("PaymentOption")
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


}
