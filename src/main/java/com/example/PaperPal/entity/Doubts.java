package com.example.PaperPal.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Document(collection = "doubts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Doubts {

    @Id
    private String doubtId;
    private String userName;
    private String doubtTitle;
    private String doubtDescription;
    private Date doubtDate;
    private List<String> replies=new ArrayList<>();
    private boolean doubtStatus;
}
