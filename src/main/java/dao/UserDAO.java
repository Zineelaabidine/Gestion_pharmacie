package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modele.Admin;
import modele.Pharmacien;
import util.DBConnection;

public class UserDAO {
    
    // Méthode pour vérifier l'authentification d'un admin
    public Admin authenticateAdmin(String email, String motDePasse) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Admin admin = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM Admin WHERE email = ? AND mot_de_passe = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, motDePasse);  
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                admin = new Admin();
                admin.setId_admin(rs.getInt("id_admin"));
                admin.setNom(rs.getString("nom"));
                admin.setPrenom(rs.getString("prénom"));
                admin.setEmail(rs.getString("email"));
                admin.setMotDePasse(rs.getString("mot_de_passe"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return admin;
    }
    
    // Méthode pour vérifier l'authentification d'un pharmacien
    public Pharmacien authenticatePharmacien(String email, String motDePasse) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Pharmacien pharmacien = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM Pharmacien WHERE email = ? AND mot_de_passe = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, motDePasse); // Idéalement, vous devriez vérifier un mot de passe haché
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                pharmacien = new Pharmacien();
                pharmacien.setId_pharmacien(rs.getInt("id_pharmacien"));
                pharmacien.setNom(rs.getString("nom"));
                pharmacien.setPrenom(rs.getString("prénom"));
                pharmacien.setEmail(rs.getString("email"));
                pharmacien.setMotDePasse(rs.getString("mot_de_passe"));
                pharmacien.setId_admin(rs.getInt("id_admin"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return pharmacien;
    }
    
    // Méthode pour créer un nouvel admin
    public boolean registerAdmin(Admin admin) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO Admin (nom, prénom, email, mot_de_passe) VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, admin.getNom());
            ps.setString(2, admin.getPrenom());
            ps.setString(3, admin.getEmail());
            ps.setString(4, admin.getMotDePasse());  
            
            int rowsAffected = ps.executeUpdate();
            success = (rowsAffected > 0);
            
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return success;
    }
}