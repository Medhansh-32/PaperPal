package com.example.PaperPal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection ="userResponse")
@Data
@NoArgsConstructor  // Add this annotation for a no-argument constructor
@AllArgsConstructor
public class UserResponse {
    @Id
    private ObjectId id;
    private String course;
    private String branch;
    private int semester;
    private String examFileLink;
}
