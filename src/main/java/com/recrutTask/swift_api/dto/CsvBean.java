package com.recrutTask.swift_api.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.FileReader;
import java.nio.file.Path;
import java.util.List;

@Getter
@Setter
public class CsvBean {

    @CsvBindByName(column = "ADDRESS")
    private String address;

    @CsvBindByName(column = "NAME")
    private String bankName;

    @CsvBindByName(column = "COUNTRY ISO2 CODE")
    private String countryISO2;

    @CsvBindByName(column = "COUNTRY NAME")
    private String countryName;

    @CsvBindByName(column = "SWIFT CODE")
    private String swiftCode;



}
