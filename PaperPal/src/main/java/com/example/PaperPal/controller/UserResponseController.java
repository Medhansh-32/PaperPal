package com.example.PaperPal.controller;

import com.example.PaperPal.entity.ExamFile;
import com.example.PaperPal.entity.UserResponse;

import com.example.PaperPal.service.UserResponseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("user")
public class UserResponseController {

    private final UserResponseService userResponseService;

    public UserResponseController(UserResponseService userResponseService) {
        this.userResponseService = userResponseService;
    }
    @PostMapping
    public ResponseEntity<List<ExamFile>> sendData(@RequestParam("userResponse")  String data, @RequestParam("file") MultipartFile file) throws IOException {

        ObjectMapper objectMapper=new ObjectMapper();
       UserResponse userResponse=objectMapper.readValue(data , UserResponse.class);
        List<ExamFile> examFiles= userResponseService.saveUserResponse(userResponse,file)
                .getExamFile();
        return  new ResponseEntity<>(examFiles, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<String>> getExamFileLink(@RequestBody  UserResponse userResponse) {
        List<ExamFile> examFiles=userResponseService.getExamLinkByUserResponse(userResponse);
        if(examFiles!=null) {
            List<String> links=new ArrayList<>();
            for(int i=0;i<examFiles.size();i++){
                links.add(examFiles.get(i).getDownloadLink());
            }
            return new ResponseEntity<>(links,HttpStatus.FOUND);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addfile")
    public ResponseEntity<UserResponse> addFileToUserResponse(@RequestParam("userResponse")  String userResponse,@RequestParam("file") MultipartFile file) throws IOException {
        ObjectMapper objectMapper=new ObjectMapper();
        UserResponse userResponse1=objectMapper.readValue(userResponse,UserResponse.class);
        userResponse1=userResponseService.addFileToUser(userResponse1,file);
        if (userResponse1!=null){
            return new ResponseEntity<>(userResponse1,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserResponse(@RequestBody  UserResponse userResponse) throws JsonProcessingException {
       return new ResponseEntity<>(userResponseService.deleteByUserResponse(userResponse).getStatusCode());

    }
}
