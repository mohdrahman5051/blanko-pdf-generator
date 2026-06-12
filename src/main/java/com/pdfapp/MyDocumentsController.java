package com.pdfapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class MyDocumentsController {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/my-documents")
    public String myDocuments(
            HttpSession session,
            Model model) {

        String email =
                (String) session.getAttribute("userEmail");

        if (email == null) {
            return "redirect:/login";
        }

        User user =
                userRepository.findByEmail(email);

        List<Document> documents =
                documentRepository.findByUserId(user.getId());

        model.addAttribute("documents", documents);

        return "mydocuments";
    }
}