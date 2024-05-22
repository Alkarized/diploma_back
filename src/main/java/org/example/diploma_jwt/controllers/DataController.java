package org.example.diploma_jwt.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.diploma_jwt.models.*;
import org.example.diploma_jwt.playload.request.*;
import org.example.diploma_jwt.playload.response.MessageResponse;
import org.example.diploma_jwt.services.AuthService;
import org.example.diploma_jwt.services.model.*;
import org.example.diploma_jwt.utils.BlackListParser;
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

        return new MessageResponse("saved enable settings");
    }

    @ResponseBody
    @PostMapping("settings/blacklist/update")
    public MessageResponse updateBlackList(@ModelAttribute @Valid StringRequest request) {
        User user = authService.getCurrentUser();
        Settings userSettings = user.getSettings();
        userSettings.setBlackList(request.getBlackList());
        userSettings.setBlackListItems(BlackListParser.parseList(request.getBlackList(), userSettings));

        settingsService.saveSettings(userSettings);

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

        return new MessageResponse("saved excel settings");
    }
}
