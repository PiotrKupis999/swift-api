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

    @Autowired
    CsvBeanSwiftService csvBeanSwiftService;

    public void addAllBeansToRepository(List<CsvBean> beans){
        System.out.println("halo1");

        for (CsvBean bean : beans){
            System.out.println("halo");
            SwiftCode swiftCode = csvBeanSwiftService.csvBeanToSwiftCode(bean);
            swiftCodeRepository.save(swiftCode);
        }
    }

    public List<CsvBean> parseCsv(Path filePath) throws Exception {
        try (Reader reader = new FileReader(filePath.toFile())) {
            CsvToBean<CsvBean> csvToBean = new CsvToBeanBuilder<CsvBean>(reader)
                    .withType(CsvBean.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse();
        }
    }

}
