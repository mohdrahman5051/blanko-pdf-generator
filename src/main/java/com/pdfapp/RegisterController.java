package com.pdfapp;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {

    private final UserRepository userRepository;

    public RegisterController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        User existingUser =
                userRepository.findByEmail(email);

        if (existingUser != null) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "An account with this email already exists.");

            return "redirect:/register";
        }

        User user = new User();

        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        userRepository.save(user);

        session.setAttribute("userEmail", email);

        redirectAttributes.addFlashAttribute(
                "success",
                "Account created successfully!");

        return "redirect:/dashboard";
    }
}