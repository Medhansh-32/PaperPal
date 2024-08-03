package com.example.PaperPal.service;

import com.example.PaperPal.entity.ExamFile;
import com.example.PaperPal.entity.UserResponse;
import com.example.PaperPal.repository.UserResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UserResponseService {

    private final UserResponseRepository userResponseRepository;
    private final ExamFileService examFileService;

    @Autowired
    public UserResponseService(UserResponseRepository userResponseRepository, ExamFileService examFileService) {
        this.userResponseRepository = userResponseRepository;
        this.examFileService = examFileService;
    }

    public UserResponse saveUserResponse(UserResponse userResponse, MultipartFile file) throws IOException {

        ExamFile examFile=examFileService.uploadExamFile(file);
        userResponse.setExamFileLink(examFile.getDownloadLink());

        return   userResponseRepository.save(userResponse);
    }

    public String getExamLinkByUserResponse(UserResponse userResponse){
        List<UserResponse> userResponses = userResponseRepository.findByCourseAndBranchAndSemester(
                userResponse.getCourse(),
                userResponse.getBranch(),
                userResponse.getSemester());

        if(!userResponses.isEmpty() && userResponses!=null){
            return userResponses.get(0).getExamFileLink();
        }else{
            return null;
        }

    }


}
