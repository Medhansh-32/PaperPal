package com.example.PaperPal.controller;

import com.example.PaperPal.entity.OtpDetails;
import com.example.PaperPal.entity.UserDto;
import com.example.PaperPal.entity.Users;
import com.example.PaperPal.repository.UserRepository;
import com.example.PaperPal.service.OtpService;
import com.example.PaperPal.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class UserController {

    private final UserService userService;
    private final OtpService otpService;
    private final UserRepository userRepository;

    public UserController(UserService userService, OtpService otpService, UserRepository userRepository) {
        this.userService = userService;
        this.otpService = otpService;
        this.userRepository = userRepository;
    }

    @GetMapping("/changePassword/{email}")
    public OtpDetails changePassword(@PathVariable String email) {
        try{
            return otpService.sendOtp(email);
        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }

    }

    @PostMapping("/otp")
    public String sendOtp(@RequestParam String email,@RequestParam String otp) {

        if(otpService.validateOtp(email,otp)){
            return "user verified...";
        }else{
            return "wrong OTP";
        }
    }


}
