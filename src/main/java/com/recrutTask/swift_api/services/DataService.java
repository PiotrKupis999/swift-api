package com.recrutTask.swift_api.services;

import com.opencsv.bean.CsvToBeanBuilder;
import com.recrutTask.swift_api.models.BankEntity;
import com.recrutTask.swift_api.repositories.SwiftCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataService {
    @Autowired
    SwiftCodeRepository repository;


    public List<BankEntity> uploadDatabaseFromLocalFile(){
        List<BankEntity> listOfBankEntities = allBankEntitiesFromCsvToList
                ("src/main/resources/data/Interns_2025_SWIFT_CODES.csv");
        return saveAllBankEntitiesFromListToDatabase(listOfBankEntities);
    }

    public List<BankEntity> uploadDatabaseFromCsvFileLocalPath(String filePath){
        return allBankEntitiesFromCsvToList(filePath);
    }

    private List<BankEntity> allBankEntitiesFromCsvToList(String csvFilePath) {
        try {
            return new CsvToBeanBuilder<BankEntity>(new FileReader(csvFilePath))
                    .withType(BankEntity.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("CSV file not found at: " + csvFilePath, e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error processing CSV file.", e);
        }
    }


    public List<BankEntity> saveAllBankEntitiesFromListToDatabase(List<BankEntity> bankEntities){

        return bankEntities.stream()
                .peek(bankEntity -> {
                    String swiftCode = bankEntity.getSwiftCode();
                    trimWhiteSpaces(bankEntity);
                    bankEntity.setHeadquarter(calculateIsHeadquarter(swiftCode));
                })
                .map(repository::save)
                .collect(Collectors.toList());
    }

    private void trimWhiteSpaces(BankEntity bankEntity){

        bankEntity.setAddress(bankEntity.getAddress().trim());
    }

    public boolean calculateIsHeadquarter(String swiftCode) {

        if (swiftCode != null && swiftCode.length() >= 3) {
            String suffix = swiftCode.substring(swiftCode.length() - 3);
            return "XXX".equals(suffix);

        }
        return false;
    }

    public List<BankEntity> findAllBranches (String swiftCode){

        String mainSwiftCodeOfHQ = swiftCode.substring(0, 8);

        return repository
                .findAll()
                .stream()
                .filter(bankEntity -> bankEntity.getSwiftCode().substring(0, 8).contains(mainSwiftCodeOfHQ)
                        && !calculateIsHeadquarter(bankEntity.getSwiftCode()))
                .collect(Collectors.toList());

    }

    public List<BankEntity> findAllBranchesByCountryISO2(String countryIso2){

        return repository
                .findAll()
                .stream()
                .filter(bankEntity -> bankEntity.getCountryISO2().contains(countryIso2))
                .collect(Collectors.toList());
    }

    public String countryIso2ToName (String countryIso2){

        for (BankEntity bank : repository.findAll()){
            if(bank.getCountryISO2().contains(countryIso2)){
                return bank.getCountryName();
            }
        }
        return null;
    }







}
