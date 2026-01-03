package ehb.demo.controller;

import ehb.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/login")
    public String toonLogin() {
        return "login"; // Spring zoekt nu naar templates/login.html
    }
    @GetMapping("/register")
    public String toonRegistratieFormulier() {
        return "register";
    }
    @PostMapping("/register")
    public String verwerkRegistratie(@RequestParam String email,
                                     @RequestParam String password,
                                     RedirectAttributes redirectAttributes) {
        try {
            userService.registreerGebruiker(email, password);
            redirectAttributes.addFlashAttribute("successMessage", "Registratie gelukt! Je kunt nu inloggen.");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/register";
        }
    }
}