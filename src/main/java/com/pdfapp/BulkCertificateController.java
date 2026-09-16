package com.pdfapp;

import com.opencsv.CSVReader;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
public class BulkCertificateController {

    private final UserRepository userRepository;
    private final InstituteRepository instituteRepository;

    public BulkCertificateController(
            UserRepository userRepository,
            InstituteRepository instituteRepository) {

        this.userRepository = userRepository;
        this.instituteRepository = instituteRepository;
    }

    @GetMapping("/bulk-certificate")
    public String bulkCertificatePage(HttpSession session) {

        String sessionEmail =
                (String) session.getAttribute("userEmail");

        if (sessionEmail == null) {
            return "redirect:/login";
        }

        User user =
                userRepository.findByEmail(sessionEmail);

        Institute institute =
                instituteRepository.findByUserId(user.getId());

        if (institute == null) {
            return "redirect:/institute";
        }

        return "bulk-certificate";
    }

    @PostMapping("/generate-bulk-certificates")
    public ResponseEntity<byte[]> generateBulkCertificates(
            MultipartFile csvFile,
            HttpSession session) {

        try {

            String sessionEmail =
                    (String) session.getAttribute("userEmail");

            if (sessionEmail == null) {
                return ResponseEntity.status(401).build();
            }

            User user =
                    userRepository.findByEmail(sessionEmail);

            Institute institute =
                    instituteRepository.findByUserId(user.getId());

            CSVReader reader =
                    new CSVReader(
                            new InputStreamReader(
                                    csvFile.getInputStream()));

            ByteArrayOutputStream zipByteStream =
                    new ByteArrayOutputStream();

            ZipOutputStream zipOut =
                    new ZipOutputStream(zipByteStream);

            String[] row;

            while ((row = reader.readNext()) != null) {

                String studentName = row[0];
                String courseName = row[1];

                byte[] pdfBytes =
                        CertificatePdfGenerator.generateCertificate(
                                studentName,
                                courseName,
                                institute);

                String fileName =
                        studentName.replace(" ", "_") + ".pdf";

                ZipEntry zipEntry =
                        new ZipEntry(fileName);

                zipOut.putNextEntry(zipEntry);

                zipOut.write(pdfBytes);

                zipOut.closeEntry();
            }

            zipOut.close();
            reader.close();

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=certificates.zip")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(zipByteStream.toByteArray());

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity.internalServerError().build();
        }
    }
}