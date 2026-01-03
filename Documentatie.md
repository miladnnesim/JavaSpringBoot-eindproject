# 📘 Technisch Ontwerp & Architectuur - RentalApp

## 1. Inleiding
Dit document beschrijft de technische keuzes, de architectuur en de complexe logica achter de RentalApp. De applicatie is gebouwd als een **Proof of Concept (PoC)** voor een verhuurplatform voor kunstonderwijs.

**Technische Stack:**
* **Framework:** Spring Boot 3 (Java 17)
* **Security:** Spring Security 6 met BCrypt
* **Database:** MySQL & Spring Data JPA
* **Frontend:** Thymeleaf + Bootstrap 5
* **Architectuur:** MVC (Model-View-Controller)

---

## 2. Security Module
**Betrokken bestanden:** `SecurityConfig.java`, `AuthController.java`, `UserService.java`

De beveiliging is niet standaard, maar aangepast aan de eisen van een veilige webapplicatie.

### 2.1 Configuratie (`SecurityConfig.java`)
We gebruiken de moderne `SecurityFilterChain` bean in plaats van de verouderde `WebSecurityConfigurerAdapter`.
* **Autorisatie Regels:**
    * `permitAll()` voor `/login`, `/register`, `/catalogus` en statische resources (`/css/**`).
    * `authenticated()` voor alle transactie-gerelateerde pagina's zoals `/winkelmandje` en `/checkout`.
* **Custom Login Flow:**
    * De applicatie gebruikt een eigen HTML-pagina (`login.html`) in plaats van de standaard Spring-pagina.
    * Bij falen wordt een parameter `?error=true` meegegeven voor foutafhandeling in de view.

### 2.2 Wachtwoord Encryptie
Er wordt gebruik gemaakt van de **BCryptPasswordEncoder**. Dit zorgt ervoor dat wachtwoorden nooit als platte tekst in de database staan (Salting & Hashing).

---

## 3. Catalogus & Zoeklogica
**Betrokken bestanden:** `ProductController.java`, `ProductRepository.java`, `DataLoader.java`

Dit is de kern van de gebruikerservaring. De logica moet flexibel omgaan met filters.

### 3.1 Dynamische Filtering (`ProductController.java`)
De methode `toonCatalogus` is complex omdat deze **drie variabelen** tegelijk moet verwerken:
1.  **Categorie:** (Bijv. "Verlichting" of "Alle").
2.  **Zoekterm:** (Vrije tekstinput).
3.  **Sortering:** (Prijs oplopend/aflopend, Naam, Stock).

In plaats van Java-streams te gebruiken om te filteren (wat traag is bij veel data), sturen we specifieke queries naar de database via de Repository. We gebruiken een Java `switch` (pattern matching) om het juiste `Sort` object aan te maken op basis van de dropdown-selectie van de gebruiker.

### 3.2 Data Seeding (`DataLoader.java`)
Om de Proof of Concept werkbaar te maken, wordt bij het opstarten via een `CommandLineRunner` gecheckt of de database leeg is. Indien ja, injecteren we automatisch 10 diverse producten met correcte stock-informatie.

---

## 4. Winkelmandje & Sessie Management
**Betrokken bestanden:** `CartService.java`, `CartController.java`, `CartItem.java`, `GlobalControllerAdvice.java`

Hier zit de meeste technische complexiteit qua state-management.

### 4.1 Sessie Scope (`CartService.java`)
De `CartService` is geannoteerd met `@SessionScope`.
* **Werking:** Spring maakt een unieke instantie van deze bean per `JSESSIONID`.
* **Voordeel:** Data van Student A (zijn mandje) is volledig gescheiden van Student B, zonder dat we hiervoor een tijdelijke database-tabel hoeven te gebruiken. Dit bespaart database-transacties en houdt de applicatie snel.

### 4.2 Data Beschikbaarheid (`GlobalControllerAdvice.java`)
Een technisch probleem bij MVC is dat de navbar op *elke* pagina staat, en dus op *elke* pagina moet weten hoeveel items er in het mandje zitten.
* **Oplossing:** In plaats van in elke Controller `model.addAttribute("cartCount", ...)` te typen, gebruiken we een `@ControllerAdvice`.
* **Resultaat:** De methode `getCartCount()` wordt automatisch uitgevoerd voor elke request, waardoor het rode bolletje in de navbar altijd correct is.

---

## 5. Transacties & Checkout (Complex)
**Betrokken bestanden:** `CheckoutController.java`, `Reservation.java`, `ReservationItem.java`

Het verwerken van een bestelling vereist strikte data-integriteit om "race conditions" (twee studenten huren tegelijk het laatste item) te voorkomen.

### 5.1 Transactionele Integriteit
De methode `verwerkCheckout` in `CheckoutController` is geannoteerd met `@Transactional`. Dit zorgt voor een **ACID**-transactie:
1.  Check voorraad.
2.  Update voorraad in `Product` tabel.
3.  Maak `Reservation` aan.
4.  Maak `ReservationItems` aan.

Als stap 4 faalt, worden stap 1, 2 en 3 automatisch teruggedraaid (Rollback). Hierdoor raakt de stock nooit uit sync met de werkelijkheid.

### 5.2 Stock Validatie
Tijdens de checkout itereren we door het mandje. Als `product.getStock() < gevraagdAantal` gooien we de gebruiker terug naar het mandje met een foutmelding, en wordt er niets opgeslagen.

---

## 6. Datamodel (Entities)
**Betrokken bestanden:** `User.java`, `Product.java`, `Reservation.java`, `ReservationItem.java`

Het datamodel is relationeel opgezet:
* **Product:** Bevat de `stock` (int) en `prijsPerDag` (double).
* **Reservation:** De "header" van de bestelling, gekoppeld aan een `User` via email.
* **ReservationItem:** De detailregels.
    * *Relatie:* Een `Reservation` heeft een `@OneToMany` relatie met `ReservationItem`.
    * *Cascade:* `CascadeType.ALL` zorgt ervoor dat als we de reservatie opslaan, alle items automatisch ook worden opgeslagen.

