<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="modele.Admin" %>
<%@ page import="modele.Stock" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    // Vérifiez si la liste des stocks a été chargée
    List<Stock> stockList = (List<Stock>) request.getAttribute("stockList");
    if (stockList == null) {
        // Si la liste n'est pas chargée, redirigez vers le servlet pour la charger
        response.sendRedirect("stock_management");
        return;
    }
    
    // Vérifiez si l'admin est connecté
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
    <title>Gestion des Stocks - Gestion Pharmacie</title>
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

            <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                    <h1 class="h2">Gestion des Stocks</h1>
                    <button type="button" class="btn btn-success" data-toggle="modal" data-target="#addStockModal">
                        <i class="fas fa-plus"></i> Ajouter un Stock
                    </button>
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
                                <th>ID Stock</th>
                                <th>ID Médicament</th>
                                <th>Quantité</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if (stockList != null && !stockList.isEmpty()) { %>
                                <% for (Stock stock : stockList) { %>
                                    <tr>
                                        <td><%= stock.getId() %></td>
                                        <td><%= stock.getId_médicament() %></td>
                                        <td><%= stock.getQuantité() %></td>
                                        <td>
                                            <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#editStockModal<%= stock.getId() %>">
                                                <i class="fas fa-edit"></i>
                                            </button>
                                            <button class="btn btn-danger btn-sm" data-toggle="modal" data-target="#deleteStockModal<%= stock.getId() %>">
                                                <i class="fas fa-trash"></i>
                                            </button>
                                        </td>
                                    </tr>

                                    <!-- Modal pour modifier le stock -->
                                    <div class="modal fade" id="editStockModal<%= stock.getId() %>" tabindex="-1" role="dialog" aria-labelledby="editStockModalLabel" aria-hidden="true">
                                        <div class="modal-dialog" role="document">
                                            <div class="modal-content">
                                                <div class="modal-header bg-primary text-white">
                                                    <h5 class="modal-title" id="editStockModalLabel">Modifier Stock</h5>
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                                <form action="stock_management" method="post">
                                                    <input type="hidden" name="action" value="update">
                                                    <input type="hidden" name="id_stock" value="<%= stock.getId() %>">
                                                    <div class="modal-body">
                                                        <div class="form-group">
                                                            <label for="id_médicament">ID Médicament</label>
                                                            <input type="number" class="form-control" id="id_médicament" name="id_médicament" value="<%= stock.getId_médicament() %>" required>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="quantité">Quantité</label>
                                                            <input type="number" class="form-control" id="quantité" name="quantité" value="<%= stock.getQuantité() %>" required>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annuler</button>
                                                        <button type="submit" class="btn btn-primary">Enregistrer</button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Modal pour supprimer le stock -->
                                    <div class="modal fade" id="deleteStockModal<%= stock.getId() %>" tabindex="-1" role="dialog" aria-labelledby="deleteStockModalLabel" aria-hidden="true">
                                        <div class="modal-dialog" role="document">
                                            <div class="modal-content">
                                                <div class="modal-header bg-danger text-white">
                                                    <h5 class="modal-title" id="deleteStockModalLabel">Confirmer la suppression</h5>
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                                <div class="modal-body">
                                                    <p>Êtes-vous sûr de vouloir supprimer ce stock (ID: <%= stock.getId() %>) ?</p>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Annuler</button>
                                                    <form action="stock_management" method="post">
                                                        <input type="hidden" name="action" value="delete">
                                                        <input type="hidden" name="id_stock" value="<%= stock.getId() %>">
                                                        <button type="submit" class="btn btn-danger">Supprimer</button>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                <% } %>
                            <% } else { %>
                                <tr>
                                    <td colspan="4" class="text-center">Aucun stock trouvé</td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </main>
        </div>
    </div>

    <!-- Modal pour ajouter un nouveau stock -->
    <div class="modal fade" id="addStockModal" tabindex="-1" role="dialog" aria-labelledby="addStockModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header bg-success text-white">
                    <h5 class="modal-title" id="addStockModalLabel">Ajouter un Stock</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <form action="stock_management" method="post">
                    <input type="hidden" name="action" value="add">
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="id_médicament">ID Médicament</label>
                            <input type="number" class="form-control" id="id_médicament" name="id_médicament" required>
                        </div>
                        <div class="form-group">
                            <label for="quantité">Quantité</label>
                            <input type="number" class="form-control" id="quantité" name="quantité" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annuler</button>
                        <button type="submit" class="btn btn-success">Ajouter</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>