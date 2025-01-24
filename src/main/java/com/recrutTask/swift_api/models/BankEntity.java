package com.recrutTask.swift_api.models;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BankEntity {

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


}

