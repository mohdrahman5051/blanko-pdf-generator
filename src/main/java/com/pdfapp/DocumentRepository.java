package com.pdfapp;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface DocumentRepository
extends JpaRepository<Document, Long> {


    List<Document> findByDocumentType(String documentType);

List<Document> findByUserId(Long userId);

long countByDocumentType(String documentType);

long countByUserId(Long userId);
}
