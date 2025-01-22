package com.recrutTask.swift_api.services;

import com.opencsv.bean.CsvToBeanBuilder;
import com.recrutTask.swift_api.models.BankEntity;
import com.recrutTask.swift_api.repositories.SwiftCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataService {
    @Autowired
    SwiftCodeRepository repository;


    public void readAllBeansToDatabase(List<BankEntity> bankEntities){
        for (BankEntity bankEntity : bankEntities){

            String swiftCode = bankEntity.getSwiftCode();

            bankEntity.setHeadquarter(calculateIsHeadquarter(swiftCode));

            trimWhiteSpacesInAddress(bankEntity);

            repository.save(bankEntity);
        }
    }

    public void trimWhiteSpacesInAddress(BankEntity bankEntity){

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
                .filter(bankEntity -> bankEntity.getSwiftCode().substring(0, 8).contains(mainSwiftCodeOfHQ) && !calculateIsHeadquarter(bankEntity.getSwiftCode()))
                .collect(Collectors.toList());

    }

    public List<BankEntity> findAllBranchesByCountryISO2(String countryIso2){

        return repository.findAll()
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
