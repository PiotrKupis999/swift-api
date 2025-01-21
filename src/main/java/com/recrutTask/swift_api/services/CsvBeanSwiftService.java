package com.recrutTask.swift_api.services;

import com.recrutTask.swift_api.dto.CsvBean;
import com.recrutTask.swift_api.models.SwiftCode;
import org.springframework.stereotype.Service;

@Service
public class CsvBeanSwiftService {

    /*
    public SwiftCode csvBeanToSwiftCode(CsvBean bean){

        return new SwiftCode(bean.getSwiftCode(), bean.getAddress(), bean.getBankName(), bean.getCountryName(), bean.getCountryISO2(), calculateIsHeadquarter(bean.getSwiftCode()));

    }

     */
    public boolean calculateIsHeadquarter(String swiftCode) {
        if (swiftCode != null && swiftCode.length() >= 3) {
            String suffix = swiftCode.substring(swiftCode.length() - 3);
            return "XXX".equals(suffix);

        }
        return false;
    }


}
