package com.pdfapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

import java.sql.Timestamp;

@Controller
public class ResumeController {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;

    // Show resume form page
    @GetMapping("/resume")
public String resumePage(HttpSession session) {

    String sessionEmail =
            (String) session.getAttribute("userEmail");

    if(sessionEmail == null) {
        return "redirect:/login";
    }

    return "resume";
}

    // Generate resume PDF
    @PostMapping("/generate-resume")
    public ResponseEntity<byte[]> generateResume(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String education,
            @RequestParam String skills,
            @RequestParam String summary,
            @RequestParam String projects,
            @RequestParam String experience,
            @RequestParam String certifications,
            @RequestParam String linkedin,
            @RequestParam String github,
            HttpSession session
    ) {
            System.out.println("GENERATE RESUME METHOD CALLED");
        // 1. Get logged-in user email from session
        String sessionEmail = (String) session.getAttribute("userEmail");

        System.out.println("SESSION EMAIL = " + sessionEmail);
System.out.println("SESSION ID = " + session.getId());

        if (sessionEmail == null) {
            return ResponseEntity.status(401).build();
        }

        // 2. Fetch user from DB
        User user = userRepository.findByEmail(sessionEmail);

        if (user == null) {
            return ResponseEntity.status(404).build();
        }

        // 3. Generate PDF
        byte[] pdfBytes = ResumePdfGenerator.generateResume(
                name,
                email,
                phone,
                education,
                skills,
                summary,
                projects,
                experience,
                certifications,
                linkedin,
                github
        );

        // 4. Save document record
        Document document = new Document();
        document.setUserId(user.getId());
        document.setDocumentType("Resume");
        document.setFileName("resume.pdf");
        document.setCreatedAt(new Timestamp(System.currentTimeMillis()));


        System.out.println("DOCUMENT SAVE EXECUTED");

        documentRepository.save(document);

        System.out.println("DOCUMENT SAVED SUCCESSFULLY");

        // 5. Return PDF response
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=resume.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}