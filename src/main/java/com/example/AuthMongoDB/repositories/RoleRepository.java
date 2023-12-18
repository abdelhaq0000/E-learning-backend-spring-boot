package com.example.AuthMongoDB.repositories;

import java.util.Optional;

import com.example.AuthMongoDB.models.ERole;
import com.example.AuthMongoDB.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
    Optional<Role> findByName(String name);
}