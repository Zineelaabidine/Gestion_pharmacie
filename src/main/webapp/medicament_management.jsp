<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, modele.Medicament, modele.Vente, modele.Pharmacien" %>
<%@ page import="modele.Admin" %>
<%
    // Vérifiez si l'admin est connecté
    Admin admin = (Admin) session.getAttribute("admin");
    if (admin == null) {
        response.sendRedirect("../login.jsp");
        return;
    }

    // Récupérer la liste des pharmaciens
    List<Pharmacien> pharmaciens = (List<Pharmacien>) request.getAttribute("pharmaciens");
    String pharmacienId = request.getParameter("pharmacienId");

    // Filtrer les ventes par pharmacien si un pharmacien est sélectionné
    List<Vente> ventes = (List<Vente>) request.getAttribute("ventes");
    if (pharmacienId != null && !pharmacienId.isEmpty()) {
        ventes = filterVentesByPharmacien(ventes, pharmacienId);
    }
    
    // Fonction pour filtrer les ventes en fonction du pharmacien sélectionné
    private List<Vente> filterVentesByPharmacien(List<Vente> ventes, String pharmacienId) {
        List<Vente> filteredVentes = new ArrayList<>();
        for (Vente vente : ventes) {
            if (vente.getPharmacien().getId().equals(pharmacienId)) {
                filteredVentes.add(vente);
            }
        }
        return filteredVentes;
    }
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gestion des Ventes</title>
    
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
    <style>
        /* Styles précédemment définis */
        /* ... (vos autres styles CSS ici) ... */
    </style>
</head>
<body>
    <nav class="navbar navbar-dark bg-success fixed-top">
        <a class="navbar-brand" href="#">Gestion Pharmacie</a>
        <ul class="navbar-nav ml-auto">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <i class="fas fa-user-circle"></i> <%= admin.getPrenom() %> <%= admin.getNom() %>
                </a>
                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="#"><i class="fas fa-user"></i> Profil</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="logout"><i class="fas fa-sign-out-alt"></i> Déconnexion</a>
                </div>
            </li>
        </ul>
    </nav>

    <div class="container-fluid">
        <div class="row">
            <nav class="col-md-2 d-none d-md-block sidebar">
                <div class="sidebar-sticky">
                    <ul class="nav flex-column">
                        <li class="nav-item">
                            <a class="nav-link" href="admin_home.jsp">
                                <i class="fas fa-tachometer-alt"></i> Dashboard
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">
                                <i class="fas fa-pills"></i> Médicaments
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" href="stock_management">
                                <i class="fas fa-boxes"></i> Stock
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="pharmacien_management">
                                <i class="fas fa-user-md"></i> Pharmaciens
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="vente">
                                <i class="fas fa-chart-line"></i> Ventes
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">
                                <i class="fas fa-cog"></i> Paramètres
                            </a>
                        </li>
                    </ul>
                </div>
            </nav>

            <main class="col-md-9 ml-sm-auto col-lg-10 px-4">
                <h2>Gestion des Ventes</h2>

                <!-- Affichage des messages d'erreur et de succès -->
                <% 
                    String error = request.getParameter("error");
                    String success = request.getParameter("success");
                    if ("1".equals(error)) { 
                %>
                    <p class="error">Une erreur s'est produite lors de l'enregistrement de la vente. Veuillez réessayer.</p>
                <% 
                    } else if ("1".equals(success)) { 
                %>
                    <p class="success">Vente enregistrée avec succès !</p>
                <% } %>

                <!-- Formulaire d'enregistrement d'une vente -->
                <h3>Enregistrer une vente</h3>
                <form action="controller/EnregistrerVente" method="POST">
                    <label for="medicament">Médicament :</label>
                    <select name="medicament" id="medicament" required>
                        <%
                            List<Medicament> medicaments = (List<Medicament>) request.getAttribute("medicaments");
                            if (medicaments != null && !medicaments.isEmpty()) {
                                for (Medicament medicament : medicaments) {
                        %>
                            <option value="<%= medicament.getId_medicament() %>"><%= medicament.getNom() %></option>
                        <%
                                }
                            } else {
                        %>
                            <option value="" disabled>Aucun médicament disponible</option>
                        <% } %>
                    </select>

                    <label for="quantite">Quantité :</label>
                    <input type="number" name="quantite" id="quantite" min="1" required>

                    <!-- Liste déroulante des pharmaciens -->
                    <label for="pharmacienId">Pharmacien :</label>
                    <select name="pharmacienId" id="pharmacienId" required>
                        <option value="">Sélectionner un pharmacien</option>
                        <%
                            if (pharmaciens != null && !pharmaciens.isEmpty()) {
                                for (Pharmacien pharmacien : pharmaciens) {
                        %>
                            <option value="<%= pharmacien.getId() %>" <%= pharmacien.getId().equals(pharmacienId) ? "selected" : "" %>><%= pharmacien.getNom() %></option>
                        <%
                                }
                            } else {
                        %>
                            <option value="" disabled>Aucun pharmacien disponible</option>
                        <% } %>
                    </select>

                    <button type="submit">Enregistrer la vente</button>
                </form>

                <!-- Affichage des ventes réalisées -->
                <h3>Ventes Réalisées</h3>
                <table>
                    <thead>
                        <tr>
                            <th>Médicament</th>
                            <th>Quantité</th>
                            <th>Pharmacien</th>
                            <th>Date</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% 
                            if (ventes != null && !ventes.isEmpty()) {
                                for (Vente vente : ventes) {
                        %>
                            <tr>
                                <td><%= vente.getMedicament() %></td>
                                <td><%= vente.getQuantite() %></td>
                                <td><%= vente.getPharmacien().getNom() %></td>
                                <td><%= vente.getDate() %></td>
                            </tr>
                        <%
                                }
                            } else {
                        %>
                            <tr>
                                <td colspan="4">Aucune vente enregistrée.</td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>

                <!-- Pagination -->
                <div class="pagination">
                    <% 
                        Integer currentPage = (Integer) request.getAttribute("currentPage");
                        Integer totalPages = (Integer) request.getAttribute("totalPages");

                        if (currentPage != null && totalPages != null && totalPages > 1) {
                            if (currentPage > 1) { 
                    %>
                        <a href="vente_management.jsp?page=<%= currentPage - 1 %>&pharmacienId=<%= pharmacienId %>">&laquo; Précédent</a>
                    <% 
                            }
                            for (int i = 1; i <= totalPages; i++) { 
                    %>
                        <a href="vente_management.jsp?page=<%= i %>&pharmacienId=<%= pharmacienId %>" class="<%= (i == currentPage) ? "active" : "" %>"><%= i %></a>
                    <% 
                            }
                            if (currentPage < totalPages) { 
                    %>
                        <a href="vente_management.jsp?page=<%= currentPage + 1 %>&pharmacienId=<%= pharmacienId %>">Suivant &raquo;</a>
                    <% 
                            }
                        }
                    %>
                </div>
            </main>
        </div>
    </div>
</body>
</html>
