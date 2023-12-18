package com.example.AuthMongoDB.dto;

import com.example.AuthMongoDB.models.ImageProfile;
import com.example.AuthMongoDB.models.Role;
import com.example.AuthMongoDB.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String email;
    private Integer age;
    private byte[] imgUrl;
    private Set<String> roles = new HashSet<>();

    public static UserDTO toDto(User entity, ImageProfile imageProfile) {
        UserDTO.UserDTOBuilder builder = UserDTO.builder()
                .username(entity.getUsername())
                .email(entity.getEmail())
                .age(entity.getAge());


        if (imageProfile != null) {
            builder.imgUrl(imageProfile.getImage());
        }
        return builder.build();
    }
}
