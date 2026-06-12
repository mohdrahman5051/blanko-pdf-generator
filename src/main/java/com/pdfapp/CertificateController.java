package com.pdfapp;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class CertificateController {


@Autowired
private DocumentRepository documentRepository;

@Autowired
private UserRepository userRepository;

@GetMapping("/certificate")
public String certificatePage(HttpSession session) {

    String sessionEmail =
            (String) session.getAttribute("userEmail");

    if(sessionEmail == null) {
        return "redirect:/login";
    }

    return "certificate";
}

@PostMapping("/generate-certificate")
public ResponseEntity<byte[]> generateCertificate(
        @RequestParam String studentName,
        @RequestParam String courseName,
        HttpSession session) {

    String sessionEmail =
            (String) session.getAttribute("userEmail");

System.out.println("CERTIFICATE SESSION EMAIL = " + sessionEmail);
System.out.println("CERTIFICATE SESSION ID = " + session.getId());


    if (sessionEmail == null) {
        return ResponseEntity.status(401).build();
    }

    User user =
            userRepository.findByEmail(sessionEmail);

    if (user == null) {
        return ResponseEntity.status(404).build();
    }

    byte[] pdfBytes =
            CertificatePdfGenerator.generateCertificate(
                    studentName,
                    courseName);

    Document document = new Document();

    document.setUserId(user.getId());
    document.setDocumentType("Certificate");
    document.setFileName("certificate.pdf");
    document.setCreatedAt(
            new Timestamp(
                    System.currentTimeMillis()));


     System.out.println("SAVING CERTIFICATE DOCUMENT");               
    documentRepository.save(document);
     System.out.println("CERTIFICATE DOCUMENT SAVED");


    return ResponseEntity.ok()
            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=certificate.pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdfBytes);
}


}
