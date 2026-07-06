package com.pdfapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    @Value("${blanko.upload-dir}")
    private String uploadDirectory;

    public String saveLogo(MultipartFile file)
            throws IOException {

        return saveFile(file, "logos");
    }

    public String saveSignature(MultipartFile file)
            throws IOException {

        return saveFile(file, "signatures");
    }

    private String saveFile(
            MultipartFile file,
            String folder)
            throws IOException {

        if (file == null || file.isEmpty()) {
            return null;
        }

        String originalFileName =
                file.getOriginalFilename();

        String extension = "";

        if (originalFileName != null &&
                originalFileName.contains(".")) {

            extension = originalFileName.substring(
                    originalFileName.lastIndexOf("."));
        }

        String newFileName =
                System.currentTimeMillis()
                        + extension;

        Path folderPath =
                Paths.get(uploadDirectory, folder);

        Files.createDirectories(folderPath);

        Path filePath =
                folderPath.resolve(newFileName);

        file.transferTo(filePath.toFile());

        return folder + "/" + newFileName;

    }

}