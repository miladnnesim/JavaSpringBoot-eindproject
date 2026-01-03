package ehb.demo.controller;

import ehb.demo.service.CartService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final CartService cartService;

    public GlobalControllerAdvice(CartService cartService) {
        this.cartService = cartService;
    }

    // Dit zorgt ervoor dat ${cartCount} in al je HTML-bestanden werkt
    @ModelAttribute("cartCount")
    public int getCartCount() {
        return cartService.getCartCount();
    }
}