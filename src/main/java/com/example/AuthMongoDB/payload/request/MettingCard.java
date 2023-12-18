package com.example.AuthMongoDB.payload.request;

import com.example.AuthMongoDB.models.GoogleMeeting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MettingCard {
    private GoogleMeeting googleMeeting;
    private byte[]  image;
}
