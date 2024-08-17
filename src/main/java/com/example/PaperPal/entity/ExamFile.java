package com.example.PaperPal.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;


@Document(collection = "examFile")
@Data
@Component
@NoArgsConstructor
public class ExamFile {
    @Id
    private ObjectId examId;
    private String fileName;
    private String filePath;
    private String downloadLink;
    private String contentType;
    private byte[] fileData;
}
