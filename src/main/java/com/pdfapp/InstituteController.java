package com.pdfapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InstituteController {

    @GetMapping("/institute")
    public String institutePage() {
        return "institute";
    }

}