package com.pdfapp;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Document;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.awt.Color;
import java.io.File;



public class CertificatePdfGenerator {


public static byte[] generateCertificate(
        String studentName,
        String courseName,
        Institute institute) {

    try {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Document document = new Document(PageSize.A4.rotate());
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);

        document.open();
        

PdfContentByte canvas = writer.getDirectContent();
        if(institute.getLogoPath() != null) {

    String logoFullPath =
            "C:/Users/mohdr/JavaProjects/Blanko/uploads/"
            + institute.getLogoPath();

    File logoFile =
            new File(logoFullPath);

    if(logoFile.exists()) {

        Image logo =
                Image.getInstance(
                        logoFullPath);

        logo.scaleToFit(90,90);

        logo.setAlignment(
                Element.ALIGN_CENTER);

        document.add(logo);
    }
}

        
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

Paragraph instituteName =
        new Paragraph(
                institute.getInstituteName().toUpperCase(),
                brandFont);

instituteName.setAlignment(
        Element.ALIGN_CENTER);

        

        Paragraph name = new Paragraph(studentName.toUpperCase(), nameFont);
        name.setAlignment(Element.ALIGN_CENTER);

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
        
        Image signature = null;

        if (institute.getSignaturePath() != null) {

    String signatureFullPath =
            "C:/Users/mohdr/JavaProjects/Blanko/uploads/"
                    + institute.getSignaturePath();

    File signatureFile =
            new File(signatureFullPath);

    if (signatureFile.exists()) {

       
if (signatureFile.exists()) {

    signature = Image.getInstance(signatureFullPath);

    signature.scaleToFit(140, 60);

    signature.setAlignment(Element.ALIGN_CENTER);
}
    }
}

Paragraph directorName =
        new Paragraph(
                institute.getDirectorName(),
                smallFont);

directorName.setAlignment(Element.ALIGN_CENTER);




        // ---------------- SPACING ----------------
        document.add(instituteName);
        document.add(new Paragraph(" "));
       
       
        document.add(new Paragraph(" "));
        
        document.add(course);
        document.add(new Paragraph(" "));
        document.add(date);
        document.add(new Paragraph(" "));
        document.add(certificateId);
        document.add(new Paragraph(" "));

        if(signature != null) {
                document.add(signature);
        }
        document.add(directorName);
        
        document.add(new Paragraph(" "));
        
        ColumnText.showTextAligned(
        canvas,
        Element.ALIGN_CENTER,
        new Phrase(
                studentName.toUpperCase(),
                nameFont),
        420,
        330,
        0);
        document.close();
        ColumnText.showTextAligned(
        canvas,
        Element.ALIGN_CENTER,
        new Phrase(
                studentName.toUpperCase(),
                nameFont),
        420,
        330,
        0);

        return outputStream.toByteArray();

    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}


}
