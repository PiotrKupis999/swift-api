package com.recrutTask.swift_api.services;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.recrutTask.swift_api.dto.CsvBean;
import com.recrutTask.swift_api.models.SwiftCode;
import com.recrutTask.swift_api.repositories.SwiftCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Path;
import java.util.List;
@Service
public class ReadCsvDataService {
    @Autowired
    SwiftCodeRepository swiftCodeRepository;


<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream
    public void addAllBeansToRepository(List<CsvBean> beans){
        System.out.println("halo1");

        for (CsvBean bean : beans){
            System.out.println("halo");
            SwiftCode swiftCode = csvBeanSwiftService.csvBeanToSwiftCode(bean);
=======
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
    public void readAllBeansToDatabase(List<SwiftCode> swiftCodes){
        for (SwiftCode swiftCode : swiftCodes){

            swiftCode.setHeadquarter(calculateIsHeadquarter(swiftCode.getSwiftCode()));

            trimWhiteSpacesInAddress(swiftCode);

<<<<<<< Updated upstream
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
            swiftCodeRepository.save(swiftCode);
        }
    }


    public boolean calculateIsHeadquarter(String swiftCode) {

        if (swiftCode != null && swiftCode.length() >= 3) {
            String suffix = swiftCode.substring(swiftCode.length() - 3);
            return "XXX".equals(suffix);

        }
        return false;
    }

    public void trimWhiteSpacesInAddress(SwiftCode swiftCode){

        swiftCode.setAddress(swiftCode.getAddress().trim());

    }


}
