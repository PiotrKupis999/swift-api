package com.recrutTask.swift_api.services;

import org.springframework.stereotype.Service;

@Service
public class CsvBeanSwiftService {

    /*
    public BankEntity csvBeanToSwiftCode(CsvBean bean){

        return new BankEntity(bean.getSwiftCode(), bean.getAddress(), bean.getBankName(), bean.getCountryName(), bean.getCountryISO2(), calculateIsHeadquarter(bean.getSwiftCode()));

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
