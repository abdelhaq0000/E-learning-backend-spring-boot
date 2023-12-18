package com.example.AuthMongoDB.repositories;

import com.example.AuthMongoDB.models.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message,String> {

}
