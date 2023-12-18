package com.example.AuthMongoDB.controllers;

import com.example.AuthMongoDB.models.ERole;
import com.example.AuthMongoDB.models.Role;
import com.example.AuthMongoDB.models.User;
import com.example.AuthMongoDB.payload.request.UserRequest;
import com.example.AuthMongoDB.payload.response.UserResponse;
import com.example.AuthMongoDB.repositories.RoleRepository;
import com.example.AuthMongoDB.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;




 @GetMapping("/All")
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAll() {
        var users=userRepository.findAll();

        return ResponseEntity.ok(users);
    }


    @GetMapping("/hi")
    public String hi(){
    Role r1 = new Role();
    Role r2 = new Role();
    Role r3 = new Role();
    r1.setName(ERole.ROLE_ADMIN);
    r2.setName(ERole.ROLE_STUDENT);
    r3.setName(ERole.ROLE_PROF);
    roleRepository.save(r1);
    roleRepository.save(r2);
    roleRepository.save(r3);
     return "hi";
    }

}
