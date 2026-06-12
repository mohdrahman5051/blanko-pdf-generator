package com.pdfapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {


@GetMapping("/")
public String home(HttpSession session) {

    String sessionEmail =
            (String) session.getAttribute("userEmail");

    if(sessionEmail == null) {
        return "redirect:/login";
    }

    return "dashboard";
}

@GetMapping("/dashboard")
public String dashboard(HttpSession session) {

    String sessionEmail =
            (String) session.getAttribute("userEmail");

    if(sessionEmail == null) {
        return "redirect:/login";
    }

    return "dashboard";
}


}
