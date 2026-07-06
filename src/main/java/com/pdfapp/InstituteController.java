package com.pdfapp;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

@Controller
public class InstituteController {

    @Autowired
    private InstituteRepository instituteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/institute")
    public String institutePage() {
        return "institute";
    }

    @PostMapping("/save-institute")
    public String saveInstitute(

            @RequestParam String instituteName,
            @RequestParam String directorName,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String website,

            @RequestParam MultipartFile logo,
            @RequestParam MultipartFile signature,

            HttpSession session)

            throws IOException {

        String sessionEmail =
                (String) session.getAttribute("userEmail");

        if(sessionEmail == null) {
            return "redirect:/login";
        }

        User user =
                userRepository.findByEmail(sessionEmail);

        Institute institute =
                instituteRepository.findByUserId(user.getId());

        if(institute == null) {
            institute = new Institute();
            institute.setUserId(user.getId());
        }

        institute.setInstituteName(instituteName);
        institute.setDirectorName(directorName);
        institute.setEmail(email);
        institute.setPhone(phone);
        institute.setWebsite(website);

        String logoPath =
                fileStorageService.saveLogo(logo);

        String signaturePath =
                fileStorageService.saveSignature(signature);

        institute.setLogoPath(logoPath);
        institute.setSignaturePath(signaturePath);

        instituteRepository.save(institute);

        return "redirect:/institute";

    }

}