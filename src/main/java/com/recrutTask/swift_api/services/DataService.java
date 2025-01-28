package com.recrutTask.swift_api.services;

import com.opencsv.bean.CsvToBeanBuilder;
import com.recrutTask.swift_api.exceptions.*;
import com.recrutTask.swift_api.models.BankEntity;
import com.recrutTask.swift_api.models.Country;
import com.recrutTask.swift_api.repositories.SwiftCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DataService {
    @Autowired
    SwiftCodeRepository repository;

    public List<BankEntity> uploadDatabaseFromLocalFile(){
        List<BankEntity> listOfBankEntities = allBankEntitiesFromCsvToList
                ("src/main/resources/data/Interns_2025_SWIFT_CODES.csv");
        return saveAllBankEntitiesFromListToDatabase(listOfBankEntities);
    }

    public List<BankEntity> uploadDatabaseFromCsvFileLocalPath(String filePath){
        List<BankEntity> listOfBankEntities = allBankEntitiesFromCsvToList(filePath);
        return saveAllBankEntitiesFromListToDatabase(listOfBankEntities);
    }

    public BankEntity getBankEntityBySwiftCode(String swiftCode) {
        BankEntity foundBank = repository.findById(swiftCode)
                .orElseThrow(() -> new BankNotFoundException("No bank found with SWIFT code: " + swiftCode));
        boolean isHeadquarterValue = calculateIsHeadquarter(foundBank.getSwiftCode());
        List<BankEntity> branches;
        if (isHeadquarterValue) {
            branches = findAllBranchesByHeadquarterSwiftCode(swiftCode);
        } else branches = null;

        return BankEntity.builder()
                .address(foundBank.getAddress())
                .bankName(foundBank.getBankName())
                .countryISO2(foundBank.getCountryISO2())
                .countryName(foundBank.getCountryName())
                .isHeadquarter(isHeadquarterValue)
                .swiftCode(foundBank.getSwiftCode())
                .branches(branches)
                .build();
    }

    public Country findAllSwiftCodesWithDetailsByCountryISO2(String countryISO2) {
        List<BankEntity> foundBankEntities = findBankEntitiesByCountryISO2(countryISO2);
        if (foundBankEntities.isEmpty()) {
            throw new CountryNotFoundException("Country with " + countryISO2 + " ISO2 code - not found.");
        }
        return Country.builder()
                .countryISO2(countryISO2)
                .countryName(countryIso2ToName(countryISO2))
                .swiftCodes(foundBankEntities)
                .build();
    }

    public ResponseEntity<Map<String,String>> addBankEntityToDatabase(BankEntity bankEntity){
        try {
            uppercaseSwiftAndCountryCodeAndCountryNameOfBankEntity(bankEntity);
            String swiftCode = bankEntity.getSwiftCode();
            if (swiftCode.length() != 8 && swiftCode.length() != 11 || !swiftCode.matches("[A-Z0-9]+")) {
                throw new InvalidDataException("Invalid SWIFT code: must be 8 or 11 alphanumeric characters.");
            }
            if (bankEntity.isHeadquarter() ^ calculateIsHeadquarter(bankEntity.getSwiftCode())) {
                throw new InvalidDataException("isHeadquarter input field is " + bankEntity.isHeadquarter()
                        + " while SWIFT code indicates it is " + calculateIsHeadquarter(bankEntity.getSwiftCode()));
            }
            repository.save(bankEntity);
            return ResponseEntity.ok(Map.of("message", "SWIFT Code added successfully"));
        }catch (Exception e){
            throw new InvalidDataException(e.getMessage());
        }
    }

    public Map<String,String> deleteBankEntityFromDatabase(String swiftCode, String bankName, String countryISO2){
        BankEntity foundBank = getBankEntityBySwiftCode(swiftCode);
        if (foundBank.getBankName().equals(bankName) && foundBank.getCountryISO2().equals(countryISO2)) {
            repository.delete(foundBank);
            return Map.of("message", "SWIFT Code deleted successfully");
        } else {
            return Map.of("message", "Input data does not match!");
        }
    }

    private void uppercaseSwiftAndCountryCodeAndCountryNameOfBankEntity(BankEntity bankEntity) {
        bankEntity.setSwiftCode(bankEntity.getSwiftCode().toUpperCase());
        bankEntity.setCountryName(bankEntity.getCountryName().toUpperCase());
        bankEntity.setCountryISO2(bankEntity.getCountryISO2().toUpperCase());
    }

    private List<BankEntity> allBankEntitiesFromCsvToList(String csvFilePath) {
        try {
            return new CsvToBeanBuilder<BankEntity>(new FileReader(csvFilePath))
                    .withType(BankEntity.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            throw new CsvProcessingException("CSV file not found at: " + csvFilePath, e);
        } catch (Exception e) {
            throw new CsvProcessingException("Unexpected error processing CSV file (" + csvFilePath + ").", e);
        }
    }

    private List<BankEntity> saveAllBankEntitiesFromListToDatabase(List<BankEntity> bankEntities){
        return bankEntities.stream()
                .peek(bankEntity -> {
                    String swiftCode = bankEntity.getSwiftCode();
                    uppercaseSwiftAndCountryCodeAndCountryNameOfBankEntity(bankEntity);
                    trimWhiteSpaces(bankEntity);
                    bankEntity.setHeadquarter(calculateIsHeadquarter(swiftCode));
                })
                .map(repository::save)
                .collect(Collectors.toList());
    }

    private void trimWhiteSpaces(BankEntity bankEntity){
        if (bankEntity.getAddress() != null) {
            bankEntity.setAddress(bankEntity.getAddress().trim());
        }
    }

    private boolean calculateIsHeadquarter(String swiftCode) {
        if (swiftCode != null && swiftCode.length() >= 3) {
            String suffix = swiftCode.substring(swiftCode.length() - 3);
            return "XXX".equals(suffix);
        }
        return false;
    }

    private List<BankEntity> findAllBranchesByHeadquarterSwiftCode (String swiftCode){
        String mainSwiftCodeOfHQ = swiftCode.substring(0, 8);
        return repository
                .findAll()
                .stream()
                .filter(bankEntity -> bankEntity.getSwiftCode().substring(0, 8).contains(mainSwiftCodeOfHQ)
                        && !calculateIsHeadquarter(bankEntity.getSwiftCode()))
                .collect(Collectors.toList());
    }

    private List<BankEntity> findBankEntitiesByCountryISO2(String countryISO2) {
        return repository.findAllBanksByCountryISO2(countryISO2);
    }

    private String countryIso2ToName(String countryIso2) {
        return repository.findFirstByCountryISO2(countryIso2)
                .map(BankEntity::getCountryName)
                .orElse(null);
    }
}
