package com.recrutTask.swift_api.services;

import com.opencsv.bean.CsvToBeanBuilder;
import com.recrutTask.swift_api.models.BankEntity;
import com.recrutTask.swift_api.models.Country;
import com.recrutTask.swift_api.repositories.SwiftCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    private List<BankEntity> allBankEntitiesFromCsvToList(String csvFilePath) {
        try {
            return new CsvToBeanBuilder<BankEntity>(new FileReader(csvFilePath))
                    .withType(BankEntity.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("CSV file not found at: " + csvFilePath, e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error processing CSV file.", e);
        }
    }

    private List<BankEntity> saveAllBankEntitiesFromListToDatabase(List<BankEntity> bankEntities){
        return bankEntities.stream()
                .peek(bankEntity -> {
                    String swiftCode = bankEntity.getSwiftCode();
                    trimWhiteSpaces(bankEntity);
                    bankEntity.setHeadquarter(calculateIsHeadquarter(swiftCode));
                })
                .map(repository::save)
                .collect(Collectors.toList());
    }

    public BankEntity getBankEntityBySwiftCode(String swiftCode) {
        BankEntity foundBank = repository.findById(swiftCode).get();
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

    private void trimWhiteSpaces(BankEntity bankEntity){
        bankEntity.setAddress(bankEntity.getAddress().trim());
    }

    public boolean calculateIsHeadquarter(String swiftCode) {
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

    public Country findAllSwiftCodesWithDetailsByCountryISO2(String countryISO2) throws Exception {
        List<BankEntity> foundBankEntities = repository
                .findAll()
                .stream()
                .filter(bankEntity -> bankEntity.getCountryISO2().contains(countryISO2))
                .toList();

        if (foundBankEntities.isEmpty()) {
            throw new Exception("Country with " + countryISO2 + " ISO2 code - not found.");
        }

        return Country.builder()
                .countryISO2(countryISO2)
                .countryName(countryIso2ToName(countryISO2))
                .swiftCodes(foundBankEntities)
                .build();
    }

    private String countryIso2ToName(String countryIso2) {
        return repository.findFirstByCountryISO2(countryIso2)
                .map(BankEntity::getCountryName)
                .orElse(null);
    }

    public Map<String,String> addBankEntityToDatabase(BankEntity bankEntity){
        try {
            repository.save(bankEntity);
            return Map.of("message", "SWIFT Code added successfully");
        }catch (HttpMessageNotReadableException e){
            return Map.of("message", "Valid data! RequestBody should looks like:\n"+ BankEntity.builder().toString());
        }
    }

    public Map<String,String> deleteBankEntityFromDatabase(String swiftCode, String bankName, String countryISO2){
        try {
            BankEntity foundBank = getBankEntityBySwiftCode(swiftCode);
            if(foundBank.getBankName().contains(bankName)&&foundBank.getCountryISO2().contains(countryISO2)){
                repository.deleteById(foundBank.getSwiftCode());
            }else return Map.of("message", "The data does not match!");
            return Map.of("message", "SWIFT Code deleted successfully");
        }catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No bank found with this SWIFT code", exception);
        }
    }
}
