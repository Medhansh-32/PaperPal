package com.example.PaperPal.controller;

import com.example.PaperPal.entity.ExamFile;
import com.example.PaperPal.entity.UserResponse;
import com.example.PaperPal.service.Pdfanalyzer;
import com.example.PaperPal.service.UserResponseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.http.ResponseEntity;

@Controller
@RequestMapping("/userresponse")
public class UserResponseController {

    private final UserResponseService userResponseService;

    private Pdfanalyzer pdfanalyzer;

    public UserResponseController(UserResponseService userResponseService, Pdfanalyzer pdfanalyzer) {
        this.userResponseService = userResponseService;
        this.pdfanalyzer = pdfanalyzer;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity sendData(
            @RequestParam("course") String course,
            @RequestParam("branch") String branch,
            @RequestParam("semester") String semester,
            @RequestParam("fileType") String fileType,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        UserResponse userResponse = new UserResponse();
        userResponse.setCourse(course);
        userResponse.setBranch(branch);
        userResponse.setSemester(Integer.parseInt(semester));
        String name= SecurityContextHolder.getContext().getAuthentication().getName();
        String fileName=file.getOriginalFilename();
        pdfanalyzer.analyzePdf(userResponse,fileType,name,file.getBytes(),fileName);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getlinks")
    public String getExamFileLink(
            @RequestParam("course") String course,
            @RequestParam("branch") String branch,
            @RequestParam("semester")int semester,
            Model model
    ) {
        UserResponse userResponse = new UserResponse();
        userResponse.setCourse(course);
        userResponse.setBranch(branch);
        userResponse.setSemester(semester);
        List<ExamFile> examFiles = userResponseService.getExamLinkByUserResponse(userResponse);
        HashMap<String,String> links = new HashMap<>();
        if (examFiles != null) {
            for (ExamFile examFile : examFiles) {
                links.put(examFile.getDownloadLink(),examFile.getContentType());
            }
            model.addAttribute("links", links);
            return "links-exam";
        }
        return "links-exam";
    }
    @PostMapping("/addfile")
    public ResponseEntity addFileToUserResponse(
            @RequestParam("course") String course,
            @RequestParam("branch") String branch,
            @RequestParam("semester") String semester,
            @RequestParam("fileType") String fileType,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        UserResponse userResponse = new UserResponse();
        userResponse.setCourse(course);
        userResponse.setBranch(branch);
        userResponse.setSemester(Integer.parseInt(semester));
        String fileName=file.getOriginalFilename();
        userResponse = userResponseService.addFileToUser(userResponse, file.getBytes(),fileType,fileName);
        if (userResponse != null) {
            return new ResponseEntity<>(HttpStatus.OK) ;
        }else{
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity deleteUserResponse(
            @RequestParam("course") String course,
            @RequestParam("branch") String branch,
            @RequestParam("semester") String semester
    ) throws JsonProcessingException {
        try {
            UserResponse userResponse = new UserResponse();
            userResponse.setCourse(course);
            userResponse.setBranch(branch);
            userResponse.setSemester(Integer.parseInt(semester));
            userResponseService.deleteByUserResponse(userResponse);
            return new ResponseEntity<>(HttpStatus.OK) ;
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
