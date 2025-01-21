package com.recrutTask.swift_api.services;


import com.recrutTask.swift_api.repositories.SwiftCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class JSONservice {

    SwiftCodeRepository repository;

    @Autowired
    DataService dataService;

    public ResponseEntity printinggg (String swiftCode){
        if (!dataService.calculateIsHeadquarter(swiftCode)){
            return ResponseEntity.ok(repository.findById(swiftCode).get());
        }
        //Headquarter headquarter = repository.findById(swiftCode).get();

        return ResponseEntity.ok(repository.findById(swiftCode).get());
    }

}
