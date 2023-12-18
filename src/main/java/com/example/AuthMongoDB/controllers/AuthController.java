package com.example.AuthMongoDB.controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import com.example.AuthMongoDB.models.ERole;
import com.example.AuthMongoDB.models.Role;
import com.example.AuthMongoDB.models.User;
import com.example.AuthMongoDB.payload.request.*;
import com.example.AuthMongoDB.payload.response.MessageResponse;
import com.example.AuthMongoDB.payload.response.UserInfoResponse;
import com.example.AuthMongoDB.repositories.*;
import com.example.AuthMongoDB.security.jwt.*;
import com.example.AuthMongoDB.services.*;
import jakarta.validation.Valid;

import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        System.out.println("user is login" + loginRequest.getUsername());
        getUserInfo(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(new UserInfoResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @GetMapping("/all")
    public String allAccess(@Valid @RequestBody SignupRequest signUpRequest) {
        return "Public Content.";
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()), signUpRequest.getAge());

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        System.out.println(signUpRequest.getRoles());
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_STUDENT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
            System.out.println("rolle add is " + userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        System.out.println("rolle add is " + adminRole.getName());
                        break;
                    case "prof":
                        Role modRole = roleRepository.findByName(ERole.ROLE_PROF)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        System.out.println("rolle add is " + modRole.getName());
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_STUDENT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                        System.out.println("rolle add is " + userRole.getName());
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        System.out.println("user add " + signUpRequest.getUsername());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signUpRequest.getUsername(), signUpRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> rolesDiff = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        registerUser(signUpRequest.getUsername(), signUpRequest.getPassword(), signUpRequest.getEmail());
        return ResponseEntity.ok(new UserInfoResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                rolesDiff));
    }

    private static final String CHAT_ENGINE_PRIVATE_KEY = "812d4609-1828-46fc-a328-df62b6183279"; // Replace with your actual private key

    public static void registerUser(String username, String password, String email) {
        HttpURLConnection con = null;

        try {
            URL url = new URL("https://api.chatengine.io/users");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");


            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Private-Key", CHAT_ENGINE_PRIVATE_KEY);


            con.setDoOutput(true);


            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("username", username);
            requestBody.put("secret", "1234");
            requestBody.put("email", email);

            String jsonInputString = mapToJson(requestBody);

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            System.out.println("Response Code: " + con.getResponseCode());

        } catch (Exception e) {
            System.out.println("Error during API call: " + e.getMessage());
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

    private static String mapToJson(Map<String, Object> map) {
        StringBuilder json = new StringBuilder("{");
        map.forEach((key, value) -> json.append("\"").append(key).append("\":\"").append(value).append("\","));
        json.deleteCharAt(json.length() - 1); // Remove the trailing comma
        json.append("}");
        return json.toString();
    }

    private static final String CHAT_ENGINE_PROJECT_ID = "e325e1b3-7843-4cfc-8711-91b41e096555"; // Replace with your actual project ID

    public static void getUserInfo(String username, String secret) {
        HttpURLConnection con = null;

        try {
            URL url = new URL("https://api.chatengine.io/users/me");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");


            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Project-ID", CHAT_ENGINE_PROJECT_ID);
            con.setRequestProperty("User-Name", username);
            con.setRequestProperty("User-Secret", secret);


            StringBuilder responseStr = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    responseStr.append(responseLine.trim());
                }
            }


            Map<String, Object> response = new Gson().fromJson(
                    responseStr.toString(), new TypeToken<HashMap<String, Object>>() {
                    }.getType());


            System.out.println("Response: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

    @GetMapping("/ceartAdmin")
    public String addAdmin() {
        // Create new user's account
        User user = new User("admin",
                "admin@elearning.ma",
                encoder.encode("admin1234"), 40);
        Set<Role> roles = new HashSet<>();
        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(adminRole);

        user.setRoles(roles);
        userRepository.save(user);
        return "admin created";

    }
}