package com.recrutTask.swift_api.repository;

import com.recrutTask.swift_api.models.BankEntity;
import com.recrutTask.swift_api.repositories.SwiftCodeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SwiftCodeRepositoryTest {

    @Autowired
    private SwiftCodeRepository swiftCodeRepository;

    @Test
    public void SwiftCodeRepository_SaveSwiftCode() {

        BankEntity bankEntity1 = BankEntity.builder()
                .address("Cracow")
                .bankName("Royal Bank")
                .countryISO2("pl")
                .countryName("poland")
                //.isHeadquarter(false)
                .swiftCode("12345678ABC")
                .branches(null)
                .build();

        BankEntity savedSwiftCode = swiftCodeRepository.save(bankEntity1);


        Assertions.assertNotNull(savedSwiftCode);
        Assertions.assertNotNull(savedSwiftCode.getSwiftCode());
        Assertions.assertFalse(savedSwiftCode.isHeadquarter());

    }
}
