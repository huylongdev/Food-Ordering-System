
/**
 *
 * @author LENOVO
 */
import context.AccountDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import context.DBContext; 
import jakarta.servlet.annotation.WebServlet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Account;

@WebServlet(name = "DashBoardServlet", urlPatterns = {"/dashboard"})
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        

        AccountDAO userDAO = new AccountDAO();
        List<Account> userList = userDAO.getAccountList();
        request.setAttribute("userList", userList);

        
        int restaurantCount = 0;
        DBContext dbContext = new DBContext(); 
        try (Connection connection = dbContext.getConnection()) { 
            String sql = "SELECT COUNT(*) FROM Shop"; 
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                restaurantCount = resultSet.getInt(1); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        request.setAttribute("restaurantCount", restaurantCount);

        
        int foodCount = 0; 
        try (Connection connection = dbContext.getConnection()) { 
            String sql = "SELECT COUNT(*) FROM Product"; 
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                foodCount = resultSet.getInt(1); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        request.setAttribute("foodCount", foodCount);

        int postCount = 0;
        try (Connection connection = dbContext.getConnection()) { 
            String sql = "SELECT COUNT(*) FROM Post"; 
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                postCount = resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("postCount", postCount);

        
        
        int billCount = 0;
        try (Connection connection = dbContext.getConnection()) { 
            String sql = "SELECT COUNT(*) FROM Bill"; 
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                postCount = resultSet.getInt(1); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("billCount", postCount);

        
        // Chuyển đến dashboard.jsp
        request.getRequestDispatcher("WEB-INF/view/dashboard.jsp").forward(request, response);
    }
}
