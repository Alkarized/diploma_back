package org.example.diploma_jwt.repositories;

import org.example.diploma_jwt.models.usable.ParsedExcelData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParsedExcelDataRepository extends JpaRepository<ParsedExcelData, Long> {
}
