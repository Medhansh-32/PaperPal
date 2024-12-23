package com.example.PaperPal.service;

import com.example.PaperPal.entity.ExamFile;
import com.example.PaperPal.entity.UserResponse;
import com.example.PaperPal.repository.UserResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public UserResponse saveUserResponse(UserResponse userResponse, MultipartFile file,String fileType) throws IOException {

        ExamFile examFile=examFileService.uploadExamFile(file,fileType);
        userResponse.getExamFile().add(examFile);

        return   userResponseRepository.save(userResponse);
    }

    public List<ExamFile> getExamLinkByUserResponse(UserResponse userResponse){
        List<UserResponse> userResponses = userResponseRepository.findByCourseAndBranchAndSemester(
                userResponse.getCourse(),
                userResponse.getBranch(),
                userResponse.getSemester());

        if(!userResponses.isEmpty() && userResponses!=null){
            return  userResponses.get(0).getExamFile();
        }else{
            return null;
        }

    }
    public UserResponse addFileToUser(UserResponse userResponse, MultipartFile file,String fileType) throws IOException {
        List<UserResponse> userResponse1 = userResponseRepository.findByCourseAndBranchAndSemester(userResponse.getCourse(),
                userResponse.getBranch(),
                userResponse.getSemester());
        if(userResponse1!=null && !userResponse1.isEmpty()){
            ExamFile examFile=examFileService.uploadExamFile(file,fileType);
            userResponse1.get(0).getExamFile().add(examFile);
            return userResponseRepository.save(userResponse1.get(0));
        }
            return null;
    }
    public ResponseEntity<?> deleteByUserResponse(UserResponse userResponse) {

       List<UserResponse> userResponse1=userResponseRepository.deleteByCourseAndBranchAndSemester(userResponse.getCourse(),
                userResponse.getBranch(),
                userResponse.getSemester());

       if(userResponse1!=null && !userResponse1.isEmpty()){
          for(int i=0;i<userResponse1.get(0).getExamFile().size();i++){
              examFileService.deleteExamFile(userResponse1.get(0).getExamFile().get(i).getExamId());
          }
       return new ResponseEntity<>(HttpStatus.OK);
       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
