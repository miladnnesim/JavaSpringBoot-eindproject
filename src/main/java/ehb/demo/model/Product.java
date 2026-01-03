package ehb.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naam;
    private String categorie;
    private String beschrijving;
    private double prijsPerDag;
    private boolean beschikbaar;
    private int stock; // <--- NIEUW

    public Product() {}

    // Update de constructor zodat we stock kunnen meegeven
    public Product(String naam, String categorie, String beschrijving, double prijsPerDag, boolean beschikbaar, int stock) {
        this.naam = naam;
        this.categorie = categorie;
        this.beschrijving = beschrijving;
        this.prijsPerDag = prijsPerDag;
        this.beschikbaar = beschikbaar;
        this.stock = stock;
    }

    // Getters en Setters
    public Long getId() { return id; }
    public String getNaam() { return naam; }
    public void setNaam(String naam) { this.naam = naam; }
    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }
    public String getBeschrijving() { return beschrijving; }
    public void setBeschrijving(String beschrijving) { this.beschrijving = beschrijving; }
    public double getPrijsPerDag() { return prijsPerDag; }
    public void setPrijsPerDag(double prijsPerDag) { this.prijsPerDag = prijsPerDag; }
    public boolean isBeschikbaar() { return beschikbaar; }
    public void setBeschikbaar(boolean beschikbaar) { this.beschikbaar = beschikbaar; }
    public int getStock() { return stock; } // <--- NIEUW
    public void setStock(int stock) { this.stock = stock; } // <--- NIEUW
}