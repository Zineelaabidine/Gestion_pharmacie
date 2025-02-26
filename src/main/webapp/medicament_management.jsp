<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="modele.*" %>
<%@ page import="java.util.List" %>
<%
    // Récupérer la liste des médicaments
    List<Medicament> medicamentList = (List<Medicament>) request.getAttribute("medicamentList");
    if (medicamentList == null) {
        // Rediriger vers un servlet pour charger la liste si elle n'est pas présente
        response.sendRedirect("medicament_management");
        return;
    }
    
    // Vérifier si l'administrateur est connecté
    Admin admin = (Admin) session.getAttribute("admin");
    if (admin == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String successMessage = (String) request.getAttribute("successMessage");
    String errorMessage = (String) request.getAttribute("errorMessage");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des Médicaments - Gestion Pharmacie</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
    <style>
        .sidebar {
            position: fixed;
            top: 0;
            bottom: 0;
            left: 0;
            z-index: 100;
            padding: 90px 0 0;
            box-shadow: inset -1px 0 0 rgba(0, 0, 0, .1);
            background-color: #343a40;
        }

        .navbar {
            box-shadow: 0 2px 5px 0 rgba(0,0,0,0.16);
        }

        .sidebar-sticky {
            position: relative;
            top: 0;
            height: calc(100vh - 90px);
            padding-top: 0.5rem;
            overflow-x: hidden;
            overflow-y: auto;
        }
        .sidebar .nav-link.active {
            color: #fff;
            background-color: #28a745;
        }
        .sidebar .nav-link {
            font-weight: 500;
            color: #f8f9fa;
            padding: 10px 20px;
        }

        .sidebar .nav-link:hover {
            color: #fff;
            background-color: #4caf50;
        }

        .sidebar .nav-link.active {
            color: #fff;
            background-color: #28a745;
        }

        .sidebar .nav-link i {
            margin-right: 10px;
        }

        main {
            padding-top: 90px;
        }
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
                            <a class="nav-link active" href="medicament_management.jsp">
                                <i class="fas fa-pills"></i> Médicaments
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">
                                <i class="fas fa-boxes"></i> Stock
                            </a>
                        </li>
                    </ul>
                </div>
            </nav>

            <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                    <h1 class="h2">Gestion des Médicaments</h1>
                </div>

                <% if (successMessage != null && !successMessage.isEmpty()) { %>
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <%= successMessage %>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <% } %>

                <% if (errorMessage != null && !errorMessage.isEmpty()) { %>
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <%= errorMessage %>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <% } %>

                <div class="table-responsive">
                    <table class="table table-bordered table-striped">
                        <thead class="thead-dark">
                            <tr>
                                <th>ID</th>
                                <th>Nom</th>
                                <th>Description</th>
                                <th>Prix</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if (medicamentList != null && !medicamentList.isEmpty()) { %>
                                <% for (Medicament medicament : medicamentList) { %>
                                    <tr>
                                        <td><%= medicament.getId_medicament() %></td>
                                        <td><%= medicament.getNom() %></td>
                                        <td><%= medicament.getDescription() %></td>
                                        <td><%= medicament.getPrix() %></td>
                                    </tr>
                                <% } %>
                            <% } else { %>
                                <tr>
                                    <td colspan="4" class="text-center">Aucun médicament trouvé</td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </main>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
