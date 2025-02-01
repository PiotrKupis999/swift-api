package com.recrutTask.swift_api.services;

import com.recrutTask.swift_api.models.BankEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DataServiceTest {
    @Autowired
    private DataService dataService;

    @Test
    public void DataServiceTest_UploadDatabaseFromLocalFile() {
        List<BankEntity> savedBanks = dataService.uploadDatabaseFromLocalFile();
        Assertions.assertNotNull(savedBanks);
        Assertions.assertFalse(savedBanks.isEmpty(), "Database upload returned an empty list!");
    }
}
