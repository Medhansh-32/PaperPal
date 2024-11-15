package com.example.PaperPal.controller;

import com.example.PaperPal.entity.Doubts;
import com.example.PaperPal.entity.OtpDetails;
import com.example.PaperPal.entity.UserDto;
import com.example.PaperPal.entity.Users;
import com.example.PaperPal.repository.DoubtsRepository;
import com.example.PaperPal.repository.UserRepository;
import com.example.PaperPal.service.OtpService;
import com.example.PaperPal.service.UserService;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
public class UserController {

    private final UserService userService;
    private final OtpService otpService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
    private final ConcurrentHashMap<String,UserDto> storeCode=new ConcurrentHashMap<>();
    private final AuthenticationProvider authenticationProvider;
    private final DoubtsRepository doubtsRepository;
    private final JavaMailSender javaMailSender;


    public UserController(UserService userService, OtpService otpService, UserRepository userRepository, AuthenticationProvider authenticationProvider, DoubtsRepository doubtsRepository, JavaMailSender javaMailSender) {
        this.userService = userService;
        this.otpService = otpService;
        this.userRepository = userRepository;
        this.authenticationProvider = authenticationProvider;
        this.doubtsRepository = doubtsRepository;
        this.javaMailSender = javaMailSender;
    }
    @PostMapping("/user/redirectHome")
    public ResponseEntity<String> register(@RequestBody UserDto user) throws MessagingException {
       try{
           String uniqueCode=bCryptPasswordEncoder.encode(user.getEmail());
           String request= ServletUriComponentsBuilder.fromCurrentRequest().path("?code="+uniqueCode).build().toString().replace("/user/redirectHome","/user/activate");
           MimeMessage mimeMessage = javaMailSender.createMimeMessage();
           MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
           mimeMessageHelper.setTo(user.getEmail());
           mimeMessageHelper.setText(
                   "<!DOCTYPE html>\n" +
                           "<html lang=\"en\">\n" +
                           "<head>\n" +
                           "    <meta charset=\"UTF-8\">\n" +
                           "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                           "    <title>Registration Confirmation</title>\n" +
                           "    <style>\n" +
                           "        body {\n" +
                           "            font-family: Arial, sans-serif;\n" +
                           "            background-color: #f4f4f4;\n" +
                           "            color: #333;\n" +
                           "            margin: 0;\n" +
                           "            padding: 0;\n" +
                           "        }\n" +
                           "        .container {\n" +
                           "            max-width: 600px;\n" +
                           "            margin: 20px auto;\n" +
                           "            padding: 20px;\n" +
                           "            background-color: #ffffff;\n" +
                           "            border-radius: 8px;\n" +
                           "            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);\n" +
                           "        }\n" +
                           "        .header {\n" +
                           "            text-align: center;\n" +
                           "            padding: 10px 0;\n" +
                           "        }\n" +
                           "        .header h1 {\n" +
                           "            color: #007bff;\n" +
                           "        }\n" +
                           "        .content {\n" +
                           "            font-size: 16px;\n" +
                           "            line-height: 1.6;\n" +
                           "            color: #333;\n" +
                           "        }\n" +
                           "        .content p {\n" +
                           "            margin: 0 0 10px;\n" +
                           "        }\n" +
                           "        .button-container {\n" +
                           "            text-align: center;\n" +
                           "            margin: 20px 0;\n" +
                           "        }\n" +
                           "        .button {\n" +
                           "            display: inline-block;\n" +
                           "            padding: 12px 24px;\n" +
                           "            color: #007bff;\n" +
                           "            background-color: #f4f4f4;\n" +
                           "            text-decoration: none;\n" +
                           "            border-radius: 5px;\n" +
                           "            font-weight: bold;\n" +
                           "        }\n" +
                           "        .footer {\n" +
                           "            font-size: 12px;\n" +
                           "            color: #666;\n" +
                           "            text-align: center;\n" +
                           "            margin-top: 20px;\n" +
                           "        }\n" +
                           "    </style>\n" +
                           "</head>\n" +
                           "<body>\n" +
                           "\n" +
                           "<div class=\"container\">\n" +
                           "    <div class=\"header\">\n" +
                           "        <h1>Welcome to PaperPal!</h1>\n" +
                           "    </div>\n" +
                           "    <div class=\"content\">\n" +
                           "        <p>Hello " + user.getFirstName() + " " + user.getLastName() + ",</p>\n" +
                           "        <p>Thank you for registering with us. Please confirm your email address by clicking the button below:</p>\n" +
                           "        <div class=\"button-container\">\n" +
                           "            <a href=\""+request+"\" class=\"button\">Confirm Your Email</a>\n" +
                           "        </div>\n" +
                           "        <p>If you didn't create an account with us, please ignore this email.</p>\n" +
                           "        <p>Thank you!<br>PaperPal Team</p>\n" +
                           "    </div>\n" +
                           "    <div class=\"footer\">\n" +
                           "        <p>&copy; 2024 PaperPal. All rights reserved.</p>\n" +
                           "    </div>\n" +
                           "</div>\n" +
                           "\n" +
                           "</body>\n" +
                           "</html>\n",
                   true // Enable HTML content
           );

           mimeMessageHelper.setSubject("Registration Confirmation");
           javaMailSender.send(mimeMessage);
           log.info("email sent....");
           storeCode.put(uniqueCode,user);
           return new ResponseEntity<>(HttpStatus.OK);
       }catch (Exception e){
           log.error(e.getMessage());
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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


    @PostMapping("/user/otp")
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

    @PutMapping("/user/setNewPassword")
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

    @GetMapping("/user/activate")
    public ResponseEntity checkActivation(@RequestParam String code,HttpServletResponse response) throws IOException {
        UserDto userDto=storeCode.get(code);
        System.out.println(userDto);
        Boolean isRegistered=false;
        log.info("activation req got..");
        if(userDto!=null) {
                 isRegistered = userService.registerUser(
                        Users.builder()
                                .userName(userDto.getFirstName() + " " + userDto.getLastName())
                                .password(userDto.getPassword())
                                .email(userDto.getEmail())
                                .build()
                );
                 storeCode.remove(code);
            }
                if (isRegistered) {
                    // If registration is successful
                    log.info("User Registered");
                     response.sendRedirect("/");
                    return ResponseEntity.ok("Registration successful");
                } else {
                    // If email/username is already taken, return error
                    log.info("User not registered");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Email or Username already registered.....");
                }
            }
    }

