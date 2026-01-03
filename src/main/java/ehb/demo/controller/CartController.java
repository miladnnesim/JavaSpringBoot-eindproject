package ehb.demo.controller;

import ehb.demo.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) { this.cartService = cartService; }

    @PostMapping("/cart/add/{id}")
    public String voegToe(@PathVariable Long id, @RequestParam int aantal, @RequestParam int dagen) {
        cartService.voegToe(id, aantal, dagen);
        return "redirect:/catalogus";
    }

    @GetMapping("/winkelmandje")
    public String toonMandje(Model model) {
        model.addAttribute("items", cartService.getItems());
        model.addAttribute("totaal", cartService.getTotaalPrijs());
        return "winkelmandje";
    }
    @PostMapping("/cart/update/{id}")
    public String updateItemInMandje(@PathVariable Long id,
                                     @RequestParam int aantal,
                                     @RequestParam int dagen) {
        // We hergebruiken de voegToe logica omdat de Map.put()
        // automatisch de oude waarden overschrijft voor dat ID.
        cartService.voegToe(id, aantal, dagen);
        return "redirect:/winkelmandje";
    }

    @PostMapping("/cart/remove/{id}")
    public String verwijder(@PathVariable Long id) {
        cartService.verwijder(id);
        return "redirect:/winkelmandje";
    }
}