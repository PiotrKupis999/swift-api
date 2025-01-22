package com.recrutTask.swift_api.controllers;


import com.opencsv.bean.CsvToBeanBuilder;
import com.recrutTask.swift_api.models.BankEntity;
import com.recrutTask.swift_api.models.Country;
import com.recrutTask.swift_api.models.Headquarter;
import com.recrutTask.swift_api.repositories.SwiftCodeRepository;
import com.recrutTask.swift_api.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/v1/swift-codes")
public class SwiftApiController {



    @Autowired
    private DataService dataService;
    @Autowired
    private SwiftCodeRepository repository;

    @GetMapping("/import-csv")
    public ResponseEntity<List<BankEntity>> importCsv(@RequestParam String pathFile) {
        try {
            List<BankEntity> csvData = new CsvToBeanBuilder(new FileReader(pathFile))
                    .withType(BankEntity.class)
                    .build()
                    .parse();

            dataService.readAllBeansToDatabase(csvData);

            return ResponseEntity.ok(csvData);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{swift-code}")
    public ResponseEntity<BankEntity> getDetails(@PathVariable("swift-code") String swiftCode) {
        swiftCode = swiftCode.toUpperCase();
        try {
            BankEntity be1 = repository.findById(swiftCode).orElseThrow(() -> new RuntimeException("BankEntity not found"));

            if (dataService.calculateIsHeadquarter(swiftCode)) {
                Headquarter h1 = new Headquarter(dataService, be1);
                return ResponseEntity.ok(h1);
            }

            return ResponseEntity.ok(be1);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }



    @GetMapping("/country/{countyISO2code}")
    public ResponseEntity<Country> getBankEntitiesByCountryISO2code(@PathVariable("countyISO2code") String countyISO2code) {
        countyISO2code = countyISO2code.toUpperCase();
        try {
            Country country = new Country(countyISO2code, dataService);

            return ResponseEntity.ok(country);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Object> addSwiftCode(@RequestBody BankEntity bankEntity) {
        try {
            repository.save(bankEntity);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body((Map.of("message", "SWIFT code added successfully")));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

}
