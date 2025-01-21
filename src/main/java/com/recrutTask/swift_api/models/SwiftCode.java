package com.recrutTask.swift_api.models;


import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
public class SwiftCode {

    @Id
    @CsvBindByName(column = "SWIFT CODE")
    private String swiftCode;

    @CsvBindByName(column = "ADDRESS")
    private String address;

    @CsvBindByName(column = "NAME")
    private String bankName;

    @CsvBindByName(column = "COUNTRY ISO2 CODE")
    private String countryISO2;

    @CsvBindByName(column = "COUNTRY NAME")
    private String countryName;

    private boolean isHeadquarter;

/*
    @OneToMany(mappedBy = "swiftCode", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SwiftCode> branches;

    @ManyToOne
    private SwiftCode parent;

 */
    public SwiftCode(){};


    public String getSwiftCode() {
        return swiftCode;
    }

    public String getAddress() {
        return address;
    }

    public String getCountryISO2() {
        return countryISO2;
    }

    public String getBankName() {
        return bankName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setHeadquarter(boolean headquarter) {
        isHeadquarter = headquarter;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setCountryISO2(String countryISO2) {
        this.countryISO2 = countryISO2;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    @Override
    public String toString() {
        return "{" +
                "address='" + address + '\'' +
                ", bankName='" + bankName + '\'' +
                ", countryISO2='" + countryISO2 + '\'' +
                ", countryName='" + countryName + '\'' +
                ", isHeadquarter=" + isHeadquarter +
                ", swiftCode='" + swiftCode + '\'' +
                '}';
    }
}

