package com.recrutTask.swift_api.services;

import com.recrutTask.swift_api.models.BankEntity;
import com.recrutTask.swift_api.models.Headquarter;
import com.recrutTask.swift_api.repositories.SwiftCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SwiftApiService {


    @Autowired
    private SwiftCodeRepository repository;




    public List<BankEntity> findAllBranches (String swiftCode){

        List<BankEntity> branches = null;

        String mainSwiftCodeOfHQ = swiftCode.substring(0, 8);

        for (BankEntity bank : repository.findAll()){

            if(bank.getSwiftCode().substring(0, 8).contains(mainSwiftCodeOfHQ)){
                branches.add(bank);
            }
        }

        return branches;

    }

    //public BankEntity showAll




    public void trimWhiteSpacesInAddress(BankEntity bankEntity){

        bankEntity.setAddress(bankEntity.getAddress().trim());

    }

/*
    public BankEntity saveSwiftCode(BankEntity swiftCode) {
        return repository.save(swiftCode);
    }

    public void deleteSwiftCode(String swiftCode) {
        repository.deleteById(swiftCode);
    }
*/


}
