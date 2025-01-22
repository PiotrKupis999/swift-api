package com.recrutTask.swift_api.repositories;

import com.recrutTask.swift_api.models.BankEntity;
import com.recrutTask.swift_api.services.DataService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;

@Repository
public interface SwiftCodeRepository extends JpaRepository<BankEntity, String> {



    //List<BankEntity> findByParentSwiftCode(BankEntity parentSwiftCode);





}