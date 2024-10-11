package com.example.PaperPal.controller;

import com.example.PaperPal.entity.UserDto;
import com.example.PaperPal.entity.Users;
import com.example.PaperPal.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;


@Controller
public class HomeController{

    public UserService userService;

    @Autowired
    public HomeController(UserService userService){
        this.userService = userService;
    }


    @GetMapping()
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }


    @PostMapping("/user")
    public String register(@ModelAttribute UserDto user, HttpServletResponse response) throws IOException {

        Boolean check=userService.registerUser(   Users.builder()
                .userName(user.getFirstName()+" "+user.getLastName())
                .password(user.getPassword())
                .email(user.getEmail())
                .build());

        if(check){
            response.sendRedirect("");

        }

            return "unsuccesfull";

    }
    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "forgotPassword";
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
    @GetMapping("/newPassword")
    public String newPassword(@RequestParam String email, Model model) {
        model.addAttribute("email",email);
        return "newPassword";
    }


}
