package com.pdfapp;

import java.time.LocalDate;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;

public class InvoicePdfGenerator {


public static byte[] generateInvoice(
        String customerName,
        String productName,
        int quantity,
        double price) {

    try {

        ByteArrayOutputStream outputStream =
                new ByteArrayOutputStream();

        Document document = new Document();

        PdfWriter.getInstance(document, outputStream);

        document.open();

        double total = quantity * price;

        String invoiceNumber =
                "INV-" + System.currentTimeMillis();

        document.add(new Paragraph("=========================================="));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("BLANKO INVOICE"));
        document.add(new Paragraph("Professional Billing Document"));

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Invoice Number: "
                + invoiceNumber));

        document.add(new Paragraph("Invoice Date: "
                + LocalDate.now()));

        document.add(new Paragraph(" "));
        document.add(new Paragraph("=========================================="));

        document.add(new Paragraph(" "));
        document.add(new Paragraph("CLIENT DETAILS"));

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Customer: "
                + customerName));

        document.add(new Paragraph(" "));
        document.add(new Paragraph("=========================================="));

        document.add(new Paragraph(" "));
        document.add(new Paragraph("PURCHASE DETAILS"));

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Product: "
                + productName));

        document.add(new Paragraph("Quantity: "
                + quantity));

        document.add(new Paragraph("Price Per Unit: ₹"
                + price));

        document.add(new Paragraph(" "));

        document.add(new Paragraph(
                "TOTAL AMOUNT: ₹" + total));

        document.add(new Paragraph(" "));
        document.add(new Paragraph("=========================================="));

        document.add(new Paragraph(" "));
        document.add(new Paragraph(
                "Thank you for your business."));

        document.add(new Paragraph(" "));
        document.add(new Paragraph("BLANKO"));

        document.close();

        return outputStream.toByteArray();

    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}


}
