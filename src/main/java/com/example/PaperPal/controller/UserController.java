package com.example.PaperPal.controller;

import com.example.PaperPal.entity.Users;
import com.example.PaperPal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    public UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Users user) {

        Boolean check=userService.registerUser(user);
        if(check){
            return ResponseEntity.ok("User registered successfully");
        }else{
            return new ResponseEntity<>("Not Registered", HttpStatus.BAD_REQUEST);
        }
    }

}
