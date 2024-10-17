package com.example.PaperPal.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "doubts")
@Data
@Builder
@AllArgsConstructor
public class Doubts {

    private String userName;
    private String doubtTitle;
    private String doubtDescription;
    private Data doubtDate;
    private boolean doubtStatus;
}
