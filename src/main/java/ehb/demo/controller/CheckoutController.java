package ehb.demo.controller;

import ehb.demo.model.*;
import ehb.demo.repository.*;
import ehb.demo.service.CartService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CheckoutController {
    private final CartService cartService;
    private final ProductRepository productRepository;
    private final ReservationRepository reservationRepository;

    public CheckoutController(CartService cartService, ProductRepository productRepository, ReservationRepository reservationRepository) {
        this.cartService = cartService;
        this.productRepository = productRepository;
        this.reservationRepository = reservationRepository;
    }

    @PostMapping("/checkout")
    @Transactional
    public String verwerkCheckout(@AuthenticationPrincipal UserDetails currentUser, RedirectAttributes ra) {
        if (cartService.getItems().isEmpty()) return "redirect:/catalogus";

        // 1. Maak de hoofdreservatie aan
        Reservation reservation = new Reservation(currentUser.getUsername(), cartService.getTotaalPrijs());


        // 2. Loop door de winkelmand en maak items + verlaag stock
        List<ReservationItem> reservationItems = new ArrayList<>();

        for (CartItem cartItem : cartService.getItems()) {
            Product p = productRepository.findById(cartItem.getProduct().getId()).orElseThrow();

            // Stock check
            if (p.getStock() < cartItem.getAantal()) {
                ra.addFlashAttribute("errorMessage", "Niet genoeg stock voor " + p.getNaam());
                return "redirect:/winkelmandje";
            }

            // Verlaag stock
            p.setStock(p.getStock() - cartItem.getAantal());
            productRepository.save(p);

            // Maak detail-item aan
            ReservationItem resItem = new ReservationItem(p, reservation, cartItem.getAantal(), cartItem.getDagen());
            reservationItems.add(resItem);
        }
        // 3. Koppel items aan reservatie en sla alles op
        reservation.setItems(reservationItems);
        reservationRepository.save(reservation);

        cartService.leegmaken();
        return "redirect:/bevestiging";
    }
    @GetMapping("/bevestiging")
    public String toonBevestiging() {
        return "bevestiging"; // Spring zoekt naar bevestiging.html
    }
}