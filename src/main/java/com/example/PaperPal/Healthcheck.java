package com.example.PaperPal;

import com.example.PaperPal.entity.UserResponse;
import com.example.PaperPal.service.Pdfanalyzer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class Healthcheck {

    private final Pdfanalyzer pdfanalyzer;

    public Healthcheck(Pdfanalyzer pdfanalyzer) {
        this.pdfanalyzer = pdfanalyzer;
    }

    @GetMapping("/health")
    public String healthCheck() {
     return "Status : Healthy";
    }

    @PostMapping("/aiTest")
    public void aiTest(@RequestParam("file") MultipartFile file) throws IOException {
        pdfanalyzer.analyzePdf(null,"pyq","medhansh",file.getBytes(),file.getOriginalFilename());
    }

}
