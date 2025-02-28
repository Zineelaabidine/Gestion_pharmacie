package dao;

import modele.Stock;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockDAO {
    
    // Get all stock items
    public List<Stock> getAllStockItems() {
        List<Stock> stockItems = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM Stock";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Stock stockItem = new Stock();
                stockItem.setId(rs.getInt("id_stock"));
                stockItem.setId_médicament(rs.getInt("id_médicament"));
                stockItem.setQuantité(rs.getInt("quantité"));
                stockItem.setId_admin(rs.getInt("id_admin"));
                
                stockItems.add(stockItem);
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
        
        return stockItems;
    }

    // Get stock item by ID
    public Stock getStockById(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Stock stockItem = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM Stock WHERE id_stock = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                stockItem = new Stock();
                stockItem.setId(rs.getInt("id_stock"));
                stockItem.setId_médicament(rs.getInt("id_médicament"));
                stockItem.setQuantité(rs.getInt("quantité"));
                stockItem.setId_admin(rs.getInt("id_admin"));
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
        
        return stockItem;
    }

    // Add a new stock item
    public boolean addStockItem(Stock stockItem) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO Stock (id_médicament, quantité, id_admin) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, stockItem.getId_médicament());
            ps.setInt(2, stockItem.getQuantité());
            ps.setInt(3, stockItem.getId_admin());
            
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

    // Update an existing stock item
    public boolean updateStockItem(Stock stockItem) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE Stock SET id_médicament = ?, quantité = ?, id_admin = ? WHERE id_stock = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, stockItem.getId_médicament());
            ps.setInt(2, stockItem.getQuantité());
            ps.setInt(3, stockItem.getId_admin());
            ps.setInt(4, stockItem.getId());
            
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

    // Delete a stock item
    public boolean deleteStockItem(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "DELETE FROM Stock WHERE id_stock = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
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
    public List<Stock> getAllStockByAdmin(int adminId) {
        List<Stock> stockItems = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM Stock WHERE id_admin = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, adminId);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Stock stockItem = new Stock();
                stockItem.setId(rs.getInt("id_stock"));
                stockItem.setId_médicament(rs.getInt("id_médicament"));
                stockItem.setQuantité(rs.getInt("quantité"));
                stockItem.setId_admin(rs.getInt("id_admin"));
                
                stockItems.add(stockItem);
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
        
        return stockItems;
    }
}

