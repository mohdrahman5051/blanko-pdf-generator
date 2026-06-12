package com.pdfapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;


@Controller
public class LoginController {


@Autowired
private UserRepository userRepository;

@GetMapping("/login")
public String loginPage() {
    return "login";
}

@PostMapping("/login")

public String loginUser(
        @RequestParam String email,
        @RequestParam String password,
        HttpSession session) {

    User user =
            userRepository
                    .findByEmail(email);

    if(user == null) {
        return "User not found";
    }

    if(!user.getPassword().equals(password)) {
        return "Invalid password";
    }


    session.setAttribute("userEmail", email);

    System.out.println("LOGIN SESSION EMAIL = " + email);
System.out.println("LOGIN SESSION ID = " + session.getId());

    return "redirect:/";
}


}
