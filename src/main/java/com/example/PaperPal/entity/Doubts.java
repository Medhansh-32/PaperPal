package com.example.PaperPal.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document(collection = "doubts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Doubts {

    private String userName;
    private String doubtTitle;
    private String doubtDescription;
    private Date doubtDate;
    private boolean doubtStatus;
}
