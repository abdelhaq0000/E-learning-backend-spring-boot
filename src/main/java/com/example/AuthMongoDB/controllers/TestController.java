package com.example.AuthMongoDB.controllers;

import com.example.AuthMongoDB.dto.UserDTO;
import com.example.AuthMongoDB.models.*;
import com.example.AuthMongoDB.payload.response.ImageResponse;
import com.example.AuthMongoDB.repositories.*;
import com.example.AuthMongoDB.services.CourService;
import com.example.AuthMongoDB.services.QCMservice;
import com.example.AuthMongoDB.services.UserDetailsServiceImpl;
import com.example.AuthMongoDB.services.VisitorService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    GoogleMeetingRepository googleMeetingRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    ImageProfileRepository imageProfileRepository;
    @Autowired
    ReminderRepository reminderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    CourService courService;
    @Autowired
    VisitorService visitorService;
    @Autowired
    QCMservice qcMservice;
    @Autowired
    VisitorRepository visitorRepository;



    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('STUDENT') or hasRole('PROF') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('PROF')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @GetMapping("/student")
    @PreAuthorize("hasRole('STUDENT')")
    public String studentacss(){
        return "I'am student";
    }

    @PostMapping("/upload/{username}")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,@PathVariable("username") String username) {
        try {
            // Check if the file is not empty
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }

            // Create a new ImageProfile
            ImageProfile imageProfile = new ImageProfile();

            // Set the image data as a byte array
            imageProfile.setImage(file.getBytes());
            imageProfile.setUsername(username);
            // Save the ImageProfile to the repository
            imageProfileRepository.save(imageProfile);
            return ResponseEntity.ok("Image uploaded successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error uploading image.");
        }
    }
    @GetMapping("/photoProfil/{username}")
    public ImageResponse getImage(@PathVariable String username) {
        // Fetch the image profile from the database
        ImageProfile imageProfile = imageProfileRepository.findImageProfileByUsername(username);
       // .else

        if (imageProfile != null && imageProfile.getImage() != null) {
            // Set the appropriate headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Adjust the media type based on your image type

            // Return the image bytes along with the headers
            return new ImageResponse(username, imageProfile.getImage());
        } else {
            return null;
        }
    }

    @GetMapping("/Allusers")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserDTO>> getAll() {
        List<UserDTO> users=userDetailsService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/countuserbyrole/{rolename}")
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Long> countuser(@PathVariable("rolename") String rolename) {
        Role roles = roleRepository.findByName(rolename).get();
        return ResponseEntity.ok(userRepository.countUserByRoles(roles));
    }
    @PostMapping("/addCour")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addCoour(@RequestBody Cour cour){
        return courService.addCour(cour);
    }
    @GetMapping("/getAllCours")
    public List<Cour> getAllcours(){
        return courService.getAllCours();
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
    @GetMapping("/getQCMs")
    public List<QCM> getAllQCM(){
        return qcMservice.getAllQCM();
    }
    @PostMapping("/addQCM")
    public String addQCM(@RequestBody QCM qcm){
        qcMservice.addQCM(qcm);
        return "QCM has been added";
    }
    @GetMapping("/nombreOFusers")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Integer countUsrs(){
        return (int) visitorRepository.count();
    }
    @PostMapping("/sendMessage")
    public String sendMessage(@RequestBody Message message){
        messageRepository.save(message);
        return "your message has been send ";
    }

    @GetMapping("/getMessages")
    public List<Message> getMessages(){
       List<Message> m = messageRepository.findAll();
        return m;
    }
    @PostMapping ("/addGoogleLink")
    public String addGoogleLink(@RequestBody GoogleMeeting googleMeeting){
        googleMeetingRepository.save(googleMeeting);
        return "you add the meeting wait student come";
    }
    @GetMapping("/gettMeetings")
    public List<GoogleMeeting> getMeeting(){
        return googleMeetingRepository.findAll();
    }

}
