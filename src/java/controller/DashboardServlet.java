
/**
 *
 * @author LENOVO
 */
import com.google.gson.Gson;
import context.AccountDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import context.DBContext; 
import context.OrderDAO;
import context.PostDAO;
import context.ProductDAO;
import context.ShopDAO;
import jakarta.servlet.annotation.WebServlet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import model.Account;

@WebServlet(name = "DashBoardServlet", urlPatterns = {"/dashboard"})
public class DashboardServlet extends HttpServlet {
    private ShopDAO shopDao = new ShopDAO();
    private ProductDAO productDAO = new ProductDAO();
    private PostDAO postDao = new PostDAO();
    private OrderDAO orderDao = new OrderDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action== null){
            action = "showDashboard";
        }
        switch(action){
            case"showDashboard":
                showDashboard(request, response);
                break;
            case "exportReport":
                exportReport(request, response);
                break;
                default:
                    showDashboard(request, response);
                    break;
        }
    }
    
    private void showDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        DBContext dbContext = new DBContext(); 

        AccountDAO userDAO = new AccountDAO();
        List<Account> userList = userDAO.getAccountList();
        request.setAttribute("userList", userList);

        
        int restaurantCount = shopDao.getNumberOfRestaurants();
        request.setAttribute("restaurantCount", restaurantCount);

        
        int foodCount = productDAO.getNumberOfProducts();
        request.setAttribute("foodCount", foodCount);
        

        
        

        int postCount = postDao.getNumberOfPosts();
        request.setAttribute("postCount", postCount);

        
        
        double revenue = orderDao.getRevenue();
        request.setAttribute("revenue", revenue );

        
        
        // Chuyển đến dashboard.jsp
        request.getRequestDispatcher("WEB-INF/view/dashboard.jsp").forward(request, response);
    }
    
    private void exportReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
}
