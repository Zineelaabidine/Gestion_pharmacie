package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modele.Vente;
import util.DBConnection;

public class VenteDAO {

    // Méthode pour ajouter une nouvelle vente
    public static boolean ajouterVente(int idMedicament, int quantite, int idPharmacien) {
        Connection connection = null;
        PreparedStatement venteStmt = null;
        PreparedStatement venteMedicamentStmt = null;
        ResultSet generatedKeys = null;

        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(false); // Démarrer une transaction

            // Insérer une nouvelle vente
            String venteSQL = "INSERT INTO Vente (date, total_vente, id_pharmacien) VALUES (NOW(), 0, ?)";
            venteStmt = connection.prepareStatement(venteSQL, Statement.RETURN_GENERATED_KEYS);
            venteStmt.setInt(1, idPharmacien);
            int rowsInserted = venteStmt.executeUpdate();

            if (rowsInserted == 0) {
                throw new SQLException("Échec de l'insertion de la vente.");
            }

            // Récupérer l'ID de la vente insérée
            generatedKeys = venteStmt.getGeneratedKeys();
            int idVente;
            if (generatedKeys.next()) {
                idVente = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Échec de la récupération de l'ID de la vente.");
            }

            // Insérer les détails de la vente (liaison médicament-vente)
            String venteMedicamentSQL = "INSERT INTO Vente_Médicament (id_vente, id_médicament, quantité, prix_unitaire) " +
                                        "SELECT ?, id_médicament, ?, prix FROM Médicament WHERE id_médicament = ?";
            venteMedicamentStmt = connection.prepareStatement(venteMedicamentSQL);
            venteMedicamentStmt.setInt(1, idVente);
            venteMedicamentStmt.setInt(2, quantite);
            venteMedicamentStmt.setInt(3, idMedicament);
            venteMedicamentStmt.executeUpdate();

            connection.commit(); // Valider la transaction
            return true;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback(); // Annuler la transaction en cas d'erreur
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
        } finally {
            closeResources(generatedKeys, connection, venteStmt, venteMedicamentStmt);
        }
        return false;
    }

    // Méthode pour récupérer la liste des ventes paginées
    public static List<Vente> getVentes(int startIndex, int limit) {
        List<Vente> ventes = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT v.id_vente, v.date, v.total_vente, vm.id_médicament, m.nom, vm.quantité " +
                         "FROM Vente v " +
                         "JOIN Vente_Médicament vm ON v.id_vente = vm.id_vente " +
                         "JOIN Médicament m ON vm.id_médicament = m.id_médicament " +
                         "ORDER BY v.date DESC " +
                         "LIMIT ?, ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, startIndex);  // Index de début pour la pagination
            statement.setInt(2, limit);       // Nombre de ventes à récupérer
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Vente vente = new Vente();
                vente.setId(resultSet.getInt("id_vente"));
                vente.setDate(resultSet.getTimestamp("date"));
                vente.setMedicament(resultSet.getString("nom"));
                vente.setQuantite(resultSet.getInt("quantité"));
                vente.setTotalVente(resultSet.getDouble("total_vente"));
                ventes.add(vente);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeResources(resultSet, connection, statement);
        }

        return ventes;
    }

 // Méthode pour récupérer toutes les ventes
    public static List<Vente> getAllVentes() {
        List<Vente> ventes = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT v.id_vente, v.date, v.total_vente, vm.id_médicament, m.nom, vm.quantité " +
                         "FROM Vente v " +
                         "JOIN Vente_Médicament vm ON v.id_vente = vm.id_vente " +
                         "JOIN Médicament m ON vm.id_médicament = m.id_médicament " +
                         "ORDER BY v.date DESC"; // Tri des ventes par date décroissante
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Vente vente = new Vente();
                vente.setId(resultSet.getInt("id_vente"));
                vente.setDate(resultSet.getTimestamp("date"));
                vente.setMedicament(resultSet.getString("nom"));
                vente.setQuantite(resultSet.getInt("quantité"));
                vente.setTotalVente(resultSet.getDouble("total_vente"));
                ventes.add(vente);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeResources(resultSet, connection, statement);
        }

        return ventes;
    }

    
    // Méthode pour récupérer le nombre total de ventes
    public static int getTotalVentes() {
        int total = 0;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT COUNT(*) FROM Vente";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                total = resultSet.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeResources(resultSet, connection, statement);
        }

        return total;
    }

    // Méthode utilitaire pour fermer les ressources
    private static void closeResources(ResultSet resultSet, Connection connection, Statement... statements) {
        try {
            if (resultSet != null) resultSet.close();
            for (Statement stmt : statements) {  // Utilisation de Statement (plutôt que PreparedStatement)
                if (stmt != null) stmt.close();
            }
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
