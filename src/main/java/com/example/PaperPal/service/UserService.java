package com.example.PaperPal.service;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.example.PaperPal.entity.Users;
import com.example.PaperPal.repository.UserRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
    private JavaMailSender mailSender;

    public UserService(UserRepository userRepository,JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    public boolean registerUser(Users user){
        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);

            mimeMessageHelper.setFrom("PaperPal");
            mimeMessageHelper.setTo(user.getEmail());
            mimeMessageHelper.setSubject("Successfully Registered to Paper Pal");
            String emailText="<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>Welcome to PaperPal!</title>\n" +
                    "    <style>\n" +
                    "        body {\n" +
                    "            font-family: Arial, sans-serif;\n" +" color:white;\n" +
                    "            margin: 0;\n" +
                    "            padding: 0;\n" +
                    "            background-color: #f4f4f4;\n" +
                    "        }\n" +
                    "        .container {\n" +
                    "            width: 100%;\n" +
                    "            max-width: 600px;\n" +
                    "            margin: 20px auto;\n" +
                    "            background-color: lightblue;\n" +
                    "            border-radius: 8px;\n" +
                    "            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);\n" +
                    "            overflow: hidden;\n" +
                    "        }\n" +
                    "        .header {\n" +
                    "            background-color: #4CAF50;\n" +
                    "            color: #ffffff;\n" +
                    "            padding: 20px;\n" +
                    "            text-align: center;\n" +
                    "        }\n" +
                    "        .content {\n" +
                    "            padding: 20px;\n" +
                    "            line-height: 1.6;\n" +
                    "            text-align: center; /* Center align text */\n" +
                    "        }\n" +
                    "        .footer {\n" +
                    "            text-align: center;\n" +
                    "            padding: 20px;\n" +
                    "            font-size: 12px;\n" +
                    "            color: #777;\n" +
                    "        }\n" +".p{\ncolor:black;\n}"+"h2{\ncolor:black;\n}"+
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div class=\"container\">\n" +
                    "        <div class=\"header\">\n" +
                    "            <h1>Welcome to PaperPal!</h1>\n" +
                    "        </div>\n" +
                    "        <div class=\"content\">\n" +
                    "            <h2>Hello, "+user.getUserName()+"</h2>\n" +
                    "            <p>We’re excited to have you with us. Welcome aboard!</p>\n" +
                    "        </div>\n" +
                    "        <div class=\"footer\">\n" +
                    "            <p>Regards,</p>\n" +
                    "            <p>The PaperPal Team</p>\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>\n";
            mimeMessageHelper.setText(emailText,true);

            sendMail(mimeMessage);
            log.info("New Thread Started and response sent.....");
            return true;
        }catch (Exception e){
        log.error(e.getMessage());
            return false;
        }

    }

    @Async
    public void sendMail(MimeMessage mimeMessage){
        try {
            log.info(Thread.currentThread().getName().toString()," Running Right Now ");
            mailSender.send(mimeMessage);
        }catch (Exception e){
            log.error(e.getMessage());
        }

    }


}
