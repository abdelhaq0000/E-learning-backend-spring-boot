package com.example.AuthMongoDB.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime; // Import LocalDateTime

@Document(collection = "visitors")
public class VisitorCount {
    @Id
    private String id;
    private long count;
    private LocalDateTime lastVisit;

    public VisitorCount(String id, long count) {
        this.id = id;
        this.count = count;
        this.lastVisit = LocalDateTime.now();
    }

    public long getCount() {
        return count;
    }

    public LocalDateTime getLastVisit() {
        return lastVisit;
    }

    public void incrementCount() {
        this.count++;
        this.lastVisit = LocalDateTime.now();
    }
}
