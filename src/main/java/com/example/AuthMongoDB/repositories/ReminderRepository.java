package com.example.AuthMongoDB.repositories;

import com.example.AuthMongoDB.models.Reminder;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReminderRepository extends MongoRepository<Reminder,String> {
}
