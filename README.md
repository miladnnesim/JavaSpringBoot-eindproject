# RentalApp - Materiaalverhuur Systeem

Dit project is een volledige **Spring Boot 3** webapplicatie voor het huren van technisch materiaal (lampen, camera's, kabels). Het systeem is ontworpen met een sterke focus op **security**, **usability** en **data-integriteit**.

## Kernfunctionaliteiten

### 1. Gebruikersbeheer & Security

* **Registratie:** Inclusief scherpe wachtwoordvalidatie (hoofdletters, cijfers, speciale tekens) en controle op unieke e-mailadressen.
* **Authenticatie:** Beveiligd inlogsysteem via **Spring Security** met custom login- en registratiepagina's.
* **Wachtwoordbeveiliging:** Gebruik van het **BCrypt** hashing-algoritme voor veilige opslag in MySQL.

### 2. Product Catalogus

* **Weergave:** Een modern overzicht van alle beschikbare materialen met Bootstrap-cards.
* **Filtering:** Krachtige zoekfunctie op naam en categorie-filters (Verlichting, Camera, etc.).
* **Sortering:** Sorteren op prijs (hoog/laag), naam (A-Z) en voorraadniveau via Spring Data `Sort`.
* **Voorraadbeheer:** Real-time weergave van de actuele stock.

### 3. Winkelwagen & Sessies

* **Session Management:** Gebruik van `@SessionScope` om winkelwagens per gebruiker uniek en tijdelijk op te slaan.
* **Dynamische Berekening:** Mogelijkheid om per item het aantal stuks en de huurperiode (dagen) aan te passen met automatische prijsberekening.
* **Stock-Validatie:** Voorkomt dat gebruikers meer kunnen huren dan de huidige voorraad toelaat.

### 4. Checkout & Reservaties

* **Transactionele Verwerking:** Gebruik van `@Transactional` om te garanderen dat de stock-verlaging en reservatie-opslag altijd gelijktijdig slagen.
* **Ordergeschiedenis:** Een persoonlijke accountpagina waar studenten hun eerdere reservaties en gehuurde items kunnen bekijken.

---

## 🛠 Technische Stack

| Component | Technologie |
| --- | --- |
| **Backend** | Java 17, Spring Boot 3.x |
| **Security** | Spring Security 6 (BCrypt, CSRF-protection) |
| **Database** | MySQL, Spring Data JPA (Hibernate) |
| **Frontend** | Thymeleaf, Bootstrap 5, Bootstrap Icons |
| **Build Tool** | Maven |

---

## Infrastructuur & Architectuur

De applicatie volgt het **MVC-patroon** (Model-View-Controller) voor een duidelijke scheiding van logica:

* **Models:** JPA Entities (`User`, `Product`, `Reservation`, `ReservationItem`) bepalen de database-structuur.
* **Repositories:** Interfaces die gebruikmaken van Spring Data voor efficiënte MySQL interactie.
* **Services:** Bevatten de business-logica, zoals wachtwoordvalidatie en winkelwagen-berekeningen.
* **Controllers:** Regelen de routing en de interactie tussen de frontend en de services.
* **Global Controller Advice:** Zorgt voor universele data-beschikbaarheid (zoals de cart-count in de navbar).

---

## 🔧 Installatie & Setup

1. **Database configuratie:**
Maak een MySQL database aan genaamd `rental_db`. Pas je `src/main/resources/application.properties` aan:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/rental_db
spring.datasource.username=jouw_username
spring.datasource.password=jouw_wachtwoord
spring.jpa.hibernate.ddl-auto=update

```


2. **App starten:**
Voer het project uit via je IDE of gebruik Maven:
```bash
mvn spring-boot:run

```


*Bij de eerste start vult de `DataLoader` de database automatisch met 10 testproducten.*
3. **Toegang:**
Surf naar `http://localhost:8080/catalogus`.

---


# Referenties
app secrets : https://chatgpt.com/share/69591b10-6dec-8011-a800-d36344c3b44f
ui en ux : https://gemini.google.com/share/6547b778aa9c
debugging: https://drive.google.com/drive/folders/1hJtvacMV74bG3qOMDvEdMAUQcm77lko3?usp=sharing
https://drive.google.com/file/d/1MwdHcTj8_MNbkGdt-ls-XX7jaPZPmJN5/view?usp=sharing


