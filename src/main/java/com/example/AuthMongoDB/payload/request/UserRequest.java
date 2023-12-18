package com.example.AuthMongoDB.payload.request;

import com.example.AuthMongoDB.models.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserRequest {

    private String username;
    private String email;
    private String password;
    private int age;
    private Set<Role> roles = new HashSet<>();
}
