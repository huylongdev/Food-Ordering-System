package controller;

import context.AccountDAO;
import context.DiscountDAO;
import context.OrderDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.CartItem;
import model.CartItemDTO;
import model.Discount;

@WebServlet(name = "DiscountManagementServlet", urlPatterns = {"/discountManage"})
public class DiscountManagementServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DiscountManagementServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DiscountManagementServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account u = (Account) session.getAttribute("user");

        if (u == null) {
            response.sendRedirect("login");
            return;
        }

        OrderDAO oDAO = new OrderDAO();
        AccountDAO accDAO = new AccountDAO();
        DiscountDAO discountDAO = new DiscountDAO();
        int userID = u.getUserID();

        int shopID = accDAO.getShopIDByUserID(userID);

        List<Discount> discounts = discountDAO.getAllDiscountsByShopID(shopID);
        request.setAttribute("discounts", discounts);

        request.getRequestDispatcher("WEB-INF/view/discountManagement.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            switch (action) {
                case "createDiscount":
                    createDiscount(request, response);
                    break;
                case "updateDiscount":
                    updateDiscount(request, response);
                    break;
                case "deleteDiscount":
                    deleteDiscount(request, response);
                    break;
                case "applyDiscount":
                    applyDiscount(request, response);
                    break;
                case "unlockDiscount":
                    unlockDiscount(request, response);
                    break;
                default:
                    throw new Error();
            }
        } catch (Exception ex) {
            Logger.getLogger(DiscountManagementServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void applyDiscount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        AccountDAO accountDAO = new AccountDAO();
        DiscountDAO discountDAO = new DiscountDAO();

        String discountCode = request.getParameter("discount_code");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        int userID = Integer.parseInt(request.getParameter("userID"));
        Account account = accountDAO.getUserById(userID);

        Double discountPercentage = discountDAO.getDiscountPercentageByDiscountCode(discountCode);

        if (discountPercentage == null) {
            request.setAttribute("errorMessage", "The discount code is invalid or has expired.");
            request.getRequestDispatcher("WEB-INF/view/checkout.jsp").forward(request, response);
            return;
        }

        List<CartItemDTO> cart = (List<CartItemDTO>) session.getAttribute("cart");

        double total = 0;
        for (CartItemDTO item : cart) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }

        double discountAmount = total * (discountPercentage / 100);
        double totalAfterDiscount = total - discountAmount;

        try {
            int discountID = discountDAO.getDiscountIDByCode(discountCode);
            int currentTotalUse = discountDAO.getCurrentTotalUse(discountID);

            discountDAO.updateTotalUse(discountID, currentTotalUse + 1);

            int numberOfDiscount = discountDAO.getNumberOfDiscount(discountID);
            if (currentTotalUse + 1 >= numberOfDiscount) {
                discountDAO.deleteDiscount(discountID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("total", totalAfterDiscount);
        request.setAttribute("address", address);
        request.setAttribute("phone", phone);
        request.setAttribute("discountCode", discountCode);
        request.setAttribute("discountAmount", discountAmount);
        request.setAttribute("cart", cart);
        session.setAttribute("user", account);

        request.getRequestDispatcher("WEB-INF/view/checkout.jsp").forward(request, response);
    }

    protected void createDiscount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountDAO accDAO = new AccountDAO();
        String userIDParam = request.getParameter("userID");
        int userID = Integer.parseInt(userIDParam);
        String discountCode = request.getParameter("discountCode");
        int numberOfDiscount = Integer.parseInt(request.getParameter("numberOfDiscount"));
        double discountPercentage = Double.parseDouble(request.getParameter("discountPercentage"));
        int shopID = accDAO.getShopIDByUserID(userID);

        Discount discount = new Discount();
        discount.setUserID(userID);
        discount.setDiscountCODE(discountCode);
        discount.setNumberOfDiscount(numberOfDiscount);
        discount.setDiscountPercentage(discountPercentage);
        discount.setShopID(shopID);

        DiscountDAO discountDAO = new DiscountDAO();
        discountDAO.createDiscount(discount);

        response.sendRedirect("discountManage");
    }

    protected void updateDiscount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int discountID = Integer.parseInt(request.getParameter("discountID")); // Get the discount ID
            int numberOfDiscount = Integer.parseInt(request.getParameter("numberOfDiscount")); // Get the new number of vouchers
            double discountPercentage = Double.parseDouble(request.getParameter("discountPercentage")); // Get the new discount percentage

            Discount discount = new Discount();
            discount.setDiscountID(discountID);
            discount.setNumberOfDiscount(numberOfDiscount);
            discount.setDiscountPercentage(discountPercentage);

            DiscountDAO discountDAO = new DiscountDAO();
            discountDAO.updateDiscount(discount);

            response.sendRedirect("discountManage");
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid number format. Please check your input.");
            request.getRequestDispatcher("discountManage").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("discountManage").forward(request, response);
        }
    }

    protected void deleteDiscount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        int discountID = Integer.parseInt(request.getParameter("discountID"));
        DiscountDAO discountDAO = new DiscountDAO();
        discountDAO.deleteDiscount(discountID);
        response.sendRedirect("discountManage");
    }

    protected void unlockDiscount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        int discountID = Integer.parseInt(request.getParameter("discountID"));
        DiscountDAO discountDAO = new DiscountDAO();

        try {
            discountDAO.unlockDiscount(discountID);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error unlocking discount: " + e.getMessage());
            request.getRequestDispatcher("WEB-INF/view/errorPage.jsp").forward(request, response);
            return;
        }

        response.sendRedirect("discountManage");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
