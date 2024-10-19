package com.example.PaperPal.service;

import com.example.PaperPal.entity.Doubts;

import com.example.PaperPal.repository.DoubtsRepository;
import java.util.Collections;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class DoubtsService {

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


    @PostMapping("/postDoubts")
    public ResponseEntity<String> addDoubts(@RequestBody Doubts doubts) {
        log.info("Doubt request received...");
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        try {
            doubtsRepository.save(Doubts.builder()
                    .userName(userName)
                    .doubtTitle(doubts.getDoubtTitle())
                    .doubtDescription(doubts.getDoubtDescription())
                    .doubtDate(new Date())
                    .doubtStatus(false)
                    .build());
            log.info("Doubts posted....");
            return new ResponseEntity<>("Doubt posted successfully", HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>("Error posting doubt. Please try again.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
