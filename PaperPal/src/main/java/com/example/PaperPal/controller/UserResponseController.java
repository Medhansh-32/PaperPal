package com.example.PaperPal.controller;

import com.example.PaperPal.entity.UserResponse;

import com.example.PaperPal.service.UserResponseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserResponseController {

    private final UserResponseService userResponseService;

    public UserResponseController(UserResponseService userResponseService) {
        this.userResponseService = userResponseService;
    }
    @PostMapping
    public ResponseEntity<String> sendData(@RequestParam("userResponse")  String data, @RequestParam("file") MultipartFile file) throws IOException {

        ObjectMapper objectMapper=new ObjectMapper();
       UserResponse userResponse=objectMapper.readValue(data , UserResponse.class);
        String link= userResponseService.saveUserResponse(userResponse,file)
                .getExamFileLink();
        return  new ResponseEntity<>(link, HttpStatus.FOUND);
    }

    @GetMapping
    public ResponseEntity<String> getExamFileLink(@RequestBody  UserResponse userResponse) {
        String link=userResponseService.getExamLinkByUserResponse(userResponse);
        if(link!=null) {
            return new ResponseEntity<String>(HttpStatus.FOUND);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
