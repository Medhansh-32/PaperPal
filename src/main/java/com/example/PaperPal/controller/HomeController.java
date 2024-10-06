package com.example.PaperPal.controller;

import com.example.PaperPal.entity.UserDto;
import com.example.PaperPal.entity.Users;
import com.example.PaperPal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class HomeController{


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
    public UserService userService;


    @PostMapping("user/register")
    public String register(@ModelAttribute UserDto user) {

        Boolean check=userService.registerUser(   Users.builder()
                .userName(user.getFirstName()+" "+user.getLastName())
                .password(user.getPassword())
                .email(user.getEmail())
                .build());

        if(check){
            return "home";
        }else{
            return "unsuccesfull";
        }
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
