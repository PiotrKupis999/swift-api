package com.recrutTask.swift_api.models;

import com.opencsv.bean.CsvBindByName;
import com.recrutTask.swift_api.services.DataService;

import java.util.List;

public class Country{

    DataService dataService;
    private String countryISO2;



    private String countryName;

    private List<BankEntity> bankEntityList;

    public Country() {}

    public Country(String countryISO2, DataService dataService){
        this.countryISO2 = countryISO2;
        this.countryName = dataService.countryIso2ToName(countryISO2);
        this.bankEntityList = dataService.findAllBranchesByCountryISO2(countryISO2);
    }


    public String getCountryISO2() {
        return countryISO2;
    }

    public void setCountryISO2(String countryISO2) {
        this.countryISO2 = countryISO2;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public List<BankEntity> getBankEntityList() {
        return bankEntityList;
    }

    public void setBankEntityList(List<BankEntity> bankEntityList) {
        this.bankEntityList = bankEntityList;
    }


}
