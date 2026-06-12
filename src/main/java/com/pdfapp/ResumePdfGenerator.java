package com.pdfapp;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;

public class ResumePdfGenerator {


public static byte[] generateResume(
        String name,
        String email,
        String phone,
        String education,
        String skills,
        String summary,
        String projects,
        String experience,
        String certifications,
        String linkedin,
        String github) {

    try {

        ByteArrayOutputStream outputStream =
                new ByteArrayOutputStream();

        Document document = new Document();

        PdfWriter.getInstance(document, outputStream);

        document.open();

        Font headingFont =
                FontFactory.getFont(
                        FontFactory.HELVETICA_BOLD,
                        14);

        Font normalFont =
                FontFactory.getFont(
                        FontFactory.HELVETICA,
                        11);

        Font whiteTitle =
                FontFactory.getFont(
                        FontFactory.HELVETICA_BOLD,
                        24,
                        Color.WHITE);

        Font whiteNormal =
                FontFactory.getFont(
                        FontFactory.HELVETICA,
                        11,
                        Color.WHITE);

         Font sidebarHeading =
        FontFactory.getFont(
                FontFactory.HELVETICA_BOLD,
                12,
                Color.WHITE);               

        PdfPTable headerTable =
                new PdfPTable(1);

        headerTable.setWidthPercentage(100);

        PdfPCell headerCell =
                new PdfPCell();

        headerCell.setBackgroundColor(
                new Color(45, 62, 80));

        headerCell.setBorder(0);
        headerCell.setPadding(20);

        Paragraph headerName =
                new Paragraph(
                        name.toUpperCase(),
                        whiteTitle);

        headerName.setAlignment(
                Element.ALIGN_CENTER);

        headerCell.addElement(headerName);

        Paragraph headerContact =
                new Paragraph(
                        email + " | " + phone,
                        whiteNormal);

        headerContact.setAlignment(
                Element.ALIGN_CENTER);

        headerCell.addElement(headerContact);

        Paragraph headerLinks =
                new Paragraph(
                        linkedin + " | " + github,
                        whiteNormal);

        headerLinks.setAlignment(
                Element.ALIGN_CENTER);

        headerCell.addElement(headerLinks);

        headerTable.addCell(headerCell);

        document.add(headerTable);

        PdfPTable mainTable = new PdfPTable(2);

mainTable.setWidthPercentage(100);

mainTable.setWidths(new float[]{30f, 70f});


PdfPCell leftColumn = new PdfPCell();

leftColumn.setBackgroundColor(
new Color(45, 62, 80));

leftColumn.setPadding(15);

leftColumn.setBorder(0);


leftColumn.addElement(
new Paragraph(
"SKILLS",
sidebarHeading));

leftColumn.addElement(
new Paragraph(
skills,
whiteNormal));

leftColumn.addElement(
new Paragraph(" "));

leftColumn.addElement(
new Paragraph(
"CERTIFICATIONS",
sidebarHeading));

leftColumn.addElement(
new Paragraph(
certifications,
whiteNormal));

leftColumn.addElement(
new Paragraph(" "));

leftColumn.addElement(
new Paragraph(
"LINKS",
sidebarHeading));

leftColumn.addElement(
new Paragraph(
"LinkedIn:\n" + linkedin,
whiteNormal));

leftColumn.addElement(
new Paragraph(
"\nGitHub:\n" + github,
whiteNormal));



PdfPCell rightColumn = new PdfPCell();

rightColumn.setPadding(15);

rightColumn.setBorder(0);


rightColumn.addElement(
        new Paragraph(
                "PROFESSIONAL SUMMARY",
                headingFont));

rightColumn.addElement(
        new Paragraph(
                summary,
                normalFont));

rightColumn.addElement(
        new Paragraph(" "));


rightColumn.addElement(
        new Paragraph(
                "EXPERIENCE",
                headingFont));

rightColumn.addElement(
        new Paragraph(
                experience,
                normalFont));

rightColumn.addElement(
        new Paragraph(" "));       
        
        
rightColumn.addElement(
        new Paragraph(
                "PROJECTS",
                headingFont));

rightColumn.addElement(
        new Paragraph(
                projects,
                normalFont));

rightColumn.addElement(
        new Paragraph(" "));     
        
        

rightColumn.addElement(
        new Paragraph(
                "EDUCATION",
                headingFont));

rightColumn.addElement(
        new Paragraph(
                education,
                normalFont));        

mainTable.addCell(leftColumn);
mainTable.addCell(rightColumn);


        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));


        document.add(mainTable);

        Paragraph generatedDate =
                new Paragraph(
                        "Generated On: "
                                + LocalDate.now(),
                        normalFont);

        generatedDate.setAlignment(
                Element.ALIGN_RIGHT);

        document.add(generatedDate);

        document.close();

        return outputStream.toByteArray();

    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}


}
