package com.example.PaperPal;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class Healthcheck {



    @GetMapping("/health")
    public String healthCheck(HttpServletRequest request) {
        return "Hello "+request.getSession().getId();
    }


}
