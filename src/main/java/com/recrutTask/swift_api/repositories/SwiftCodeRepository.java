package com.recrutTask.swift_api.repositories;

import com.recrutTask.swift_api.SwiftApiApplication;
import com.recrutTask.swift_api.models.SwiftCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SwiftCodeRepository extends JpaRepository<SwiftCode, String> {
    List<SwiftCode> findByParentSwiftCode(SwiftCode parentSwiftCode);
}