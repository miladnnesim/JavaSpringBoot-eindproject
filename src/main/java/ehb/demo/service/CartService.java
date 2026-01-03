package ehb.demo.service;

import ehb.demo.model.CartItem;
import ehb.demo.model.Product;
import ehb.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import java.util.*;

@Service
@SessionScope // Maakt dit uniek per browser-sessie
public class CartService {
    private final ProductRepository productRepository;
    private final Map<Long, CartItem> items = new HashMap<>();

    public CartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void voegToe(Long id, int aantal, int dagen) {
        productRepository.findById(id).ifPresent(p -> {
            items.put(id, new CartItem(p, aantal, dagen));
        });
    }
    // Voeg deze methode toe aan je bestaande CartService
    public int getCartCount() {
        return items.values().stream()
                .mapToInt(CartItem::getAantal)
                .sum();
    }

    public void verwijder(Long id) { items.remove(id); }
    public Collection<CartItem> getItems() { return items.values(); }
    public void leegmaken() { items.clear(); }
    public double getTotaalPrijs() {
        return items.values().stream().mapToDouble(CartItem::getSubtotaal).sum();
    }
}