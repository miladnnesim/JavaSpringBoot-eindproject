package ehb.demo.service;

import ehb.demo.model.User;
import ehb.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registreerGebruiker(String email, String password) throws Exception {
        // 1. Check of e-mail al bestaat
        if (userRepository.findByEmail(email).isPresent()) {
            throw new Exception("Er bestaat al een account met dit emailadres.");
        }

        // 2. Wachtwoord complexiteit check (RegEx)
        // Minimaal 8 tekens, 1 hoofdletter, 1 kleine letter, 1 cijfer en 1 speciaal teken
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!.])(?=\\S+$).{8,}$";
        if (!password.matches(passwordPattern)) {
            throw new Exception("Wachtwoord moet minimaal 8 tekens bevatten, inclusief een hoofdletter, cijfer en speciaal teken.");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
}