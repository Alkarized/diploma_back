package org.example.diploma_jwt.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.diploma_jwt.configs.ThreadPoolTaskSchedulerConfig;
import org.example.diploma_jwt.models.*;
import org.example.diploma_jwt.models.usable.LogType;
import org.example.diploma_jwt.playload.request.*;
import org.example.diploma_jwt.playload.response.LogResponse;
import org.example.diploma_jwt.playload.response.MessageResponse;
import org.example.diploma_jwt.services.AuthService;
import org.example.diploma_jwt.services.TaskSchedulerService;
import org.example.diploma_jwt.services.model.*;
import org.example.diploma_jwt.utils.BlackListParser;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/data")
@RequiredArgsConstructor
public class DataController {
    private final AuthService authService;
    private final AvitoService avitoService;
    private final VKService vkService;
    private final OzonService ozonService;
    private final SettingsService settingsService;
    private final TaskSchedulerService taskSchedulerService;
    private final UserLogService userLogService;

    @ResponseBody
    @GetMapping("user")
    public User user() {
        User user = authService.getCurrentUser();
        user.setPassword(null);
        return user;
    }

    @ResponseBody
    @PostMapping("avito/secret")
    public MessageResponse saveAvitoSecret(@ModelAttribute @Valid AvitoSecretRequest avitoSecretRequest) {
        User user = authService.getCurrentUser();
        Avito avito = user.getAvito();
        avito.setClientId(avitoSecretRequest.getClientID());
        avito.setClientSecret(avitoSecretRequest.getClientSecret());
        avitoService.save(avito);

        userLogService.createAndSaveLog(user, "saved new Avito secret", LogType.INFO);

        return new MessageResponse("saved new Avito");
    }

    @ResponseBody
    @PostMapping("avito/percentage/update")
    public MessageResponse updateAvitoPercentage(@ModelAttribute @Valid PercentageRequest percentageRequest) {
        User user = authService.getCurrentUser();
        Avito avito = user.getAvito();
        avito.setMarkup(percentageRequest.getMarkup());
        avito.setPercentage(percentageRequest.isPercentage());

        avitoService.save(avito);

        userLogService.createAndSaveLog(user, "saved Avito precentages", LogType.INFO);


        return new MessageResponse("saved new Avito markup");
    }

    @ResponseBody
    @PostMapping("vk/percentage/update")
    public MessageResponse updateVKPercentage(@ModelAttribute @Valid PercentageRequest percentageRequest) {
        User user = authService.getCurrentUser();
        VK vk = user.getVk();
        vk.setMarkup(percentageRequest.getMarkup());
        vk.setPercentage(percentageRequest.isPercentage());
        vkService.save(vk);

        userLogService.createAndSaveLog(user, "saved VK percentages", LogType.INFO);

        return new MessageResponse("saved new VK markup");
    }

    @ResponseBody
    @PostMapping("ozon/percentage/update")
    public MessageResponse updateOzonPercentage(@ModelAttribute @Valid PercentageRequest percentageRequest) {
        User user = authService.getCurrentUser();
        Ozon ozon = user.getOzon();
        ozon.setMarkup(percentageRequest.getMarkup());
        ozon.setPercentage(percentageRequest.isPercentage());
        ozonService.save(ozon);

        userLogService.createAndSaveLog(user, "saved OZON percentages", LogType.INFO);

        return new MessageResponse("saved new Ozon markup");
    }

    @ResponseBody
    @PostMapping("vk/token/update")
    public MessageResponse updateVKToken(@ModelAttribute @Valid UpdateTokenRequest updateTokenRequest) {
        User user = authService.getCurrentUser();
        VK vk = user.getVk();
        vk.setToken(updateTokenRequest.getToken());
        vk.setClubID(updateTokenRequest.getId());
        vkService.save(vk);

        userLogService.createAndSaveLog(user, "saved VK new token", LogType.INFO);

        return new MessageResponse("saved new VK token");
    }

    @ResponseBody
    @PostMapping("ozon/token/update")
    public MessageResponse updateOzonToken(@ModelAttribute @Valid UpdateTokenRequest updateTokenRequest) {
        User user = authService.getCurrentUser();
        Ozon ozon = user.getOzon();
        ozon.setToken(updateTokenRequest.getToken());
        ozon.setClientID(updateTokenRequest.getId());
        ozonService.save(ozon);

        userLogService.createAndSaveLog(user, "saved ozon new token", LogType.INFO);


        return new MessageResponse("saved new Ozon token");
    }

    @ResponseBody
    @PostMapping("settings/enable/update")
    public MessageResponse updateSettingsEnable(@ModelAttribute @Valid SettingsEnableRequest request) {
        User user = authService.getCurrentUser();
        Settings userSettings = user.getSettings();

        userSettings.setAvitoEnable(request.isAvitoEnable());
        userSettings.setVkEnable(request.isVkEnable());
        userSettings.setOzonEnable(request.isOzonEnable());

        settingsService.saveSettings(userSettings);

        userLogService.createAndSaveLog(user, "updates enable settings, where: \n AVITO - " + request.isAvitoEnable() + "\n" +
                "VK - " + request.isVkEnable() + "\n" +
                "Ozon - " + request.isOzonEnable(), LogType.INFO);

        return new MessageResponse("saved enable settings");
    }

    @ResponseBody
    @PostMapping("settings/blacklist/update")
    public MessageResponse updateBlackList(@ModelAttribute @Valid StringRequest request) {
        User user = authService.getCurrentUser();
        Settings userSettings = user.getSettings();
        userSettings.setBlackList(request.getBlackList());
        userSettings.setBlackListForm(BlackListParser.parseList(request.getBlackList()));

        settingsService.saveSettings(userSettings);

        userLogService.createAndSaveLog(user, "Saved new BlackList: " + userSettings.getBlackListForm().toString(), LogType.INFO);

        return new MessageResponse("saved blacklist settings");
    }


    @ResponseBody
    @PostMapping("settings/excel/update")
    public MessageResponse updateExcelData(@ModelAttribute ExcelFieldsRequest request) {
        User user = authService.getCurrentUser();
        Settings userSettings = user.getSettings();
        userSettings.getExcel().setArticle(request.getArticle());
        userSettings.getExcel().setCode(request.getCode());
        userSettings.getExcel().setPrice(request.getPrice());
        userSettings.getExcel().setCompany(request.getCompany());
        userSettings.getExcel().setTitle(request.getTitle());

        settingsService.saveSettings(userSettings);

        userLogService.createAndSaveLog(user, "saved new excel settings: " + userSettings.getExcel().toLog(), LogType.INFO);

        return new MessageResponse("saved excel settings");
    }

    @ResponseBody
    @PostMapping("settings/time/update")
    public MessageResponse updateTimeUpdate(@ModelAttribute TimeUpdateRequest request) {
        User user = authService.getCurrentUser();
        Settings userSettings = user.getSettings();

        userSettings.setHours(request.getHours());
        userSettings.setMinutes(request.getMinutes());
        userSettings.setTimeEnable(request.isEnabled());

        settingsService.saveSettings(userSettings);

        taskSchedulerService.cancelUserEvent(user.getId());

        if (request.isEnabled()){
            taskSchedulerService.scheduleUserEvent(user);
            userLogService.createAndSaveLog(user, "time update set new: " + userSettings.getHours() + "h" + " " + userSettings.getMinutes() + "m", LogType.INFO);
        } else {
            userLogService.createAndSaveLog(user, "scheduled task canceled", LogType.INFO);

        }

        return new MessageResponse("saved time settings");
    }

    @ResponseBody
    @PostMapping("settings/orders/update")
    public MessageResponse updateOrders(@ModelAttribute @Valid OrdersRequest request) {
        User user = authService.getCurrentUser();
        Settings userSettings = user.getSettings();

        System.out.println(userSettings.getCheckOrders());

        userSettings.setCheckOrders(request.getOrders());
        settingsService.saveSettings(userSettings);

        System.out.println(request.getOrders());
        System.out.println(userSettings.getCheckOrders());


        userLogService.createAndSaveLog(user, "saved new orders", LogType.INFO);

        return new MessageResponse("saved new settings order");
    }

    @ResponseBody
    @GetMapping("logs/get")
    public LogResponse updateOrders() {
        User user = authService.getCurrentUser();
        // log.info("got logs: {}", user.getLogs());
        return new LogResponse(user.getLogs());
    }

}
