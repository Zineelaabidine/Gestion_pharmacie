package controller;

import java.io.IOException;
import java.util.List;

import dao.MedicamentDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modele.*;
import dao.*;


@WebServlet("/medicament_management")
public class MedicamentManagementServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private MedicamentDAO medicamentDAO;

    @Override
    public void init() throws ServletException {
        // Initialisation du DAO pour l'accès aux données
        medicamentDAO = new MedicamentDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Vérification de l'authentification
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        if (admin == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Récupérer la liste des médicaments à afficher
        List<Medicament> medicamentList = medicamentDAO.getAllMedicaments();
        request.setAttribute("medicamentList", medicamentList);
        
        // Afficher la page de gestion des médicaments
        RequestDispatcher dispatcher = request.getRequestDispatcher("medicament_management.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Vérification de l'authentification
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        if (admin == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Obtenir l'action depuis le formulaire
        String action = request.getParameter("action");

        switch (action) {
            case "add":
                addMedicament(request, response);
                break;
            case "update":
                updateMedicament(request, response);
                break;
            case "delete":
                deleteMedicament(request, response);
                break;
            default:
                response.sendRedirect("medicament_management");
                break;
        }
    }

    private void addMedicament(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer les informations du médicament à ajouter
        String nom = request.getParameter("nom");
        int quantite = Integer.parseInt(request.getParameter("quantite"));
        double prix = Double.parseDouble(request.getParameter("prix"));

        // Créer un objet Medicament
        Medicament medicament = new Medicament(nom, quantite, prix);

        // Ajouter le médicament via le DAO
        boolean success = medicamentDAO.addMedicament(medicament);

        // Gérer la redirection ou l'affichage d'un message
        if (success) {
            request.setAttribute("successMessage", "Médicament ajouté avec succès.");
        } else {
            request.setAttribute("errorMessage", "Échec de l'ajout du médicament.");
        }

        // Rediriger vers la page de gestion
        doGet(request, response);
    }

    private void updateMedicament(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer les informations du médicament à modifier
        int idMedicament = Integer.parseInt(request.getParameter("id_medicament"));
        String nom = request.getParameter("nom");
        int quantite = Integer.parseInt(request.getParameter("quantite"));
        double prix = Double.parseDouble(request.getParameter("prix"));

        // Créer un objet Medicament pour la mise à jour
        Medicament medicament = new Medicament(idMedicament, nom, quantite, prix);

        // Mettre à jour le médicament via le DAO
        boolean success = medicamentDAO.updateMedicament(medicament);

        // Gérer la redirection ou l'affichage d'un message
        if (success) {
            request.setAttribute("successMessage", "Médicament mis à jour avec succès.");
        } else {
            request.setAttribute("errorMessage", "Échec de la mise à jour du médicament.");
        }

        // Rediriger vers la page de gestion
        doGet(request, response);
    }

    private void deleteMedicament(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer l'ID du médicament à supprimer
        int idMedicament = Integer.parseInt(request.getParameter("id_medicament"));

        // Supprimer le médicament via le DAO
        boolean success = medicamentDAO.deleteMedicament(idMedicament);

        // Gérer la redirection ou l'affichage d'un message
        if (success) {
            request.setAttribute("successMessage", "Médicament supprimé avec succès.");
        } else {
            request.setAttribute("errorMessage", "Échec de la suppression du médicament.");
        }

        // Rediriger vers la page de gestion
        doGet(request, response);
    }
}
