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
public class InvoiceController {


@Autowired
private DocumentRepository documentRepository;

@Autowired
private UserRepository userRepository;

@GetMapping("/invoice")
public String invoicePage(HttpSession session) {

    String sessionEmail =
            (String) session.getAttribute("userEmail");

    if(sessionEmail == null) {
        return "redirect:/login";
    }

    return "invoice";
}

@PostMapping("/generate-invoice")
public ResponseEntity<byte[]> generateInvoice(
        @RequestParam String customerName,
        @RequestParam String productName,
        @RequestParam int quantity,
        @RequestParam double price,
        HttpSession session) {

    String sessionEmail =
            (String) session.getAttribute("userEmail");

    System.out.println("INVOICE SESSION EMAIL = " + sessionEmail);
    System.out.println("INVOICE SESSION ID = " + session.getId());

    if (sessionEmail == null) {
        return ResponseEntity.status(401).build();
    }

    User user =
            userRepository.findByEmail(sessionEmail);

    if (user == null) {
        return ResponseEntity.status(404).build();
    }

    byte[] pdfBytes =
            InvoicePdfGenerator.generateInvoice(
                    customerName,
                    productName,
                    quantity,
                    price);

    Document document = new Document();

    document.setUserId(user.getId());
    document.setDocumentType("Invoice");
    document.setFileName("invoice.pdf");
    document.setCreatedAt(
            new Timestamp(
                    System.currentTimeMillis()));

    System.out.println("INVOICE SAVE EXECUTED");

    documentRepository.save(document);

    System.out.println("INVOICE SAVED SUCCESSFULLY");

    return ResponseEntity.ok()
            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=invoice.pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdfBytes);
}


}
