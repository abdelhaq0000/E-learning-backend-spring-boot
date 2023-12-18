package com.example.AuthMongoDB.repositories;

import com.example.AuthMongoDB.models.Cour;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourRepository extends MongoRepository<Cour,String> {
}
