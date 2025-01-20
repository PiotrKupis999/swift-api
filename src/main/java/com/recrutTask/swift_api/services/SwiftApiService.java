package com.recrutTask.swift_api.services;

import java.util.List;

import com.recrutTask.swift_api.models.SwiftCode;
import com.recrutTask.swift_api.repositories.SwiftCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SwiftApiService {


    @Autowired
    private SwiftCodeRepository repository;




    public SwiftCode saveSwiftCode(SwiftCode swiftCode) {
        return repository.save(swiftCode);
    }

    public void deleteSwiftCode(String swiftCode) {
        repository.deleteById(swiftCode);
    }

}
