package com.pdfapp;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;

public class PdfGenerator {

    public static byte[] generatePdf() {

        try {

            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

            Document document = new Document();

            PdfWriter.getInstance(document, outputStream);

            document.open();

            document.add(new Paragraph("Student Report"));
            document.add(new Paragraph("WELCOME! "));
            document.add(new Paragraph("Name: Tahura"));
            document.add(new Paragraph("Course: Bachelor of Computer Applications"));
            document.add(new Paragraph("Generated using Java Spring Boot"));

            document.close();

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}