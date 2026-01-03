package ehb.demo.config;

import ehb.demo.model.Product;
import ehb.demo.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(ProductRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                // Formaat: Naam, Categorie, Beschrijving, Prijs, Beschikbaar, STOCK
                repository.save(new Product("LED Paneel 600", "Verlichting", "Zacht invullicht", 15.0, true, 8));
                repository.save(new Product("Arri Fresnel 650W", "Verlichting", "Focusseerbaar spotlicht", 20.0, true, 3));
                repository.save(new Product("XLR Kabel 10m", "Kabels", "Audiokabel", 2.0, true, 25));
                repository.save(new Product("HDMI Kabel 5m", "Kabels", "4K ondersteuning", 3.5, true, 12));
                repository.save(new Product("Canon EOS R5", "Camera", "45MP Full Frame", 55.0, true, 2));
                repository.save(new Product("Sony A7 III", "Camera", "Video powerhouse", 45.0, true, 4));
                repository.save(new Product("Statief Manfrotto", "Accessoires", "Stevig videostatief", 10.0, true, 6));
                repository.save(new Product("Rode VideoMic", "Audio", "On-camera microfoon", 12.0, true, 5));
                repository.save(new Product("Softbox 80cm", "Verlichting", "Voor portretten", 8.0, true, 10));

                System.out.println("Database gevuld met stock-informatie!");
            }
        };
    }
}