package com.recrutTask.swift_api.controllers;

import com.recrutTask.swift_api.models.BankEntity;
import com.recrutTask.swift_api.models.Country;
import com.recrutTask.swift_api.repositories.SwiftCodeRepository;
import com.recrutTask.swift_api.services.DataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SwiftApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SwiftCodeRepository swiftCodeRepository;

    @Mock
    private DataService dataService;

    @InjectMocks
    private SwiftApiController swiftApiController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        swiftCodeRepository.deleteAll();

        BankEntity bankEntity = BankEntity.builder()
                .swiftCode("15935745XXX")
                .address("Berlin")
                .bankName("Bank A")
                .countryISO2("GE")
                .countryName("GERMANY")
                .isHeadquarter(true)
                .build();
        swiftCodeRepository.save(bankEntity);

        BankEntity anotherBank = BankEntity.builder()
                .swiftCode("87654321XXX")
                .address("Sydney")
                .bankName("Bank F")
                .countryISO2("AU")
                .countryName("AUSTRALIA")
                .isHeadquarter(true)
                .build();
        swiftCodeRepository.save(anotherBank);
    }

    @Test
    public void SwiftApiControllerTest_GetDetails() throws Exception {
        mockMvc.perform(get("/v1/swift-codes/{swift-code}", "15935745XXX"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.swiftCode", is("15935745XXX")))
                .andExpect(jsonPath("$.bankName", is("Bank A")));
    }

    @Test
    public void SwiftApiControllerTest_DeleteSwiftCode() throws Exception {
        mockMvc.perform(delete("/v1/swift-codes/{swift-code}", "87654321XXX")
                        .param("bankName", "Bank F")
                        .param("countryISO2", "AU"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("SWIFT Code deleted successfully")));
    }

    @Test
    public void SwiftApiControllerTest_ImportCsvFromPath() throws Exception {

        mockMvc.perform(post("/v1/swift-codes/import/import-csv-from")
                        .param("pathFile", "P:/Pobrane1/codes.csv"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].swiftCode", is("AAISALTRXXX")))
                .andExpect(jsonPath("$[1].swiftCode", is("ABIEBGS1XXX")));
    }

    @Test
    public void SwiftApiControllerTest_ImportCsv() throws Exception {

        mockMvc.perform(get("/v1/swift-codes/import/import-database"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].swiftCode", is("AAISALTRXXX")))
                .andExpect(jsonPath("$[1].swiftCode", is("ABIEBGS1XXX")));
    }

    @Test
    public void SwiftApiControllerTest_GetBankEntitiesByCountryISO2Code() throws Exception {
        String countryISO2 = "GE";

        BankEntity bankEntity = BankEntity.builder()
                .swiftCode("15935745XXX")
                .address("Berlin")
                .bankName("Bank A")
                .countryISO2("GE")
                .countryName("GERMANY")
                .isHeadquarter(true)
                .build();

        Country country = new Country(countryISO2, "GERMANY", List.of(bankEntity));

        when(dataService.findAllSwiftCodesWithDetailsByCountryISO2(countryISO2))
                .thenReturn(country);

        mockMvc.perform(get("/v1/swift-codes/country/{countyISO2code}", countryISO2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.countryISO2", is("GE")))
                .andExpect(jsonPath("$.countryName", is("GERMANY")))
                .andExpect(jsonPath("$.swiftCodes[0].swiftCode", is("15935745XXX")));
    }
}
