package com.recrutTask.swift_api.controllers;


import com.opencsv.bean.CsvToBeanBuilder;
import com.recrutTask.swift_api.dto.CsvBean;
import com.recrutTask.swift_api.models.SwiftCode;
import com.recrutTask.swift_api.repositories.SwiftCodeRepository;
import com.recrutTask.swift_api.services.ReadCsvDataService;
import com.recrutTask.swift_api.services.SwiftApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/v1/swift-codes")
public class SwiftApiController {

    @Autowired
    private SwiftApiService swiftApiService;

    @Autowired
    private ReadCsvDataService readCsvDataService;
    @Autowired
    private SwiftCodeRepository swiftCodeRepository;

    @GetMapping("/import-csv")
    public ResponseEntity<List<SwiftCode>> importCsv(@RequestParam String pathFile) {
        try {
            List<SwiftCode> csvData = new CsvToBeanBuilder(new FileReader(pathFile))
                    .withType(SwiftCode.class)
                    .build()
                    .parse();

            readCsvDataService.readAllBeansToDatabase(csvData);

            return ResponseEntity.ok(csvData);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }




}
