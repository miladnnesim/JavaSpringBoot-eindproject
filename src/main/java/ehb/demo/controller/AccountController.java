package ehb.demo.controller;

import ehb.demo.model.Reservation;
import ehb.demo.repository.ReservationRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AccountController {

    private final ReservationRepository reservationRepository;

    public AccountController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/mijn-reservaties")
    public String toonAccountPagina(@AuthenticationPrincipal UserDetails user, Model model) {
        // We halen de reservaties op basis van de ingelogde e-mail
        List<Reservation> reservaties = reservationRepository.findByUserEmailOrderByReservatieDatumDesc(user.getUsername());

        model.addAttribute("reservaties", reservaties);
        return "reservaties";
    }
}