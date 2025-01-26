package com.recrutTask.swift_api.controllers;

import com.recrutTask.swift_api.models.BankEntity;
import com.recrutTask.swift_api.models.Country;
import com.recrutTask.swift_api.services.DataService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/v1/swift-codes")
public class SwiftApiController {
    @Autowired
    private DataService dataService;

    @PostMapping("/import-database")
    @ResponseBody
    public List<BankEntity> importCsv() { return dataService.uploadDatabaseFromLocalFile(); }

    @PostMapping("/import-csv-from")
    @ResponseBody
    public List<BankEntity> importCsvFromPath(@RequestParam String pathFile) {
        return dataService.uploadDatabaseFromCsvFileLocalPath(pathFile);
    }

    @GetMapping("/{swift-code}")
    @ResponseBody
    public ResponseEntity<BankEntity> getDetails1(@PathVariable("swift-code") String swiftCode) {
        try {
            BankEntity found = dataService.getBankEntityBySwiftCode(swiftCode.toUpperCase());
            return ResponseEntity.ok(found);
        }catch (NoSuchElementException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No bank found with this SWIFT code", exception);
        }
    }

    @SneakyThrows
    @GetMapping("/country/{countyISO2code}")
    @ResponseBody
    public ResponseEntity<Country> getBankEntitiesByCountryISO2code(@PathVariable("countyISO2code") String countyISO2code) {
        Country foundCountry = dataService.findAllSwiftCodesWithDetailsByCountryISO2(countyISO2code.toUpperCase());
        return ResponseEntity.ok(foundCountry);
    }

    @PostMapping("/")
    @ResponseBody
    public Map<String, String> addSwiftCode(@RequestBody BankEntity bankEntity) {
        return dataService.addBankEntityToDatabase(bankEntity);
    }

    @DeleteMapping("/{swift-code}")
    @ResponseBody
    public Map<String, String> deleteSwiftCode(@PathVariable("swift-code") String swiftCode,
                                               @RequestParam("bankName") String bankName,
                                               @RequestParam("countryISO2") String countryISO2) {
        try {
            return dataService.deleteBankEntityFromDatabase(swiftCode,bankName,countryISO2);
        }catch (Exception exception){
            return Map.of("message", exception.getMessage());
        }
    }
}
