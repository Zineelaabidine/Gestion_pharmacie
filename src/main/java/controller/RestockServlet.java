package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modele.Admin;
import util.DBConnection;

@WebServlet("/admin/restock")
public class RestockServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Vérifier si l'admin est connecté
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        if (admin == null) {
            response.sendRedirect("../login.jsp");
            return;
        }
        
        // Récupérer l'ID du médicament
        String medicamentId = request.getParameter("id_medicament");
        
        if (medicamentId != null && !medicamentId.isEmpty()) {
            try (Connection conn = DBConnection.getConnection()) {
                // Vérifier si le médicament existe déjà dans le stock
                String checkQuery = "SELECT id_stock, quantité FROM Stock WHERE id_médicament = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
                checkStmt.setInt(1, Integer.parseInt(medicamentId));
                ResultSet rs = checkStmt.executeQuery();
                
                if (rs.next()) {
                    // Mettre à jour le stock existant
                    int stockId = rs.getInt("id_stock");
                    int currentQuantity = rs.getInt("quantité");
                    int newQuantity = currentQuantity + 10; // Ajouter 10 unités par défaut
                    
                    String updateQuery = "UPDATE Stock SET quantité = ? WHERE id_stock = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                    updateStmt.setInt(1, newQuantity);
                    updateStmt.setInt(2, stockId);
                    updateStmt.executeUpdate();
                } else {
                    // Créer une nouvelle entrée de stock
                    String insertQuery = "INSERT INTO Stock (id_médicament, quantité, id_admin) VALUES (?, ?, ?)";
                    PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                    insertStmt.setInt(1, Integer.parseInt(medicamentId));
                    insertStmt.setInt(2, 10); // Ajouter 10 unités par défaut
                    insertStmt.setInt(3, admin.getId_admin());
                    insertStmt.executeUpdate();
                }
                
                // Rediriger vers la page d'accueil
                response.sendRedirect("admin_home.jsp");
                
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", "Une erreur est survenue lors du réapprovisionnement.");
                request.getRequestDispatcher("admin_home.jsp").forward(request, response);
            } catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        } else {
            // ID de médicament non valide
            response.sendRedirect("admin_home.jsp");
        }
    }
}