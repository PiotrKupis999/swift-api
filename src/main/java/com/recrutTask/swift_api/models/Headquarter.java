package com.recrutTask.swift_api.models;

import com.recrutTask.swift_api.services.SwiftApiService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.spec.PSource;
import java.util.List;

public class Headquarter extends BankEntity{

    @Autowired
    SwiftApiService swiftApiService;

    private List<BankEntity> branches;

    public Headquarter(){};
/*
    public Headquarter(String swiftCode,String address, String bankName ,String countryISO2,String countryName,boolean isHeadquarter){
        super(swiftCode,address,bankName, countryISO2,countryName,isHeadquarter);
        this.branches = swiftApiService.findAllBranches(this);
    }

 */

    public List<BankEntity> getBranches() {
        return branches;
    }

    public void setBranches(List<BankEntity> branches) {
        this.branches = branches;
    }
}
