package org.example.diploma_jwt.services.model;

import lombok.RequiredArgsConstructor;
import org.example.diploma_jwt.models.UserLog;
import org.example.diploma_jwt.models.usable.ParsedExcelData;
import org.example.diploma_jwt.repositories.ParsedExcelDataRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParsedExcelDataService {
    private final ParsedExcelDataRepository parsedExcelDataRepository;

    public ParsedExcelData save(ParsedExcelData parsedExcelData){
        return parsedExcelDataRepository.save(parsedExcelData);
    }
}
