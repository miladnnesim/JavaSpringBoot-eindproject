package ehb.demo.model;

import jakarta.persistence.*;

@Entity
public class ReservationItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Reservation reservation;

    private int aantal;
    private int dagen;
    private double prijsOpDatMoment; // Goed voor de administratie als prijzen later wijzigen

    public ReservationItem() {}

    public ReservationItem(Product product, Reservation reservation, int aantal, int dagen) {
        this.product = product;
        this.reservation = reservation;
        this.aantal = aantal;
        this.dagen = dagen;
        this.prijsOpDatMoment = product.getPrijsPerDag();
    }

    // Getters
    public Product getProduct() { return product; }
    public int getAantal() { return aantal; }
    public int getDagen() { return dagen; }
}