<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="modele.Admin" %>
<%
    // Vérifiez si l'admin est connecté
    Admin admin = (Admin) session.getAttribute("admin");
    if (admin == null) {
        response.sendRedirect("../login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard Admin - Gestion Pharmacie</title>
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
        
        .card-counter {
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            margin-bottom: 20px;
            padding: 20px 10px;
            background-color: #fff;
            height: 100px;
            border-radius: 5px;
            transition: .3s linear all;
        }
        
        .card-counter:hover {
            box-shadow: 0 8px 16px rgba(0,0,0,0.3);
            transition: .3s linear all;
        }
        
        .card-counter i {
            font-size: 5em;
            opacity: 0.5;
        }
        
        .card-counter .count-numbers {
            position: absolute;
            right: 35px;
            top: 20px;
            font-size: 32px;
            display: block;
        }
        
        .card-counter .count-name {
            position: absolute;
            right: 35px;
            top: 65px;
            opacity: 0.7;
            display: block;
            font-size: 18px;
        }
        
        .bg-primary {
            background-color: #007bff;
            color: #fff;
        }
        
        .bg-success {
            background-color: #28a745;
            color: #fff;
        }
        
        .bg-warning {
            background-color: #ffc107;
            color: #fff;
        }
        
        .bg-danger {
            background-color: #dc3545;
            color: #fff;
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
                    <a class="dropdown-item" href="../logout"><i class="fas fa-sign-out-alt"></i> Déconnexion</a>
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
                            <a class="nav-link active" href="#">
                                <i class="fas fa-tachometer-alt"></i> Dashboard
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">
                                <i class="fas fa-pills"></i> Médicaments
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">
                                <i class="fas fa-boxes"></i> Stock
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">
                                <i class="fas fa-user-md"></i> Pharmaciens
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">
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
                    <h1 class="h2">Dashboard</h1>
                </div>

                <div class="row">
                    <div class="col-md-3">
                        <div class="card-counter bg-primary">
                            <i class="fas fa-pills"></i>
                            <span class="count-numbers">24</span>
                            <span class="count-name">Médicaments</span>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="card-counter bg-success">
                            <i class="fas fa-user-md"></i>
                            <span class="count-numbers">5</span>
                            <span class="count-name">Pharmaciens</span>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="card-counter bg-warning">
                            <i class="fas fa-shopping-cart"></i>
                            <span class="count-numbers">12</span>
                            <span class="count-name">Ventes Aujourd'hui</span>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="card-counter bg-danger">
                            <i class="fas fa-exclamation-triangle"></i>
                            <span class="count-numbers">3</span>
                            <span class="count-name">Stock Faible</span>
                        </div>
                    </div>
                </div>

                <div class="row mt-4">
                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-header bg-success text-white">
                                <i class="fas fa-chart-line"></i> Ventes Récentes
                            </div>
                            <div class="card-body">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th>Date</th>
                                            <th>Pharmacien</th>
                                            <th>Montant</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>24/02/2025</td>
                                            <td>Ahmed Alaoui</td>
                                            <td>450.00 DH</td>
                                        </tr>
                                        <tr>
                                            <td>24/02/2025</td>
                                            <td>Sara Benmoussa</td>
                                            <td>325.50 DH</td>
                                        </tr>
                                        <tr>
                                            <td>23/02/2025</td>
                                            <td>Karim Idrissi</td>
                                            <td>780.75 DH</td>
                                        </tr>
                                        <tr>
                                            <td>23/02/2025</td>
                                            <td>Ahmed Alaoui</td>
                                            <td>560.00 DH</td>
                                        </tr>
                                        <tr>
                                            <td>22/02/2025</td>
                                            <td>Sara Benmoussa</td>
                                            <td>215.25 DH</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-header bg-warning text-white">
                                <i class="fas fa-exclamation-triangle"></i> Médicaments à Stock Faible
                            </div>
                            <div class="card-body">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th>Nom</th>
                                            <th>Quantité</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>Paracétamol 500mg</td>
                                            <td><span class="badge badge-danger">5</span></td>
                                            <td><button class="btn btn-sm btn-primary">Réapprovisionner</button></td>
                                        </tr>
                                        <tr>
                                            <td>Amoxicilline 1g</td>
                                            <td><span class="badge badge-danger">3</span></td>
                                            <td><button class="btn btn-sm btn-primary">Réapprovisionner</button></td>
                                        </tr>
                                        <tr>
                                            <td>Ibuprofène 400mg</td>
                                            <td><span class="badge badge-warning">8</span></td>
                                            <td><button class="btn btn-sm btn-primary">Réapprovisionner</button></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>