package com.example.PaperPal.controller;


import com.example.PaperPal.service.DoubtsService;
import com.example.PaperPal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;


@Controller
public class HomeController{

    private final DoubtsService doubtsService;
    public UserService userService;

    @Autowired
    public HomeController(UserService userService, DoubtsService doubtsService){
        this.userService = userService;
        this.doubtsService = doubtsService;
    }


    @GetMapping()
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
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
    @GetMapping("/user/newPassword")
    public String newPassword(@RequestParam String email, Model model) {
        model.addAttribute("email",email);
        return "newPassword";
    }
    @GetMapping("/user/register")
    public String register() {
        return "register";
    }
    @GetMapping("/user/forgotPassword")
    public String forgotPassword() {
        return "forgotPassword";
    }

    @GetMapping("/allDoubts")
    public String getAllDoubts(Model model) {
        model.addAttribute("doubts",doubtsService.getAllDoubts());
        return "allDoubts";
    }

}
