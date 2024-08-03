package com.example.PaperPal.service;

import com.example.PaperPal.entity.ExamFile;
import com.example.PaperPal.repository.ExamFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;



@Service
@Slf4j
public class ExamFileService {
    private static final String filePath="C:\\Users\\Medhansh Sharma\\Desktop\\";
    private ExamFile examFile;
    private ExamFileRepository examFileRepository;

    @Autowired
    ExamFileService(ExamFile examFile, ExamFileRepository examFileRepository) {
        this.examFile = examFile;
        this.examFileRepository=examFileRepository;

    }
    public ExamFile uploadExamFile(MultipartFile file) throws IOException {
        examFile.setFileName(file.getOriginalFilename());
        examFile.setFilePath(filePath+file.getOriginalFilename());
        examFile.setContentType(file.getContentType());
        examFile.setFileData(file.getBytes());
        ExamFile oldFile=examFileRepository.save(examFile);
        oldFile.setDownloadLink(createDownloadLink(examFile));
        return examFileRepository.save(oldFile);

    }

    public ResponseEntity<byte[]> downloadExamFile(ObjectId id) throws IOException {
        ExamFile examFile = examFileRepository.findById(id).orElse(null);

        return new ResponseEntity<>(examFile.getFileData(),HttpStatus.OK);

    }
    public String createDownloadLink(ExamFile examFile) {

        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/file/")
                .path(examFile.getExamId().toString())  // Convert the examId to string and append it to the path
                .toUriString();

    }
}
