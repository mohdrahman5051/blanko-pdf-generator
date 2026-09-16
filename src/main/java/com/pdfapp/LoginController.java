package com.pdfapp;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        User user = userRepository.findByEmail(email);

        if (user == null) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Account not found.");

            return "redirect:/login";
        }

        if (!user.getPassword().equals(password)) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Incorrect password.");

            return "redirect:/login";
        }

        session.setAttribute("userEmail", email);

        redirectAttributes.addFlashAttribute(
                "success",
                "Welcome back, " + user.getName() + "!");

        System.out.println("LOGIN SESSION EMAIL = " + email);
        System.out.println("LOGIN SESSION ID = " + session.getId());

        return "redirect:/dashboard";
    }
}