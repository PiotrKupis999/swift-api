package com.recrutTask.swift_api.models;

import com.recrutTask.swift_api.services.DataService;

import java.util.List;

public class Headquarter extends BankEntity{

    DataService dataService;

    private List<BankEntity> branches;


    public Headquarter(DataService dataService, BankEntity bankEntity) {
        super( bankEntity.getSwiftCode(), bankEntity.getAddress(), bankEntity.getBankName(), bankEntity.getCountryISO2(), bankEntity.getCountryName(), bankEntity.isHeadquarter());
        this.dataService = dataService;
        this.branches = dataService.findAllBranches(bankEntity.getSwiftCode());
    }


    public List<BankEntity> getBranches() {
        return branches;
    }

    public void setBranches(List<BankEntity> branches) {
        this.branches = branches;
    }
}
