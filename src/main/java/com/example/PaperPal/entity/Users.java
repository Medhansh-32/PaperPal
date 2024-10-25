package com.example.PaperPal.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private String userName;

    @Indexed(unique = true)
    private String email;

    private String password;


}
