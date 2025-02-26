package dao;

import modele.Pharmacien;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PharmacienDAO {
    
    // Get all pharmacists
    public List<Pharmacien> getAllPharmaciens() {
        List<Pharmacien> pharmaciens = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM Pharmacien";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Pharmacien pharmacien = new Pharmacien();
                pharmacien.setId_pharmacien(rs.getInt("id_pharmacien"));
                pharmacien.setNom(rs.getString("nom"));
                pharmacien.setPrenom(rs.getString("prénom"));
                pharmacien.setEmail(rs.getString("email"));
                pharmacien.setMotDePasse(rs.getString("mot_de_passe"));
                pharmacien.setId_admin(rs.getInt("id_admin"));
                
                pharmaciens.add(pharmacien);
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
        
        return pharmaciens;
    }
    
    // Get all pharmacists for a specific admin
    public List<Pharmacien> getAllPharmaciensByAdmin(int adminId) {
        List<Pharmacien> pharmaciens = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM Pharmacien WHERE id_admin = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, adminId);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Pharmacien pharmacien = new Pharmacien();
                pharmacien.setId_pharmacien(rs.getInt("id_pharmacien"));
                pharmacien.setNom(rs.getString("nom"));
                pharmacien.setPrenom(rs.getString("prénom"));
                pharmacien.setEmail(rs.getString("email"));
                pharmacien.setMotDePasse(rs.getString("mot_de_passe"));
                pharmacien.setId_admin(rs.getInt("id_admin"));
                
                pharmaciens.add(pharmacien);
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
        
        return pharmaciens;
    }
    
    // Add a new pharmacist
    public boolean addPharmacien(Pharmacien pharmacien) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO Pharmacien (nom, prénom, email, mot_de_passe, id_admin) VALUES (?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, pharmacien.getNom());
            ps.setString(2, pharmacien.getPrenom());
            ps.setString(3, pharmacien.getEmail());
            ps.setString(4, pharmacien.getMotDePasse());
            ps.setInt(5, pharmacien.getId_admin());
            
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
    
    // Update an existing pharmacist
    public boolean updatePharmacien(Pharmacien pharmacien) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;
        
        try {
            conn = DBConnection.getConnection();
            
            // Update with or without password change
            String sql;
            if (pharmacien.getMotDePasse() != null && !pharmacien.getMotDePasse().isEmpty()) {
                sql = "UPDATE Pharmacien SET nom = ?, prénom = ?, email = ?, mot_de_passe = ? WHERE id_pharmacien = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, pharmacien.getNom());
                ps.setString(2, pharmacien.getPrenom());
                ps.setString(3, pharmacien.getEmail());
                ps.setString(4, pharmacien.getMotDePasse());
                ps.setInt(5, pharmacien.getId_pharmacien());
            } else {
                sql = "UPDATE Pharmacien SET nom = ?, prénom = ?, email = ? WHERE id_pharmacien = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, pharmacien.getNom());
                ps.setString(2, pharmacien.getPrenom());
                ps.setString(3, pharmacien.getEmail());
                ps.setInt(4, pharmacien.getId_pharmacien());
            }
            
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
    
    // Delete a pharmacist
    public boolean deletePharmacien(int id_pharmacien) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "DELETE FROM Pharmacien WHERE id_pharmacien = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id_pharmacien);
            
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
    
    // Get pharmacist by ID
    public Pharmacien getPharmacienById(int id_pharmacien) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Pharmacien pharmacien = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM Pharmacien WHERE id_pharmacien = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id_pharmacien);
            
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
}