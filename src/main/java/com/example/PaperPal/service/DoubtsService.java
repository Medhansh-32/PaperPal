package com.example.PaperPal.service;

import com.example.PaperPal.controller.DoubtsController;
import com.example.PaperPal.entity.Doubts;

import com.example.PaperPal.repository.DoubtsRepository;
import java.util.Collections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DoubtsService {

    @Builder
    @AllArgsConstructor
    @Data
    public static class Reply{
        private ObjectId id;
        private String message;

    }
    private DoubtsRepository doubtsRepository;

    @Autowired
    public DoubtsService(DoubtsRepository doubtsRepository) {
        this.doubtsRepository = doubtsRepository;
    }

    public List<Doubts> getAllDoubts(){
        try{
            List<Doubts> doubtsList = doubtsRepository.findAll();
            return doubtsList;
        }
        catch(Exception e){
            log.error(e.getMessage());
            return Collections.emptyList();
        }
    }


    public boolean addDoubts(@RequestBody Doubts doubts) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        try {
            doubtsRepository.save(Doubts.builder()
                    .userName(userName)
                    .doubtTitle(doubts.getDoubtTitle())
                    .doubtDescription(doubts.getDoubtDescription())
                    .doubtDate(new Date())
                    .doubtStatus(false)
                    .build());
            log.info("Doubts posted....");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean addReply(Reply reply) {
        try {
            Optional<Doubts> doubts = doubtsRepository.findById(reply.id);
            if (doubts.isPresent()) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                Object principal = authentication.getPrincipal();
                if(principal instanceof UserDetails) {
                    UserDetails userDetails = (UserDetails) principal;
                    String userName = userDetails.getUsername();
                    doubts.get().getReplies().add(userName + " : " + reply.message);
                    doubtsRepository.save(doubts.get());
                    return true;
                }
                if(principal instanceof OAuth2User) {
                    OAuth2User oAuth2User = (OAuth2User) principal;
                    String userName = oAuth2User.getAttribute("name");
                    doubts.get().getReplies().add(userName + " : " + reply.message);
                    doubtsRepository.save(doubts.get());

                }

                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }
    public List<String> getReply(ObjectId id){
        try {
            return doubtsRepository.findById(id).get().getReplies();
        }catch(Exception e){
            return Collections.emptyList();
        }
    }
}
