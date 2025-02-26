package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modele.Medicament;
import util.DBConnection;

public class MedicamentDAO {
    
    // Méthode pour obtenir tous les médicaments
    public static List<Medicament> getAllMedicaments() {
        List<Medicament> medicaments = new ArrayList<>();
        
        // Connexion à la base de données via la classe DBConnection
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT id_médicament, nom, description, prix, id_admin FROM Médicament");
             ResultSet resultSet = statement.executeQuery()) {
             
            // Parcourir les résultats et créer des objets Medicament
            while (resultSet.next()) {
                Medicament medicament = new Medicament();
                medicament.setId_medicament(resultSet.getInt("id_médicament"));  // Modification ici
                medicament.setNom(resultSet.getString("nom"));
                medicament.setDescription(resultSet.getString("description"));
                medicament.setPrix(resultSet.getDouble("prix"));
                medicament.setId_admin(resultSet.getInt("id_admin"));
                medicaments.add(medicament);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Erreur lors de l'exécution de la requête : " + e.getMessage());
            e.printStackTrace();
        }
        return medicaments;
    }

    // Méthode pour obtenir la liste des médicaments en fonction d'une requête de recherche
    public static List<Medicament> getMedicaments(String searchQuery) {
        List<Medicament> medicaments = new ArrayList<>();
        
        String sql = "SELECT id_médicament, nom, description, prix, id_admin FROM Médicament WHERE nom LIKE ?";
        
        // Connexion à la base de données via la classe DBConnection
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
             
            statement.setString(1, "%" + searchQuery + "%");
            
            // Exécuter la requête et récupérer les résultats
            try (ResultSet resultSet = statement.executeQuery()) {
                // Parcourir les résultats et créer des objets Medicament
                while (resultSet.next()) {
                    Medicament medicament = new Medicament();
                    medicament.setId_medicament(resultSet.getInt("id_médicament"));  // Modification ici
                    medicament.setNom(resultSet.getString("nom"));
                    medicament.setDescription(resultSet.getString("description"));
                    medicament.setPrix(resultSet.getDouble("prix"));
                    medicament.setId_admin(resultSet.getInt("id_admin"));
                    medicaments.add(medicament);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Erreur lors de l'exécution de la requête : " + e.getMessage());
            e.printStackTrace();
        }
        
        return medicaments;
    }

    // Méthode pour ajouter un médicament
    public static boolean addMedicament(Medicament medicament) {
        String sql = "INSERT INTO Médicament (nom, description, prix, id_admin) VALUES (?, ?, ?, ?)";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
             
            statement.setString(1, medicament.getNom());
            statement.setString(2, medicament.getDescription());
            statement.setDouble(3, medicament.getPrix());
            statement.setInt(4, medicament.getId_admin());
            
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Erreur lors de l'ajout du médicament : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour mettre à jour un médicament
    public static boolean updateMedicament(Medicament medicament) {
        String sql = "UPDATE Médicament SET nom = ?, description = ?, prix = ?, id_admin = ? WHERE id_médicament = ?";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
             
            statement.setString(1, medicament.getNom());
            statement.setString(2, medicament.getDescription());
            statement.setDouble(3, medicament.getPrix());
            statement.setInt(4, medicament.getId_admin());
            statement.setInt(5, medicament.getId_medicament());  // Modification ici
            
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Erreur lors de la mise à jour du médicament : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour supprimer un médicament
    public static boolean deleteMedicament(int id_medicament) {
        String sql = "DELETE FROM Médicament WHERE id_médicament = ?";  // Modification ici
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
             
            statement.setInt(1, id_medicament);
            
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Erreur lors de la suppression du médicament : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
