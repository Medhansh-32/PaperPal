package com.example.PaperPal.service;

import com.example.PaperPal.entity.ExamFile;
import com.example.PaperPal.repository.ExamFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;



@Service
@Slf4j
public class ExamFileService {

    @Value("${filepath}")
    private String filePath;
    private ExamFileRepository examFileRepository;
    private String servletUri;


    ExamFileService(ExamFileRepository examFileRepository,@Value("${servletUri}") String servletUri) {
        this.examFileRepository=examFileRepository;
        this.servletUri=servletUri;
    }


    public ExamFile uploadExamFile(byte[] fileBytes,String fileType) throws IOException {
        ExamFile examFile=new ExamFile();
        examFile.setFileName("materialFile");
        examFile.setFilePath(filePath);
        examFile.setContentType(fileType);
        examFile.setFileData(fileBytes);
        ExamFile oldFile=examFileRepository.save(examFile);
        oldFile.setDownloadLink(createDownloadLink(examFile));
        return examFileRepository.save(oldFile);

    }

    public ResponseEntity<byte[]> downloadExamFile(ObjectId id) throws IOException {
        ExamFile examFile = examFileRepository.findById(id).orElse(null);
        if(examFile!=null) {
            return new ResponseEntity<>(examFile.getFileData(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    public String createDownloadLink(ExamFile examFile) {

        String secureRequest= servletUri.concat("file/").concat(examFile.getExamId().toString());
        log.info(secureRequest);
        return secureRequest;
    }
    public ResponseEntity<?> deleteExamFile(ObjectId id) {
       ExamFile examFile= examFileRepository.deleteExamFileByExamId(id);
       if(examFile!=null) {
           return new ResponseEntity<>(HttpStatus.OK);
       }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
