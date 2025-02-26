package modele;

public class Medicament {
    private int id_medicament;
    private String nom;
    private String description;
    private double prix;
    private int id_admin;
    
    // Constructeurs
    public Medicament() {}
    
    public Medicament(int id_medicament, String nom, String description, double prix, int id_admin) {
        this.id_medicament = id_medicament;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.id_admin = id_admin;
    }
    
    // Getters et Setters
    public int getId_medicament() {
        return id_medicament;
    }
    
    public void setId_medicament(int id_medicament) {
        this.id_medicament = id_medicament;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public double getPrix() {
        return prix;
    }
    
    public void setPrix(double prix) {
        this.prix = prix;
    }
    
    public int getId_admin() {
        return id_admin;
    }
    
    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }
}