package ehb.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // We slaan de email van de lener op om te weten van wie de bestelling is
    private String userEmail;

    private LocalDateTime reservatieDatum;

    private double totaalPrijs;

    // Default constructor voor JPA
    public Reservation() {}

    public Reservation(String userEmail, double totaalPrijs) {
        this.userEmail = userEmail;
        this.totaalPrijs = totaalPrijs;
        this.reservatieDatum = LocalDateTime.now(); // Automatisch de datum van nu
    }
// Voeg dit toe aan je bestaande Reservation klasse:

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    private List<ReservationItem> items = new ArrayList<>();

    public List<ReservationItem> getItems() { return items; }
    public void setItems(List<ReservationItem> items) { this.items = items; }
    // Getters
    public Long getId() { return id; }
    public String getUserEmail() { return userEmail; }
    public LocalDateTime getReservatieDatum() { return reservatieDatum; }
    public double getTotaalPrijs() { return totaalPrijs; }

    // Setters (optioneel voor dit project)
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public void setTotaalPrijs(double totaalPrijs) { this.totaalPrijs = totaalPrijs; }
}