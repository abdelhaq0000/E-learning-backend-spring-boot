package com.example.AuthMongoDB.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "GoogleMeeting")
public class GoogleMeeting {
    @Id
    private String id;
    private String title;
    private String link;
    private String teacherName;
}
