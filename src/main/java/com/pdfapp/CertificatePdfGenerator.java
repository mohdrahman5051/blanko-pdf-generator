package com.pdfapp;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Document;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.awt.Color;

public class CertificatePdfGenerator {


public static byte[] generateCertificate(
        String studentName,
        String courseName) {

    try {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, outputStream);

        document.open();

        // ---------------- TITLE ----------------
        Font titleFont = new Font(
        Font.TIMES_ROMAN,
        32,
        Font.BOLD,
        new Color(139,69,19));
        Font subFont = new Font(Font.HELVETICA, 14, Font.ITALIC);
        Font nameFont = new Font(
        Font.TIMES_ROMAN,
        30,
        Font.BOLD,
        new Color(25,25,25));
        Font normalFont = new Font(Font.HELVETICA, 12, Font.NORMAL);
        Font smallFont = new Font(Font.HELVETICA, 10, Font.ITALIC);

        Font brandFont = new Font(
        Font.TIMES_ROMAN,
        22,
        Font.BOLD,
        new Color(184,134,11));

Paragraph blanko =
        new Paragraph("BLANKO", brandFont);
        blanko.setAlignment(Element.ALIGN_CENTER);

        Paragraph line1 = new Paragraph("__________________________________________", normalFont);
        line1.setAlignment(Element.ALIGN_CENTER);

        Paragraph certificateTitle = new Paragraph("CERTIFICATE OF COMPLETION", titleFont);
        certificateTitle.setAlignment(Element.ALIGN_CENTER);

        Paragraph subtitle = new Paragraph("This is proudly presented to", subFont);
        subtitle.setAlignment(Element.ALIGN_CENTER);

        Paragraph name = new Paragraph(studentName.toUpperCase(), nameFont);
        name.setAlignment(Element.ALIGN_CENTER);

        Paragraph text1 = new Paragraph(
                "for successfully completing the course", normalFont);
        text1.setAlignment(Element.ALIGN_CENTER);

        Paragraph course = new Paragraph(courseName, new Font(Font.HELVETICA, 18, Font.BOLD));
        course.setAlignment(Element.ALIGN_CENTER);

        Paragraph date = new Paragraph(
                "Date of Completion: " + LocalDate.now(),
                normalFont);
        date.setAlignment(Element.ALIGN_CENTER);


        Paragraph certificateId =
        new Paragraph(
        "Certificate ID: CERT-"
        + System.currentTimeMillis(),
        smallFont);

certificateId.setAlignment(
        Element.ALIGN_CENTER);

        Paragraph signature = new Paragraph(
        "━━━━━━━━━━━━━━━━━━━━━━━━",
        normalFont);
        signature.setAlignment(Element.ALIGN_CENTER);

        Paragraph signText = new Paragraph("Authorized Signature", smallFont);
        signText.setAlignment(Element.ALIGN_CENTER);

        Paragraph footer = new Paragraph("Blanko - Document Generator System", smallFont);
        footer.setAlignment(Element.ALIGN_CENTER);

        // ---------------- SPACING ----------------
        document.add(blanko);
        document.add(new Paragraph(" "));
        document.add(line1);
        document.add(new Paragraph(" "));
        document.add(certificateTitle);
        document.add(new Paragraph(" "));
        document.add(subtitle);
        document.add(new Paragraph(" "));
        document.add(name);
        document.add(new Paragraph(" "));
        document.add(text1);
        document.add(new Paragraph(" "));
        document.add(course);
        document.add(new Paragraph(" "));
        document.add(date);
        document.add(new Paragraph(" "));
        document.add(certificateId);
        document.add(new Paragraph(" "));
        document.add(signature);
        document.add(signText);
        document.add(new Paragraph(" "));
        document.add(footer);

        document.close();

        return outputStream.toByteArray();

    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}


}
