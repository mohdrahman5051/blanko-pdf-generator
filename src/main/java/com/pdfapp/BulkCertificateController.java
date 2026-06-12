package com.pdfapp;

import com.opencsv.CSVReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;

import java.io.ByteArrayOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Controller
public class BulkCertificateController {


@GetMapping("/bulk-certificate")
public String bulkCertificatePage() {
    return "bulk-certificate";
}

@PostMapping("/generate-bulk-certificates")
public ResponseEntity<byte[]> generateBulkCertificates(
MultipartFile csvFile) {


try {

    CSVReader reader =
            new CSVReader(
                    new InputStreamReader(
                            csvFile.getInputStream()));

    ByteArrayOutputStream zipByteStream =
            new ByteArrayOutputStream();

    ZipOutputStream zipOut =
            new ZipOutputStream(
                    zipByteStream);

    String[] row;

    while((row = reader.readNext()) != null) {

        String studentName = row[0];
        String courseName = row[1];

        byte[] pdfBytes =
                CertificatePdfGenerator
                        .generateCertificate(
                                studentName,
                                courseName);

        String fileName =
                studentName.replace(" ", "_")
                + ".pdf";

        ZipEntry zipEntry =
                new ZipEntry(fileName);

        zipOut.putNextEntry(zipEntry);

        zipOut.write(pdfBytes);

        zipOut.closeEntry();

        System.out.println(
                "ADDED TO ZIP: "
                        + fileName);
    }

    zipOut.close();
    reader.close();

    return ResponseEntity.ok()
            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=certificates.zip")
            .contentType(
                    MediaType.APPLICATION_OCTET_STREAM)
            .body(
                    zipByteStream.toByteArray());

} catch(Exception e) {

    e.printStackTrace();

    return ResponseEntity.internalServerError()
            .build();
}


}



}
