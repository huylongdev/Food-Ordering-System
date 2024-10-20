package controller;

import context.DiscountDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Discount;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "DiscountServlet", urlPatterns = {"/discount"})
public class DiscountServlet extends HttpServlet {

    private DiscountDAO discountDAO;

    @Override
    public void init() throws ServletException {
        discountDAO = new DiscountDAO(); 
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Discount> discounts = discountDAO.getAllDiscounts();
            request.setAttribute("discounts", discounts);
            
            String successMessage = (String) request.getAttribute("successMessage");
            request.setAttribute("successMessage", successMessage);

            
            request.getRequestDispatcher("WEB-INF/view/discount.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error retrieving discounts: " + e.getMessage());
            request.getRequestDispatcher("WEB-INF/view/error.jsp").forward(request, response); 
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            Discount discount = new Discount();
            discount.setUserID(Integer.parseInt(request.getParameter("userID")));
            discount.setDiscountName(request.getParameter("discountName"));
            discount.setStartDate(java.sql.Date.valueOf(request.getParameter("startDate"))); 
            discount.setEndDate(java.sql.Date.valueOf(request.getParameter("endDate"))); 
            discount.setDiscountPercentage(Double.parseDouble(request.getParameter("discountPercentage")));
            discount.setType(request.getParameter("type"));

            try {
                discountDAO.createDiscount(discount);
                request.setAttribute("successMessage", "Discount created successfully."); 
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Error creating discount: " + e.getMessage());
                request.getRequestDispatcher("WEB-INF/view/error.jsp").forward(request, response); 
                return; 
            }

            response.sendRedirect("discount"); 
        } else if ("update".equals(action)) {
    Discount discount = new Discount();
    discount.setDiscountID(Integer.parseInt(request.getParameter("discountID")));
    discount.setUserID(Integer.parseInt(request.getParameter("userID")));
    discount.setDiscountName(request.getParameter("discountName"));
    discount.setStartDate(java.sql.Date.valueOf(request.getParameter("startDate"))); 
    discount.setEndDate(java.sql.Date.valueOf(request.getParameter("endDate"))); 
    discount.setDiscountPercentage(Double.parseDouble(request.getParameter("discountPercentage")));
    discount.setType(request.getParameter("type"));

    try {
        discountDAO.updateDiscount(discount);
        request.setAttribute("successMessage", "Discount updated successfully."); 
        response.sendRedirect("discount"); 
    } catch (Exception e) {
        e.printStackTrace();
        
        request.setAttribute("errorMessage", "Error updating discount: " + e.getMessage());
        request.getRequestDispatcher("WEB-INF/view/error.jsp").forward(request, response); 
        return; 
    }
}else if ("delete".equals(action)) {
            
            int discountID = Integer.parseInt(request.getParameter("discountID"));
            try {
                discountDAO.deleteDiscount(discountID);
                request.setAttribute("successMessage", "Discount deleted successfully."); 
            } catch (Exception e) {
                e.printStackTrace();
                
                request.setAttribute("errorMessage", "Error deleting discount: " + e.getMessage());
                request.getRequestDispatcher("WEB-INF/view/error.jsp").forward(request, response); 
                return; 
            }
            response.sendRedirect("discount"); 
        } else {
            processRequest(request, response); 
        }
    }

    @Override
    public String getServletInfo() {
        return "Discount Servlet";
    }
}
