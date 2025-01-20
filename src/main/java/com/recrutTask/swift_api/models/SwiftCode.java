package com.recrutTask.swift_api.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class SwiftCode {

    @Id
    private String swiftCode;

    private String address;
    private String bankName;
    private String countryISO2;
    private String countryName;
    private boolean isHeadquarter;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SwiftCode> branches;

    @ManyToOne
    private SwiftCode parent;

    public SwiftCode(String swiftCode, String address, String bankName, String countryName, String countryISO2) {
        this.swiftCode = swiftCode;
        this.address = address;
        this.bankName = bankName;
        this.countryName = countryName;
        this.countryISO2 = countryISO2;
    }

}

