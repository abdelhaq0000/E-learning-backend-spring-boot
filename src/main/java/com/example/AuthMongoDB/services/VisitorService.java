package com.example.AuthMongoDB.services;

import com.example.AuthMongoDB.models.VisitorCount;
import com.example.AuthMongoDB.repositories.VisitorRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class VisitorService {
    private final VisitorRepository visitorRepository;

    @Autowired
    public VisitorService(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    public Long incrementVisitorCount(String visitorKey) {
        VisitorCount visitorCount = visitorRepository.findById(visitorKey).orElse(new VisitorCount(visitorKey, 0));
        visitorCount.incrementCount();
        visitorRepository.save(visitorCount);
        return visitorCount.getCount();
    }
}