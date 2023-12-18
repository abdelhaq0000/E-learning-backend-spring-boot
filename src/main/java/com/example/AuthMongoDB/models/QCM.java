package com.example.AuthMongoDB.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("QSMs")
public class QCM {
    @Id
    private String id;
    private String category;  
    private  String question1;
    private List<String> choices;
    private  String right;
    private String explanation;
}
