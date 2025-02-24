package modele;

public class Pharmacien {
    private int id_pharmacien;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private int id_admin;
    
    // Constructeurs
    public Pharmacien() {}
    
    public Pharmacien(int id_pharmacien, String nom, String prenom, String email, String motDePasse, int id_admin) {
        this.id_pharmacien = id_pharmacien;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.id_admin = id_admin;
    }
    
    // Getters et Setters
    public int getId_pharmacien() {
        return id_pharmacien;
    }
    
    public void setId_pharmacien(int id_pharmacien) {
        this.id_pharmacien = id_pharmacien;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getMotDePasse() {
        return motDePasse;
    }
    
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
    
    public int getId_admin() {
        return id_admin;
    }
    
    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }
}