package com.example.PaperPal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController{



    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

    @GetMapping("/getlinks")
    public String getLinks() {
        return "getlinks";
    }

    @GetMapping("/addfile")
    public String addFile() {
        return "addfile";
    }

    @GetMapping("/delete")
    public String delete() {
        return "deleteresponse";
    }

}
