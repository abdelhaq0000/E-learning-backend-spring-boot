package com.example.AuthMongoDB.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("cours")
public class Cour {
    @Id
    private String id;
    private String title;
    private String category;
    private List<String> paragraphs;
}
