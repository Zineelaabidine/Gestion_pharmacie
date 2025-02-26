package modele;

public class Medicament {
    private int id_medicament;
    private String nom;
    private String description;
    private int quantite;
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
    public Medicament(int id_medicament, String nom, int quantite, double prix) {
        this.id_medicament = id_medicament;
        this.nom = nom;
        
        this.prix = prix;
        this.quantite = quantite;
    }
    public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	// Getters et Setters
    public int getId_medicament() {
        return id_medicament;
    }
    
    public Medicament(String nom, double prix,int quantite ,int id_admin) {
		super();
		this.nom = nom;
		this.prix = prix;
		this.id_admin = id_admin;
	}

	public Medicament(String nom, int quantite, double prix) {
		super();
		this.nom = nom;
		this.quantite = quantite;
		this.prix = prix;
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