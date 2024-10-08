/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package context;

import java.sql.*;

/**
 *
 * @author LENOVO
 */
public class DBContext {
    private static String driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String username = "sa";
    private static String password = "2104";
    private static String jdbcURL = "jdbc:sqlserver://YANG;databaseName=ordering_system;encrypt=true;trustServerCertificate=true;loginTimeout=30";
    //kết nối datebase dùng URL username, password. Thả ra lỗi 
    
    public static Connection getConnection(){
        Connection con = null;
        try{
            Class.forName(driverClass);
            con = (Connection) DriverManager.getConnection(jdbcURL, username, password);
        } catch (Exception e){
            System.out.println("Error: " + e);
        }
        return con;
    }

    public void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
}
