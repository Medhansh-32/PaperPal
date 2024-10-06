package com.example.PaperPal.service;

import com.example.PaperPal.entity.Users;
import com.example.PaperPal.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private UserRepository userRepository;
    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Users user=userRepository.findByUserName(username);
            if(user==null){
                throw new UsernameNotFoundException("User not found");
            }
      return  User.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .build();
    }
}
