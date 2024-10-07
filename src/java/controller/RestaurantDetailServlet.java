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
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "RestaurantDetailServlet", urlPatterns = {"/restaurant-detail"})
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
            
            List<Product> foodList = pDAO.getProductByShopIDInPage(shopId, page, size);
            List<ProductDTO> productList = new ArrayList<>();
            ProductImageDAO pid= new ProductImageDAO();
            for(Product p: foodList){
                ProductDTO pd = new ProductDTO(p, pid.getAvatarProductImageByID(p.getProductId()).getImgURL());
                productList.add(pd);
            }
            
            ShopDAO sDAO = new ShopDAO();
            Shop s = sDAO.getShopByID(shopId);
            
            
        
        int totalProducts = pDAO.getProductByShopID(shopId).size();
        int totalPages = (int) Math.ceil((double) totalProducts / size); // Tính tổng số trang

        request.setAttribute("totalProducts", totalProducts);
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", size);
        request.setAttribute("totalPages", totalPages);
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
            updateProduct(request, response);
        } else if (mt != null && mt.equalsIgnoreCase("delete")) {
            deleteBook(request, response);
        } else {
            addProduct(request, response);
        }
    }
    
    private void addProduct(HttpServletRequest request, HttpServletResponse response)
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
    
    
    private void updateProduct(HttpServletRequest request, HttpServletResponse response)
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
        int productID = Integer.parseInt(request.getParameter("productID"));
        Product p = new Product(productID, name, description, price, true, shopID, category);
        try {
            pDAO.updateProduct(p);
        } catch (Exception ex) {
            System.out.println("db error update");
        }
        

        
        ProductImageDAO pid = new ProductImageDAO();

        
        Part filePart = request.getPart("img");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String relativePath;
        if (filePart != null && filePart.getSize() > 0) {
            String filePath = savePath + File.separator + fileName;
            filePart.write(filePath);
            relativePath = SAVE_DIR + File.separator + fileName;
                
                pid.updateProductImage(new ProductImage(productID, true, relativePath));
            }
        
        
        response.sendRedirect("restaurant-detail?shopId="+ shopID);
    }
    
    private void deleteBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        
        ProductImageDAO pid = new ProductImageDAO();
        pid.deleteProductImageByProductID(productId);
        int shopID = Integer.parseInt(request.getParameter("shopID"));
        ProductDAO pDAO = new ProductDAO();
        try {
            pDAO.deleteProduct(productId);
        } catch (Exception ex) {
            Logger.getLogger(RestaurantDetailServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        response.sendRedirect("restaurant-detail?shopId="+ shopID);
        
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
