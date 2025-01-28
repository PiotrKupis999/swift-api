package com.recrutTask.swift_api.controllers;

import com.recrutTask.swift_api.models.BankEntity;
import com.recrutTask.swift_api.models.Country;
import com.recrutTask.swift_api.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/v1/swift-codes")
public class SwiftApiController {
    @Autowired
    private DataService dataService;

    @GetMapping("/import/import-database")
    @ResponseBody
    public List<BankEntity> importCsv() { return dataService.uploadDatabaseFromLocalFile(); }

    @PostMapping("/import/import-csv-from")
    @ResponseBody
    public List<BankEntity> importCsvFromPath(@RequestParam String pathFile) {
        return dataService.uploadDatabaseFromCsvFileLocalPath(pathFile);
    }

    @GetMapping("/{swift-code}")
    @ResponseBody
    public ResponseEntity<BankEntity> getDetails(@PathVariable("swift-code") String swiftCode) {
        BankEntity found = dataService.getBankEntityBySwiftCode(swiftCode.toUpperCase());
        return ResponseEntity.ok(found);
    }

    @GetMapping("/country/{countyISO2code}")
    @ResponseBody
    public ResponseEntity<Country> getBankEntitiesByCountryISO2code(@PathVariable("countyISO2code") String countyISO2code) {
        Country foundCountry = dataService.findAllSwiftCodesWithDetailsByCountryISO2(countyISO2code.toUpperCase());
        return ResponseEntity.ok(foundCountry);
    }

    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<Map<String, String>> addSwiftCode(@RequestBody BankEntity bankEntity) {
        return dataService.addBankEntityToDatabase(bankEntity);
    }

    @DeleteMapping("/{swift-code}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteSwiftCode(@PathVariable("swift-code") String swiftCode,
                                               @RequestParam("bankName") String bankName,
                                               @RequestParam("countryISO2") String countryISO2) {
        Map<String, String> response = dataService.deleteBankEntityFromDatabase(swiftCode.toUpperCase(), bankName, countryISO2.toUpperCase());
        return ResponseEntity.ok(response);
    }
}
