package com.example.AuthMongoDB.repositories;

import com.example.AuthMongoDB.models.QCM;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QCMrepository extends MongoRepository<QCM,String> {
}
