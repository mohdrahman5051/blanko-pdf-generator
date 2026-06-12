package com.pdfapp;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DashboardController {

    private final DocumentRepository documentRepository;

    public DashboardController(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    // ---------------- COUNTS ----------------

    @GetMapping("/api/count/resume")
    public long resumeCount() {
        return documentRepository.countByDocumentType("Resume");
    }

    @GetMapping("/api/count/invoice")
    public long invoiceCount() {
        return documentRepository.countByDocumentType("Invoice");
    }

    @GetMapping("/api/count/certificate")
    public long certificateCount() {
        return documentRepository.countByDocumentType("Certificate");
    }

    // ---------------- RECENT DOCUMENTS ----------------

    @GetMapping("/api/documents/recent")
    public List<Document> getRecentDocuments() {
        return documentRepository.findAll(
                PageRequest.of(
                        0,
                        5,
                        Sort.by("createdAt").descending()
                )
        ).getContent();
    }
}