package controller;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modele.Medicament;
import modele.Vente;
import dao.MedicamentDAO;
import dao.VenteDAO;

@WebServlet("/vente")  
public class VenteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Récupérer la page actuelle depuis la requête (par défaut, page 1)
        int currentPage = 1;
        if (request.getParameter("page") != null) {
            try {
                currentPage = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        // Nombre d'éléments par page
        int itemsPerPage = 10;
        
     // Récupérer la liste des médicaments depuis la base
        List<Medicament> medicaments = MedicamentDAO.getAllMedicaments();
        request.setAttribute("medicaments", medicaments);
        
        // Récupérer les ventes et calculer le nombre total de pages
        List<Vente> allVentes = VenteDAO.getAllVentes();
        int totalItems = allVentes.size();
        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

        // Limiter la liste des ventes à la page actuelle
        int startIndex = (currentPage - 1) * itemsPerPage;
        List<Vente> ventes = allVentes.subList(startIndex, Math.min(startIndex + itemsPerPage, totalItems));

        // Passer les données à la JSP
        request.setAttribute("ventes", ventes);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);

        // Rediriger vers la JSP
        request.getRequestDispatcher("vente_management.jsp").forward(request, response);
    }
}

