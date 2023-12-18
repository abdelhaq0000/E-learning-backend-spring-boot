package com.example.AuthMongoDB.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ImageProfiles")
public class ImageProfile {
    @Id
    private String id;
    @Field(targetType = FieldType.BINARY)
    private byte[]  image;
    private String username;
}
