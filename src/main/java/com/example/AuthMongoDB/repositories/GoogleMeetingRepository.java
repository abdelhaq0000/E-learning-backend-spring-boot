package com.example.AuthMongoDB.repositories;

import com.example.AuthMongoDB.models.GoogleMeeting;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GoogleMeetingRepository extends MongoRepository<GoogleMeeting,String> {
}
