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

<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
=======
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
/*
    @OneToMany(mappedBy = "swiftCode", cascade = CascadeType.ALL, orphanRemoval = true)
>>>>>>> Stashed changes
    private List<SwiftCode> branches;

    @ManyToOne
    private SwiftCode parent;

<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream
    public SwiftCode(String swiftCode, String address, String bankName, String countryName, String countryISO2) {
        this.swiftCode = swiftCode;
        this.address = address;
        this.bankName = bankName;
        this.countryName = countryName;
        this.countryISO2 = countryISO2;
    }

=======
 */
    public SwiftCode(){};


    public String getSwiftCode() {
        return swiftCode;
    }

=======
 */
    public SwiftCode(){};


    public String getSwiftCode() {
        return swiftCode;
    }

>>>>>>> Stashed changes
=======
 */
    public SwiftCode(){};


    public String getSwiftCode() {
        return swiftCode;
    }

>>>>>>> Stashed changes
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setHeadquarter(boolean headquarter) {
        isHeadquarter = headquarter;
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
>>>>>>> Stashed changes
}

