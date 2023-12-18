package com.example.AuthMongoDB.services;

import com.example.AuthMongoDB.models.Cour;
import com.example.AuthMongoDB.repositories.CourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourService {
    @Autowired
    CourRepository courRepository;

    public List<Cour> getAllCours(){
        return courRepository.findAll();
    }

    public String addCour(Cour cour){
        courRepository.save(cour);
        return  "cour has been add";
    }
    public String deletcour(String id){
        courRepository.deleteById(id);
        return "cour has ben deleted";
    }


}
