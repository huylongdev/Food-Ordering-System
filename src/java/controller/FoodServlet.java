/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import context.CategoryDAO;
import context.ProductDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import model.Category;
import model.Product;

/**
 *
 * @author phuct
 */
@WebServlet(name = "FoodServlet", urlPatterns = {"/food"})
public class FoodServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/view/food.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAO productDAO = new ProductDAO();
        CategoryDAO categoryDAO = new CategoryDAO();

        // Pagination
        int page = 1; // Khởi tạo trang mặc định là 1
        int size = 9; // Số lượng sản phẩm trên mỗi trang

        // Kiểm tra tham số 'page' từ request
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1; // Nếu có lỗi định dạng, quay về trang 1
            }
        }

        // Lấy danh sách sản phẩm cho trang hiện tại
        List<Product> productList = productDAO.getProducts(page, size);
        int totalProducts = productDAO.getTotalProducts();
        int totalPages = (int) Math.ceil((double) totalProducts / size); // Tính tổng số trang

        request.setAttribute("productList", productList);
        request.setAttribute("totalProducts", totalProducts);
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", size);
        request.setAttribute("totalPages", totalPages);

        // Fetching categories
        try {
            if (categoryDAO.checkConnection()) {
                List<Category> categoryList = categoryDAO.getAllCategories();
                request.setAttribute("categoryList", categoryList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/view/food-homepage.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAO productDAO = new ProductDAO();
        String keyword = request.getParameter("keyword");
        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> categoryList = null;

        List<Product> products = productDAO.searchProducts(keyword);

        try {
            if (categoryDAO.checkConnection()) {
                System.out.println("Connected to category database.");
                categoryList = categoryDAO.getAllCategories();
            } else {
                System.out.println("Failed to connect to the category database.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }

        request.setAttribute("categoryList", categoryList);
        request.setAttribute("productList", products);
        request.setAttribute("keyword", keyword);
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/view/food-homepage.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
