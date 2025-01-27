package com.example.PaperPal.service;

import com.example.PaperPal.entity.Users;
import com.example.PaperPal.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.springframework.security.config.http.MatcherType.regex;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserDetailServiceImpl.class);
    private UserRepository userRepository;
    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Users user=userRepository.findByEmail(username);
            if(user!=null){
                return  User.builder()
                        .username(user.getUserName())
                        .password(user.getPassword())
                        .build();
            }else if(userRepository.findByUserName(username)!=null){
                 user=userRepository.findByUserName(username);
                return  User.builder()
                        .username(user.getUserName())
                        .password(user.getPassword())
                        .build();
            }else {
                throw new UsernameNotFoundException("User not found");
            }
    }
}
