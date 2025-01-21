package com.recrutTask.swift_api.services;

import com.recrutTask.swift_api.models.BankEntity;
import com.recrutTask.swift_api.repositories.SwiftCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DataService {
    @Autowired
    SwiftCodeRepository swiftCodeRepository;

    @Autowired
    SwiftApiService swiftApiService;


    public void readAllBeansToDatabase(List<BankEntity> bankEntities){
        for (BankEntity bankEntity : bankEntities){

            String swiftCode = bankEntity.getSwiftCode();
            bankEntity.setHeadquarter(calculateIsHeadquarter(swiftCode));


            trimWhiteSpacesInAddress(bankEntity);

            //bankEntity.setBranches(swiftApiService.findAllBranches(swiftCode));




            swiftCodeRepository.save(bankEntity);


            System.out.println(bankEntity.toString());
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







}
