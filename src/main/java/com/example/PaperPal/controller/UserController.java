package com.example.PaperPal.controller;

import com.example.PaperPal.entity.OtpDetails;
import com.example.PaperPal.repository.UserRepository;
import com.example.PaperPal.service.OtpService;
import com.example.PaperPal.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
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

    @PostMapping("/changePassword")
    public ResponseEntity changePassword(@RequestParam String email, HttpServletResponse response) {
        try{
            otpService.sendOtp(email);
           // response.sendRedirect("/otpPage");
            return new ResponseEntity<>(HttpStatus.OK);

        }catch (Exception e){

            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/otp")
    public ResponseEntity validateOtp(@RequestParam String email, @RequestParam String otp) {

        if(otpService.validateOtp(email,otp)){
            log.info("Checking otp...");
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
