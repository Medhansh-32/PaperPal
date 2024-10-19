package com.example.PaperPal.controller;

import com.example.PaperPal.entity.Doubts;
import com.example.PaperPal.entity.OtpDetails;
import com.example.PaperPal.entity.UserDto;
import com.example.PaperPal.entity.Users;
import com.example.PaperPal.repository.DoubtsRepository;
import com.example.PaperPal.repository.UserRepository;
import com.example.PaperPal.service.OtpService;
import com.example.PaperPal.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@Slf4j
@RestController
public class UserController {

    private final UserService userService;
    private final OtpService otpService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
    private final AuthenticationProvider authenticationProvider;
    private final DoubtsRepository doubtsRepository;

    public UserController(UserService userService, OtpService otpService, UserRepository userRepository, AuthenticationProvider authenticationProvider, DoubtsRepository doubtsRepository) {
        this.userService = userService;
        this.otpService = otpService;
        this.userRepository = userRepository;
        this.authenticationProvider = authenticationProvider;
        this.doubtsRepository = doubtsRepository;
    }
    @PostMapping("/user/redirectHome")
    public ResponseEntity<String> register(@RequestBody UserDto user) {
        // Check if the email is already taken
        boolean isRegistered = userService.registerUser(
                Users.builder()
                        .userName(user.getFirstName() + " " + user.getLastName())
                        .password(user.getPassword())
                        .email(user.getEmail())
                        .build()
        );

        if (isRegistered) {
            // If registration is successful
            log.info("User Registered");
            return ResponseEntity.ok("Registration successful");
        } else {
            // If email/username is already taken, return error
            log.info("User not registered");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Email or Username already registered.....");
        }
    }

    @PostMapping("/user/changePassword")
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
    public ResponseEntity validateOtp(@RequestParam String email,
                                      @RequestParam String otp,
                                      HttpServletResponse response,
                                      HttpServletRequest request) throws IOException {
        HttpSession session=request.getSession();
        if(otpService.validateOtp(email,otp)){
            log.info("Checking otp...");

            session.setAttribute("otpVerified","true");
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            session.setAttribute("otpVerified","false");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/setNewPassword")
    public ResponseEntity setNewPassword(@RequestBody UserDto userDto,HttpSession session){
        log.info(userDto.getEmail());
        if(session!=null && session.getAttribute("otpVerified")!=null){
            try {
                if(session.getAttribute("otpVerified").equals("true")){
                    Users user= userRepository.findByEmail(userDto.getEmail());
                    user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
                    userRepository.save(user);
                    log.info("password updated successfully");
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
        log.info("password not updated...");
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
