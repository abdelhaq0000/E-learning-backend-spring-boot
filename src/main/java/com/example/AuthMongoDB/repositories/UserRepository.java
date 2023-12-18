package com.example.AuthMongoDB.repositories;

import java.util.Optional;

import com.example.AuthMongoDB.models.Role;
import com.example.AuthMongoDB.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

    long countUserByRoles(Role role);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}