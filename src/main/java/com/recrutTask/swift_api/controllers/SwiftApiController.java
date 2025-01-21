package com.recrutTask.swift_api.controllers;


import com.opencsv.bean.CsvToBeanBuilder;
import com.recrutTask.swift_api.models.BankEntity;
import com.recrutTask.swift_api.repositories.SwiftCodeRepository;
import com.recrutTask.swift_api.services.DataService;
import com.recrutTask.swift_api.services.SwiftApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileReader;
import java.util.List;

@RestController
@RequestMapping("/v1/swift-codes")
public class SwiftApiController {

    @Autowired
    private SwiftApiService swiftApiService;

    @Autowired
    private DataService dataService;
    @Autowired
    private SwiftCodeRepository swiftCodeRepository;

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
        try {
            System.out.println(swiftCode);



            return ResponseEntity.ok(swiftCodeRepository.findById(swiftCode).get());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }


}
