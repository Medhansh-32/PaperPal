package com.example.PaperPal.controller;

import com.example.PaperPal.entity.ExamFile;
import com.example.PaperPal.entity.UserResponse;
import com.example.PaperPal.service.UserResponseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("userresponse")
public class UserResponseController {

    private final UserResponseService userResponseService;

    public UserResponseController(UserResponseService userResponseService) {
        this.userResponseService = userResponseService;
    }

    @PostMapping
    public String sendData(
            @RequestParam("course") String course,
            @RequestParam("branch") String branch,
            @RequestParam("semester") String semester,
            @RequestParam("file") MultipartFile file
    )  {
        try {
            UserResponse userResponse = new UserResponse();
            userResponse.setCourse(course);
            userResponse.setBranch(branch);
            userResponse.setSemester(Integer.parseInt(semester));
            if(userResponseService.getExamLinkByUserResponse(userResponse)==null){
                List<ExamFile> examFiles = userResponseService.saveUserResponse(userResponse, file).getExamFile();
                if(examFiles.size() > 0) {
                    return "succesfull";
                }else{
                    return "unsuccesfull";
                }

            }else{
                userResponseService.addFileToUser(userResponse,file);
                return "succesfull";
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
            return "unsuccesfull";
        }

    }

    @GetMapping("/getlinks")
    public String getExamFileLink(
            @RequestParam("course") String course,
            @RequestParam("branch") String branch,
            @RequestParam("semester") String semester,
            Model model
    ) {
        UserResponse userResponse = new UserResponse();
        userResponse.setCourse(course);
        userResponse.setBranch(branch);
        userResponse.setSemester(Integer.parseInt(semester));
        List<ExamFile> examFiles = userResponseService.getExamLinkByUserResponse(userResponse);
        List<String> links = new ArrayList<>();
        if (examFiles != null) {
            for (ExamFile examFile : examFiles) {
                links.add(examFile.getDownloadLink());
            }
            model.addAttribute("links", links);
            return "links-exam";
        }
        return "links-exam";
    }
    @PostMapping("/addfile")
    public String addFileToUserResponse(
            @RequestParam("course") String course,
            @RequestParam("branch") String branch,
            @RequestParam("semester") String semester,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        UserResponse userResponse = new UserResponse();
        userResponse.setCourse(course);
        userResponse.setBranch(branch);
        userResponse.setSemester(Integer.parseInt(semester));
        userResponse = userResponseService.addFileToUser(userResponse, file);
        if (userResponse != null) {
            return "succesfull";
        }else{
            return "unsuccesfull";
        }
    }

    @PostMapping("/delete")
    public String deleteUserResponse(
            @RequestParam("course") String course,
            @RequestParam("branch") String branch,
            @RequestParam("semester") String semester
    ) throws JsonProcessingException {
        try {

            UserResponse userResponse = new UserResponse();
            userResponse.setCourse(course);
            userResponse.setBranch(branch);
            userResponse.setSemester(Integer.parseInt(semester));
            return "succesfull";
        }catch (Exception e){
            return "unsuccesfull";
        }
    }
    @GetMapping("/ai")
    public void getAi(@RequestBody String ai){

    }
}
