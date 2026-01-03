package ehb.demo.repository;

import ehb.demo.model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    // Voeg 'Sort' toe aan de parameters van je bestaande methodes
    List<Product> findByCategorieAndNaamContainingIgnoreCase(String categorie, String zoekterm, Sort sort);
    List<Product> findByCategorie(String categorie, Sort sort);
    List<Product> findByNaamContainingIgnoreCase(String zoekterm, Sort sort);
    List<Product> findAll(Sort sort);
}