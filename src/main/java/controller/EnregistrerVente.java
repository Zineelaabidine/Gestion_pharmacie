package controller;

import dao.VenteDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import modele.Admin;

@WebServlet("/controller/EnregistrerVente")
public class EnregistrerVente extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Récupérer l'ID du pharmacien connecté depuis la session
            HttpSession session = request.getSession();
            Admin admin = (Admin) session.getAttribute("admin");

            // Si l'utilisateur n'est pas connecté, rediriger vers la connexion
            if (admin == null) {
                response.sendRedirect("http://localhost:8080/GestionPharmacie/login.jsp?error=notLoggedIn");
                return;
            }

            // Vérifier que les paramètres ne sont pas null
            String medicamentParam = request.getParameter("medicament");
            String quantiteParam = request.getParameter("quantite");

            if (medicamentParam == null || quantiteParam == null) {
                response.sendRedirect("http://localhost:8080/GestionPharmacie/vente_management.jsp?error=invalidInput");
                return;
            }

            // Convertir les paramètres en entiers
            int idMedicament = Integer.parseInt(medicamentParam);
            int quantite = Integer.parseInt(quantiteParam);

            // Vérifier que la quantité est valide
            if (quantite <= 0) {
                response.sendRedirect("http://localhost:8080/GestionPharmacie/vente_management.jsp?error=invalidQuantity");
                return;
            }

            // Ajouter la vente
            boolean success = VenteDAO.ajouterVente(idMedicament, quantite, admin.getId_admin());

            // Redirection en fonction du résultat
            if (success) {
                response.sendRedirect("http://localhost:8080/GestionPharmacie/vente_management.jsp?success=1");
            } else {
                response.sendRedirect("http://localhost:8080/GestionPharmacie/vente_management.jsp?error=1");
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("http://localhost:8080/GestionPharmacie/vente_management.jsp?error=invalidNumber");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("http://localhost:8080/GestionPharmacie/vente_management.jsp?error=serverError");
        }
    }
}
