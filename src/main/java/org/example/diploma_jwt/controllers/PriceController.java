package org.example.diploma_jwt.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.diploma_jwt.models.User;
import org.example.diploma_jwt.models.usable.ParsedExcelData;
import org.example.diploma_jwt.playload.request.ExcelDataRequest;
import org.example.diploma_jwt.playload.response.MessageResponse;
import org.example.diploma_jwt.services.AuthService;
import org.example.diploma_jwt.services.model.AvitoService;
import org.example.diploma_jwt.services.model.OzonService;
import org.example.diploma_jwt.services.model.SettingsService;
import org.example.diploma_jwt.services.model.VKService;
import org.example.diploma_jwt.utils.ExcelDataParser;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/price")
@RequiredArgsConstructor
public class PriceController {
    private final AuthService authService;
    private final AvitoService avitoService;
    private final VKService vkService;
    private final OzonService ozonService;
    private final SettingsService settingsService;

    @ResponseBody
    @PostMapping("excel")
    public MessageResponse priceAllUpdateExcel(@ModelAttribute ExcelDataRequest request) {
        User user = authService.getCurrentUser();

        ParsedExcelData parsedExcelData = ExcelDataParser.parseData(request.getData(), user.getSettings());

        if (user.getSettings().isAvitoEnable()) {
            log.info("1");
        }

        if (user.getSettings().isOzonEnable()) {
            log.info("2");
        }

        if (user.getSettings().isVkEnable()) {
            log.info("3");
        }

        return new MessageResponse("Data Updated");
    }
}
