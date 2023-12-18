package com.example.AuthMongoDB.services;

import com.example.AuthMongoDB.models.QCM;
import com.example.AuthMongoDB.repositories.QCMrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QCMservice {
        @Autowired
        QCMrepository qsMrepository;


        public String addQCM(QCM qcm){
            qsMrepository.save(qcm);
            return "QCM has been added";
        }
        public List<QCM> getAllQCM(){
            return  qsMrepository.findAll();
        }
}
