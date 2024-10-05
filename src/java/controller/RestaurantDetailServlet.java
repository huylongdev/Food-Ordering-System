/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import context.ProductDAO;
import context.ProductImageDAO;
import context.ShopDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.Product;
import model.ProductDTO;
import model.ProductImage;
import model.Shop;

/**
 *
 * @author LENOVO
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class RestaurantDetailServlet extends HttpServlet {

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
            out.println("<title>Servlet RestaurantDetailServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RestaurantDetailServlet at " + request.getContextPath() + "</h1>");
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
        
        int shopId = Integer.parseInt(request.getParameter("shopId"));
        ProductDAO pDAO = new ProductDAO();
            
            List<Product> foodList = pDAO.getProductByShopID(shopId);
            List<ProductDTO> productList = new ArrayList<>();
            ProductImageDAO pid= new ProductImageDAO();
            for(Product p: foodList){
                ProductDTO pd = new ProductDTO(p, pid.getAvatarProductImageByID(p.getProductId()).getImgURL());
                productList.add(pd);
            }
            
            ShopDAO sDAO = new ShopDAO();
            Shop s = sDAO.getShopByID(shopId);
            
            
            request.setAttribute("shop", s);
            request.setAttribute("productList", productList);
        request.getRequestDispatcher("WEB-INF/view/shop.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private static final String SAVE_DIR = "foodImages";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String mt = request.getParameter("mt");
        if (mt != null && mt.equalsIgnoreCase("update")) {
            updateBookInformation(request, response);
        } else if (mt != null && mt.equalsIgnoreCase("delete")) {
            deleteBook(request, response);
        } else {
            addBook(request, response);
        }
    }
    
    private void addBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String appPath = request.getServletContext().getRealPath("").replace("build\\web", "web");
        String savePath = appPath + File.separator + SAVE_DIR;

        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
        
        
        ProductDAO pDAO = new ProductDAO();
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        int category = Integer.parseInt(request.getParameter("category"));
        String description = request.getParameter("description");
        int shopID = Integer.parseInt(request.getParameter("shopID"));
        Product p = new Product(name, description, price, true, shopID, category);
        int productID = pDAO.createProductGetID(p);

        
        ProductImageDAO pid = new ProductImageDAO();

        
        Part filePart = request.getPart("img");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String relativePath;
        if (filePart != null && filePart.getSize() > 0) {
            String filePath = savePath + File.separator + fileName;
            filePart.write(filePath);
            relativePath = SAVE_DIR + File.separator + fileName;
                
                pid.insertProductImage(new ProductImage(productID, true, relativePath));
            }
        
        
        response.sendRedirect("restaurant-detail?shopId="+ shopID);
    }
    
    
    private void updateBookInformation(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
    
    private void deleteBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
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
