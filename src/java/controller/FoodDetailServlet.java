/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import context.AccountDAO;
import context.FavouriteDAO;
import context.FeedbackDAO;
import context.ProductDAO;
import context.ProductImageDAO;
import context.ShopDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.Favourite;
import model.Feedback;
import model.FeedbackDTO;
import model.Product;
import model.ProductImage;
import model.Shop;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "FoodDetailServlet", urlPatterns = {"/food-detail"})
public class FoodDetailServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet FoodDetailServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FoodDetailServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        int id = Integer.parseInt(request.getParameter("productId"));
        Account user = (Account) request.getSession().getAttribute("user");
            ProductDAO pDAO = new ProductDAO();
            ProductImageDAO iDAO = new ProductImageDAO();
            FeedbackDAO fdao = new FeedbackDAO();
            AccountDAO adao = new AccountDAO();
            Product p = pDAO.getProductByID(id);
            List<ProductImage> images = iDAO.getListImageByProductID(id);
            request.setAttribute("images", images);
            String cateName = pDAO.getCategoryNameByID(id);
            
            ShopDAO sDAO = new ShopDAO();
            Shop shop = sDAO.getShopByID(p.getShopId());
            FavouriteDAO fDAO = new FavouriteDAO();
            if(user!=null){
            Favourite fav = fDAO.getFavouriteByUserIdandProductID(user.getUserID(),id);
            request.setAttribute("fav", fav);
            }
            
            List<Feedback> flist = fdao.getFeedbackByProductID(id);
            List<FeedbackDTO> flistdto = new ArrayList<>();
            for(Feedback f : flist){
                Account a = adao.getUserById(f.getUserId());
                flistdto.add(new FeedbackDTO(f.getFeedbackId(),f.getProductId(),f.getRating(),f.getComment(),a.getFullName(),f.getCreatedDate(),f.getUserId()));
            }
                
            request.setAttribute("shop", shop);
            request.setAttribute("flist", flistdto);
            
            request.setAttribute("cateName", cateName);
            request.setAttribute("p", p);
            request.setAttribute("ratingInt", (int) p.getRating());
        request.getRequestDispatcher("WEB-INF/view/food-detail.jsp").forward(request, response);
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
        doGet(request, response);
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
