package dao;
import util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
public class DashboardDAO {
    
    // Get count of all medications
    public int countMedicaments() {
        int count = 0;
        String query = "SELECT COUNT(*) AS total FROM Médicament";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        return count;
    }
    
    // Get count of all pharmacists
    public int countPharmaciens() throws ClassNotFoundException {
        int count = 0;
        String query = "SELECT COUNT(*) AS total FROM Pharmacien";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return count;
    }
    
    // Get count of today's sales
    public int countVentesToday() {
        int count = 0;
        String query = "SELECT COUNT(*) AS total FROM Vente WHERE DATE(date) = CURDATE()";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        return count;
    }
    
    // Get count of low stock medications (quantity < 10)
    public int countLowStock() {
        int count = 0;
        String query = "SELECT COUNT(*) AS total FROM Stock WHERE quantité < 10";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        return count;
    }
    
    // Get recent sales with pharmacist info
    public List<Map<String, Object>> getRecentSales(int limit) {
        List<Map<String, Object>> sales = new ArrayList<>();
        String query = "SELECT v.date, CONCAT(p.prénom, ' ', p.nom) AS pharmacien, v.total_vente " +
                       "FROM Vente v " +
                       "JOIN Pharmacien p ON v.id_pharmacien = p.id_pharmacien " +
                       "ORDER BY v.date DESC LIMIT ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, limit);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> sale = new HashMap<>();
                sale.put("date", rs.getTimestamp("date"));
                sale.put("pharmacien", rs.getString("pharmacien"));
                sale.put("montant", rs.getDouble("total_vente"));
                sales.add(sale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        return sales;
    }
    
    // Get medications with low stock
    public List<Map<String, Object>> getLowStockMedicaments() {
        List<Map<String, Object>> medicaments = new ArrayList<>();
        String query = "SELECT m.nom, s.quantité, m.id_médicament " +
                       "FROM Stock s " +
                       "JOIN Médicament m ON s.id_médicament = m.id_médicament " +
                       "WHERE s.quantité < 10 " +
                       "ORDER BY s.quantité ASC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Map<String, Object> medicament = new HashMap<>();
                medicament.put("id", rs.getInt("id_médicament"));
                medicament.put("nom", rs.getString("nom"));
                medicament.put("quantite", rs.getInt("quantité"));
                medicaments.add(medicament);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        return medicaments;
    }
}