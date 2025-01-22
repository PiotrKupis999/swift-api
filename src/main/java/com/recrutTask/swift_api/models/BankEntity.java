package com.recrutTask.swift_api.models;


import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class BankEntity {



    @CsvBindByName(column = "ADDRESS")
    private String address;

    @CsvBindByName(column = "NAME")
    private String bankName;

    @CsvBindByName(column = "COUNTRY ISO2 CODE")
    private String countryISO2;

    @CsvBindByName(column = "COUNTRY NAME")
    private String countryName;

    private boolean isHeadquarter;

    @Id
    @CsvBindByName(column = "SWIFT CODE")
    private String swiftCode;

/*
    @OneToMany(mappedBy = "swiftCode", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BankEntity> branches;
*/

/*
    @ManyToOne
    private BankEntity parent;
*/

    public BankEntity(){};


    public BankEntity(String address, String bankName, String countryISO2, String countryName, boolean isHeadquarter, String swiftCode) {
        this.address = address;
        this.bankName = bankName;
        this.countryISO2 = countryISO2;
        this.countryName = countryName;
        this.isHeadquarter = isHeadquarter;
        this.swiftCode = swiftCode;
    }



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

    public boolean getHeadquarter() {
        return isHeadquarter;
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
/*
    public List<BankEntity> getBranches() {
        return branches;
    }

    public void setBranches(List<BankEntity> branches) {
        this.branches = branches;
    }

*/

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

