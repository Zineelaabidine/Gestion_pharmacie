package modele;

import java.sql.Timestamp;

public class Vente {
    private int id;
    private Timestamp date;
    private double totalVente;
    private String medicament;
    private int quantite;
    
    // Constructeur par défaut
    public Vente() {}

    // Constructeur avec paramètres
    public Vente(int id, Timestamp date, double totalVente, String medicament, int quantite) {
        this.id = id;
        this.date = date;
        this.totalVente = totalVente;
        this.medicament = medicament;
        this.quantite = quantite;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public double getTotalVente() {
        return totalVente;
    }

    public void setTotalVente(double totalVente) {
        this.totalVente = totalVente;
    }

    public String getMedicament() {
        return medicament;
    }

    public void setMedicament(String medicament) {
        this.medicament = medicament;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    // Méthode pour afficher une vente (utile pour le débogage)
    @Override
    public String toString() {
        return "Vente [id=" + id + ", date=" + date + ", totalVente=" + totalVente +
                ", medicament=" + medicament + ", quantite=" + quantite + "]";
    }
}
