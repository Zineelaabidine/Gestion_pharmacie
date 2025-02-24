package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import modele.Admin;
import modele.Pharmacien;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private UserDAO userDAO;
    
    public LoginServlet() {
        userDAO = new UserDAO();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");
        String userType = request.getParameter("userType");
        
        HttpSession session = request.getSession();
        
        if ("admin".equals(userType)) {
            Admin admin = userDAO.authenticateAdmin(email, motDePasse);
            
            if (admin != null) {
                session.setAttribute("admin", admin);
                response.sendRedirect("admin_home.jsp");
            } else {
                request.setAttribute("errorMessage", "Email ou mot de passe incorrect");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } else if ("pharmacien".equals(userType)) {
            Pharmacien pharmacien = userDAO.authenticatePharmacien(email, motDePasse);
            
            if (pharmacien != null) {
                session.setAttribute("pharmacien", pharmacien);
                response.sendRedirect("pharmacien/dashboard.jsp");
            } else {
                request.setAttribute("errorMessage", "Email ou mot de passe incorrect");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", "Type d'utilisateur non valide");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}