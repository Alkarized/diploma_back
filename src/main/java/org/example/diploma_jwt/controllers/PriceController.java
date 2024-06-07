package org.example.diploma_jwt.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.diploma_jwt.exceptions.EmptyDataException;
import org.example.diploma_jwt.models.Settings;
import org.example.diploma_jwt.models.User;
import org.example.diploma_jwt.models.usable.LogType;
import org.example.diploma_jwt.models.usable.ParsedExcelData;
import org.example.diploma_jwt.playload.request.DataRequest;
import org.example.diploma_jwt.playload.request.ExcelDataRequest;
import org.example.diploma_jwt.playload.response.MessageResponse;
import org.example.diploma_jwt.services.AuthService;
import org.example.diploma_jwt.services.model.*;
import org.example.diploma_jwt.utils.ExcelDataParser;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    private final UserService userService;
    private final UserLogService userLogService;
    private final ParsedExcelDataService parsedExcelDataService;

    @ResponseBody
    @PostMapping("excel")
    public MessageResponse priceAllUpdateExcel(@ModelAttribute ExcelDataRequest request) {
        User user = authService.getCurrentUser();
        Settings settings = user.getSettings();

        ParsedExcelData parsedExcelData = ExcelDataParser.parseData(request.getData(), settings);
        settings.setParsedExcelData(parsedExcelData);
        settingsService.saveSettings(settings);

        log.info("data got parsed: {}", parsedExcelData);
        userLogService.createAndSaveLog(user, "new Update Price Excel Requset", LogType.INFO);
        userLogService.createAndSaveLog(user, "got parsed Data: " + parsedExcelData.toString(), LogType.INFO);

        if (settings.isVkEnable() && settings.isAvitoEnable() && settings.isOzonEnable()) {
            throw new EmptyDataException("Не выбрана ни одна из систем!");
        }

        if (settings.isAvitoEnable()) {
            userLogService.createAndSaveLog(user, "Started EXCEL Avito update", LogType.INFO);
            avitoService.updateAvitoPrice(user, parsedExcelData);
        } else {
            userLogService.createAndSaveLog(user, "Avito disabled", LogType.WARN);
        }

        if (settings.isOzonEnable()) {
            userLogService.createAndSaveLog(user, "Started EXCEL Ozon update", LogType.INFO);
            ozonService.updateOzonPrice(user, parsedExcelData);
        } else {
            userLogService.createAndSaveLog(user, "OZON disabled", LogType.WARN);
        }

        if (settings.isVkEnable()) {
            userLogService.createAndSaveLog(user, "Started EXCEL VK update", LogType.INFO);
            vkService.updateVKPrice(user, parsedExcelData);
        } else {
            userLogService.createAndSaveLog(user, "VK disabled", LogType.WARN);
        }

        return new MessageResponse("Data Updated");
    }

    @ResponseBody
    @PostMapping("data/all")
    public MessageResponse priceAllUpdateJson(@ModelAttribute DataRequest request) {
        User user = authService.getCurrentUser();
        Settings settings = user.getSettings();

        if (settings.isVkEnable() && settings.isAvitoEnable() && settings.isOzonEnable()) {
            throw new EmptyDataException("Не выбрана ни одна из систем!");
        }

        userLogService.createAndSaveLog(user, "new Update Price API Requset", LogType.INFO);
        ParsedExcelData parsedExcelData = ExcelDataParser.parseApiData(request);
        userLogService.createAndSaveLog(user, "got parsed Data: " + parsedExcelData.toString(), LogType.INFO);

        if (settings.isAvitoEnable()) {
            userLogService.createAndSaveLog(user, "Started API ALL Avito update", LogType.INFO);

            avitoService.updateAvitoPrice(user, parsedExcelData);
        } else {
            userLogService.createAndSaveLog(user, "Avito disabled", LogType.WARN);
        }

        if (settings.isOzonEnable()) {
            userLogService.createAndSaveLog(user, "Started API ALL Ozon update", LogType.INFO);

            ozonService.updateOzonPrice(user, parsedExcelData);
        } else {
            userLogService.createAndSaveLog(user, "OZON disabled", LogType.WARN);
        }

        if (settings.isVkEnable()) {
            userLogService.createAndSaveLog(user, "Started API ALL VK update", LogType.INFO);

            vkService.updateVKPrice(user, parsedExcelData);
        } else {
            userLogService.createAndSaveLog(user, "VK disabled", LogType.WARN);
        }

        return new MessageResponse("Data Updated");

    }

    @ResponseBody
    @PostMapping("data/avito")
    public MessageResponse priceAvitoUpdateJson(@ModelAttribute DataRequest request) {
        User user = authService.getCurrentUser();
        Settings settings = user.getSettings();

        if (settings.isAvitoEnable()) {
            throw new EmptyDataException("Avito Не включено");
        }

        userLogService.createAndSaveLog(user, "Started API ONLY AVITO update", LogType.INFO);
        avitoService.updateAvitoPrice(user, ExcelDataParser.parseApiData(request));

        return new MessageResponse("Data Updated");

    }

    @ResponseBody
    @PostMapping("data/vk")
    public MessageResponse priceVkUpdateJson(@ModelAttribute DataRequest request) {
        User user = authService.getCurrentUser();
        Settings settings = user.getSettings();

        if (settings.isVkEnable()) {
            throw new EmptyDataException("VK не включено");
        }

        userLogService.createAndSaveLog(user, "Started API ONLY VK update", LogType.INFO);

        vkService.updateVKPrice(user, ExcelDataParser.parseApiData(request));

        return new MessageResponse("Data Updated");

    }

    @ResponseBody
    @PostMapping("data/ozon")
    public MessageResponse priceOzonUpdateJson(@ModelAttribute DataRequest request) {
        User user = authService.getCurrentUser();
        Settings settings = user.getSettings();

        if (settings.isOzonEnable()) {
            throw new EmptyDataException("Ozon не включен");
        }

        userLogService.createAndSaveLog(user, "Started API ONLY OZON update", LogType.INFO);

        ozonService.updateOzonPrice(user, ExcelDataParser.parseApiData(request));

        return new MessageResponse("Data Updated");

    }
}
