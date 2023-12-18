package com.example.AuthMongoDB.controllers;

import ch.qos.logback.classic.Logger;
import com.example.AuthMongoDB.services.VisitorService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/visitor")
public class VisitorController {
    private final VisitorService visitorService;
    private Logger log;

    @Autowired
    public VisitorController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }


    @GetMapping("/count-visitor")
    public Long countVisitor(HttpServletRequest request, HttpServletResponse response) {
        String visitorId = getVisitorIdFromCookie(request, response);
        // Uncomment the following line if you want to log the visitorId
        // log.info("Visitor ID: " + visitorId);
        return visitorService.incrementVisitorCount(visitorId);
    }

    private String generateUniqueKey() {
        return "visitor-" + System.currentTimeMillis();
    }


    private String getVisitorIdFromCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("visitorId".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        // If no existing cookie is found, create a new one
        String newVisitorId = generateUniqueKey();
        Cookie newCookie = new Cookie("visitorId", newVisitorId);

        // Set the age of the cookie to 1 year (in seconds)
        int maxAge = (int) TimeUnit.DAYS.toSeconds(365);
        newCookie.setMaxAge(maxAge);

        response.addCookie(newCookie);

        return newCookie.getValue(); // Return the value of the newly created cookie
    }

}
