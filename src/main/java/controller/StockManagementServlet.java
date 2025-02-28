package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.StockDAO;
import modele.Admin;
import modele.Stock;

@WebServlet("/stock_management")
public class StockManagementServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StockDAO stockDAO;
    
    public StockManagementServlet() {
        super();
        stockDAO = new StockDAO();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Verify if admin is logged in
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        
        if (admin == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        // Get stock for the logged-in admin
        List<Stock> stockList = stockDAO.getAllStockByAdmin(admin.getId_admin());
        request.setAttribute("stockList", stockList);
        
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("stock_management.jsp");
        dispatcher.forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Verify if admin is logged in
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        
        if (admin == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String action = request.getParameter("action");
        String successMessage = "";
        String errorMessage = "";
        
        try {
            // Add new stock
            if ("add".equals(action)) {
                int id_médicament = Integer.parseInt(request.getParameter("id_médicament"));
                int quantité = Integer.parseInt(request.getParameter("quantité"));
                
                Stock newStock = new Stock();
                newStock.setId_médicament(id_médicament);
                newStock.setQuantité(quantité);
                newStock.setId_admin(admin.getId_admin()); // Associate with current admin
                
                boolean success = stockDAO.addStockItem(newStock);
                
                if (success) {
                    successMessage = "Le stock a été ajouté avec succès!";
                } else {
                    errorMessage = "Erreur lors de l'ajout du stock.";
                }
            }
            
            // Update stock
            else if ("update".equals(action)) {
                int id_stock = Integer.parseInt(request.getParameter("id_stock"));
                int id_médicament = Integer.parseInt(request.getParameter("id_médicament"));
                int quantité = Integer.parseInt(request.getParameter("quantité"));
                
                Stock existingStock = stockDAO.getStockById(id_stock);
                
                if (existingStock != null) {
                    existingStock.setId_médicament(id_médicament);
                    existingStock.setQuantité(quantité);
                    
                    boolean success = stockDAO.updateStockItem(existingStock);
                    
                    if (success) {
                        successMessage = "Le stock a été mis à jour avec succès!";
                    } else {
                        errorMessage = "Erreur lors de la mise à jour du stock.";
                    }
                } else {
                    errorMessage = "Stock non trouvé.";
                }
            }
            
            // Delete stock
            else if ("delete".equals(action)) {
                int id_stock = Integer.parseInt(request.getParameter("id_stock"));
                
                boolean success = stockDAO.deleteStockItem(id_stock);
                
                if (success) {
                    successMessage = "Le stock a été supprimé avec succès!";
                } else {
                    errorMessage = "Erreur lors de la suppression du stock.";
                }
            }
            
        } catch (Exception e) {
            errorMessage = "Une erreur s'est produite: " + e.getMessage();
            e.printStackTrace();
        }
        
        // Set attributes for the response
        request.setAttribute("successMessage", successMessage);
        request.setAttribute("errorMessage", errorMessage);
        
        // Reload the stock list and forward to the JSP
        List<Stock> stockList = stockDAO.getAllStockByAdmin(admin.getId_admin());
        request.setAttribute("stockList", stockList);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("stock_management.jsp");
        dispatcher.forward(request, response);
    }
}
