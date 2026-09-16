package com.pdfapp;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logout(
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        session.invalidate();

        redirectAttributes.addFlashAttribute(
                "success",
                "Logged out successfully.");

        return "redirect:/login";
    }

}