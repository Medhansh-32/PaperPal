package com.example.PaperPal.controller;

import com.example.PaperPal.entity.ExamFile;
import com.example.PaperPal.service.ExamFileService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class ExamFileController {

    private final ExamFileService examFileService;

    @Autowired
    ExamFileController(ExamFileService examFileService) {
        this.examFileService = examFileService;
    }

    @PostMapping
    public ResponseEntity<ExamFile> uploadExamFile(@RequestBody MultipartFile file) throws IOException {
        return  new ResponseEntity<>(examFileService.uploadExamFile(file),HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ByteArrayResource> downaloadExamFile(@PathVariable ObjectId id) throws IOException {
        byte[] data = examFileService.downloadExamFile(id).getBody();
        if(data!=null) {
            ByteArrayResource resource = new ByteArrayResource(data);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=examFile");
            headers.setContentType(MediaType.APPLICATION_PDF); // Adjust based on file type
            headers.setContentLength(data.length);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
