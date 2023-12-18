package com.example.AuthMongoDB.repositories;

import com.example.AuthMongoDB.models.ImageProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageProfileRepository extends MongoRepository<ImageProfile, String> {
    @Override
    Optional<ImageProfile> findById(String s);

    ImageProfile findImageProfileByUsername(String username);
}
