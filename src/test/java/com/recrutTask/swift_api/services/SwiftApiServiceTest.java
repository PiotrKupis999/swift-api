package com.recrutTask.swift_api.services;

import com.recrutTask.swift_api.exceptions.BankNotFoundException;
import com.recrutTask.swift_api.models.BankEntity;
import com.recrutTask.swift_api.models.Country;
import com.recrutTask.swift_api.repositories.SwiftCodeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootTest
public class SwiftApiServiceTest {

    @Autowired
    private SwiftApiService swiftApiService;

    @Autowired
    private SwiftCodeRepository swiftCodeRepository;

    @Test
    public void DataServiceTest_GetBankEntityBySwiftCode() {

        BankEntity bankEntity1 = BankEntity.builder()
                .swiftCode("15935745XXX")
                .address("Berlin")
                .bankName("Bank A")
                .countryISO2("GE")
                .countryName("GERMANY")
                .build();

        BankEntity bankEntity2 = BankEntity.builder()
                .swiftCode("12345678XXX")
                .address("Warsaw")
                .bankName("Bank B")
                .countryISO2("PL")
                .countryName("POLAND")
                .build();

        BankEntity bankEntity3 = BankEntity.builder()
                .swiftCode("87654321BBB")
                .address("Cracow")
                .bankName("Bank C")
                .countryISO2("PL")
                .countryName("POLAND")
                .build();

        swiftCodeRepository.save(bankEntity1);
        swiftCodeRepository.save(bankEntity2);
        swiftCodeRepository.save(bankEntity3);

        BankEntity result = swiftApiService.getBankEntityBySwiftCode("87654321BBB");

        Assertions.assertEquals(result, bankEntity3);
    }

    @Test
    public void DataServiceTest_FindAllSwiftCodesWithDetailsByCountryISO2() {

        BankEntity bankEntity1 = BankEntity.builder()
                .swiftCode("15935745XXX")
                .address("Berlin")
                .bankName("Bank A")
                .countryISO2("GE")
                .countryName("GERMANY")
                .build();

        BankEntity bankEntity2 = BankEntity.builder()
                .swiftCode("12345678XXX")
                .address("Zaza")
                .bankName("Bank B")
                .countryISO2("ZE")
                .countryName("ZIMBABWE")
                .build();

        BankEntity bankEntity3 = BankEntity.builder()
                .swiftCode("87654321BBB")
                .address("Waza")
                .bankName("Bank C")
                .countryISO2("ZE")
                .countryName("ZIMBABWE")
                .build();

        swiftCodeRepository.save(bankEntity1);
        swiftCodeRepository.save(bankEntity2);
        swiftCodeRepository.save(bankEntity3);

        List<BankEntity> banksInZimbabwe = List.of(bankEntity2, bankEntity3);

        Country zimbabwe = Country.builder()
                .countryISO2("ZE")
                .countryName("ZIMBABWE")
                .swiftCodes(banksInZimbabwe)
                .build();

        Country resultZimbabwe = swiftApiService.findAllSwiftCodesWithDetailsByCountryISO2("ZE");

        Assertions.assertEquals(resultZimbabwe, zimbabwe);
    }

    @Test
    public void DataServiceTest_AddBankEntityToDatabase() {
        BankEntity bankEntity = BankEntity.builder()
                .swiftCode("78945612XXX")
                .address("New York")
                .bankName("Bank X")
                .countryISO2("US")
                .countryName("UNITED STATES")
                .isHeadquarter(true)
                .build();

        ResponseEntity<Map<String, String>> response = swiftApiService.addBankEntityToDatabase(bankEntity);

        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertTrue(response.getBody().containsKey("message"));
        Assertions.assertEquals("SWIFT Code added successfully", response.getBody().get("message"));
    }

    @Test
    public void DataServiceTest_DeleteBankEntityFromDatabase() {
        BankEntity bankEntity = BankEntity.builder()
                .swiftCode("87654321XXX")
                .address("Los Angeles")
                .bankName("Bank Y")
                .countryISO2("US")
                .countryName("UNITED STATES")
                .isHeadquarter(true)
                .build();

        swiftCodeRepository.save(bankEntity);

        Map<String, String> result = swiftApiService.deleteBankEntityFromDatabase("87654321XXX", "Bank Y", "US");

        Assertions.assertEquals("SWIFT Code deleted successfully", result.get("message"));
        Optional<BankEntity> deletedBank = swiftCodeRepository.findById("87654321XXX");
        Assertions.assertFalse(deletedBank.isPresent());
    }

    @Test
    public void DataServiceTest_GetBankEntityBySwiftCode_NotFound() {
        BankEntity bankEntity = BankEntity.builder()
                .swiftCode("99999999XXX")
                .address("NonExistent")
                .bankName("NonExistentBank")
                .countryISO2("XX")
                .countryName("NOWHERE")
                .isHeadquarter(true)
                .build();

        swiftCodeRepository.save(bankEntity);

        try {
            swiftApiService.getBankEntityBySwiftCode("11111111XXX");
        } catch (BankNotFoundException e) {
            Assertions.assertEquals("No bank found with SWIFT code: 11111111XXX", e.getMessage());
        }
    }
}
