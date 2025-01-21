package com.recrutTask.swift_api.repositories;

import com.recrutTask.swift_api.models.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SwiftCodeRepository extends JpaRepository<BankEntity, String> {
    //List<BankEntity> findByParentSwiftCode(BankEntity parentSwiftCode);



}