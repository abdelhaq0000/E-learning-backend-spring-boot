package com.example.AuthMongoDB.repositories;

import com.example.AuthMongoDB.models.VisitorCount;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VisitorRepository extends MongoRepository<VisitorCount, String> {

}