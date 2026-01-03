package ehb.demo.controller;

import ehb.demo.model.Product;
import ehb.demo.repository.ProductRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/catalogus")
    public String toonCatalogus(
            @RequestParam(required = false, defaultValue = "Alle") String categorie,
            @RequestParam(required = false, defaultValue = "") String zoekterm,
            @RequestParam(required = false, defaultValue = "naam_asc") String sort, // Nieuwe parameter
            Model model) {

        // 1. Bepaal de sorteer-logica
        Sort sorteerObject = switch (sort) {
            case "naam_desc" -> Sort.by("naam").descending();
            case "prijs_asc" -> Sort.by("prijsPerDag").ascending();
            case "prijs_desc" -> Sort.by("prijsPerDag").descending();
            case "stock_desc" -> Sort.by("stock").descending();
            default -> Sort.by("naam").ascending(); // naam_asc is de fallback
        };

        Iterable<Product> producten;

        // 2. Filteren EN Sorteren
        if (!categorie.equals("Alle") && !zoekterm.isEmpty()) {
            producten = productRepository.findByCategorieAndNaamContainingIgnoreCase(categorie, zoekterm, sorteerObject);
        } else if (!categorie.equals("Alle")) {
            producten = productRepository.findByCategorie(categorie, sorteerObject);
        } else if (!zoekterm.isEmpty()) {
            producten = productRepository.findByNaamContainingIgnoreCase(zoekterm, sorteerObject);
        } else {
            producten = productRepository.findAll(sorteerObject);
        }

        // 3. Gegevens terugsturen naar de HTML
        model.addAttribute("producten", producten);
        model.addAttribute("geselecteerdeCategorie", categorie);
        model.addAttribute("zoekterm", zoekterm);
        model.addAttribute("sort", sort); // Zorgt dat de dropdown de juiste keuze onthoudt

        return "catalogus";
    }
}