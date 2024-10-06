package com.example.PaperPal.service;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.example.PaperPal.entity.Users;
import com.example.PaperPal.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean registerUser(Users user){
        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return true;
        }catch (Exception e){
        log.error(e.getMessage());
            return false;
        }

    }


}
