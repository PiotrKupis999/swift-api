package com.recrutTask.swift_api.services;

import com.recrutTask.swift_api.models.BankEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {
    @Autowired
    SwiftApiService swiftApiService;

    @Value("${file.swift.path}")
    private String swiftFilePath;

    public List<BankEntity> uploadDatabaseFromLocalFile() {
        List<BankEntity> listOfBankEntities = swiftApiService.allBankEntitiesFromCsvToList(swiftFilePath);
        return swiftApiService.saveAllBankEntitiesFromListToDatabase(listOfBankEntities);
    }
}
