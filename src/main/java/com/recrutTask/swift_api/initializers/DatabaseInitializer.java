package com.recrutTask.swift_api.initializers;

import com.recrutTask.swift_api.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer {

    private final DataService dataService;

    @Autowired
    public DatabaseInitializer(DataService dataService) {
        this.dataService = dataService;
        LoadUsers();
    }

    private void LoadUsers() {
        dataService.uploadDatabaseFromLocalFile();
    }
}

