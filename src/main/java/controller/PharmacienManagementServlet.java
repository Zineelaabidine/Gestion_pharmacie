package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.PharmacienDAO;
import modele.Admin;
import modele.Pharmacien;

@WebServlet("/pharmacien_management")
public class PharmacienManagementServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PharmacienDAO pharmacienDAO;
    
    public PharmacienManagementServlet() {
        super();
        pharmacienDAO = new PharmacienDAO();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Verify if admin is logged in
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        
        if (admin == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        // Get pharmacists for the logged-in admin
        List<Pharmacien> pharmacienList = pharmacienDAO.getAllPharmaciensByAdmin(admin.getId_admin());
        request.setAttribute("pharmacienList", pharmacienList);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("pharmacien_management.jsp");
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
            // Add new pharmacist
            if ("add".equals(action)) {
                String nom = request.getParameter("nom");
                String prenom = request.getParameter("prenom");
                String email = request.getParameter("email");
                String motDePasse = request.getParameter("motDePasse");
                
                Pharmacien newPharmacien = new Pharmacien();
                newPharmacien.setNom(nom);
                newPharmacien.setPrenom(prenom);
                newPharmacien.setEmail(email);
                newPharmacien.setMotDePasse(motDePasse);
                newPharmacien.setId_admin(admin.getId_admin()); // Associate with current admin
                
                boolean success = pharmacienDAO.addPharmacien(newPharmacien);
                
                if (success) {
                    successMessage = "Le pharmacien a été ajouté avec succès!";
                } else {
                    errorMessage = "Erreur lors de l'ajout du pharmacien.";
                }
            }
            
            // Update pharmacist
            else if ("update".equals(action)) {
                int id_pharmacien = Integer.parseInt(request.getParameter("id_pharmacien"));
                String nom = request.getParameter("nom");
                String prenom = request.getParameter("prenom");
                String email = request.getParameter("email");
                String motDePasse = request.getParameter("motDePasse");
                
                Pharmacien existingPharmacien = pharmacienDAO.getPharmacienById(id_pharmacien);
                
                if (existingPharmacien != null) {
                    existingPharmacien.setNom(nom);
                    existingPharmacien.setPrenom(prenom);
                    existingPharmacien.setEmail(email);
                    
                    // Only update password if a new one is provided
                    if (motDePasse != null && !motDePasse.trim().isEmpty()) {
                        existingPharmacien.setMotDePasse(motDePasse);
                    }
                    
                    boolean success = pharmacienDAO.updatePharmacien(existingPharmacien);
                    
                    if (success) {
                        successMessage = "Le pharmacien a été mis à jour avec succès!";
                    } else {
                        errorMessage = "Erreur lors de la mise à jour du pharmacien.";
                    }
                } else {
                    errorMessage = "Pharmacien non trouvé.";
                }
            }
            
            // Delete pharmacist
            else if ("delete".equals(action)) {
                int id_pharmacien = Integer.parseInt(request.getParameter("id_pharmacien"));
                
                boolean success = pharmacienDAO.deletePharmacien(id_pharmacien);
                
                if (success) {
                    successMessage = "Le pharmacien a été supprimé avec succès!";
                } else {
                    errorMessage = "Erreur lors de la suppression du pharmacien.";
                }
            }
            
        } catch (Exception e) {
            errorMessage = "Une erreur s'est produite: " + e.getMessage();
            e.printStackTrace();
        }
        
        // Set attributes for the response
        request.setAttribute("successMessage", successMessage);
        request.setAttribute("errorMessage", errorMessage);
        
        // Reload the pharmacist list and forward to the JSP
        List<Pharmacien> pharmacienList = pharmacienDAO.getAllPharmaciensByAdmin(admin.getId_admin());
        request.setAttribute("pharmacienList", pharmacienList);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("pharmacien_management.jsp");
        dispatcher.forward(request, response);
    }
}