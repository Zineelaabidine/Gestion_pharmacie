package modele;

import java.util.Objects;

public class Stock {
    private int id;
    private int id_médicament;
    private int quantité;
    private int id_admin;
    
    // Constructeurs
    public Stock() {
    }
    
    public Stock(int id_médicament, int quantité, int id_admin) {
        this.id_médicament = id_médicament;
        this.quantité = quantité;
        this.id_admin = id_admin;
    }
    
    public Stock(int id, int id_médicament, int quantité, int id_admin) {
        this.id = id;
        this.id_médicament = id_médicament;
        this.quantité = quantité;
        this.id_admin = id_admin;
    }
    
    // Getters et Setters existants
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId_médicament() {
        return id_médicament;
    }
    
    public void setId_médicament(int id_médicament) {
        this.id_médicament = id_médicament;
    }
    
    public int getQuantité() {
        return quantité;
    }
    
    public void setQuantité(int quantité) {
        this.quantité = quantité;
    }
    
    public int getId_admin() {
        return id_admin;
    }
    
    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }
    
    // Méthodes utilitaires
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return id == stock.id &&
               id_médicament == stock.id_médicament &&
               id_admin == stock.id_admin;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, id_médicament, id_admin);
    }
    
    @Override
    public String toString() {
        return "Stock{" +
               "id=" + id +
               ", id_médicament=" + id_médicament +
               ", quantité=" + quantité +
               ", id_admin=" + id_admin +
               '}';
    }
    
    // Méthode utilitaire pour vérifier si le stock est faible
    public boolean isLowStock(int threshold) {
        return quantité < threshold;
    }
    
    // Méthode pour augmenter le stock
    public void augmenterStock(int amount) {
        if (amount > 0) {
            this.quantité += amount;
        }
    }
    
    // Méthode pour diminuer le stock
    public boolean diminuerStock(int amount) {
        if (amount > 0 && this.quantité >= amount) {
            this.quantité -= amount;
            return true;
        }
        return false;
    }
}