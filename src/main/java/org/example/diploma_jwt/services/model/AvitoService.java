package org.example.diploma_jwt.services.model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.diploma_jwt.models.Avito;
import org.example.diploma_jwt.models.Item;
import org.example.diploma_jwt.models.Settings;
import org.example.diploma_jwt.models.User;
import org.example.diploma_jwt.models.usable.CheckOrder;
import org.example.diploma_jwt.models.usable.LogType;
import org.example.diploma_jwt.models.usable.ParsedExcelData;
import org.example.diploma_jwt.repositories.AvitoRepository;
import org.example.diploma_jwt.utils.AvitoUtils;
import org.example.diploma_jwt.utils.VKUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AvitoService {
    private final AvitoRepository avitoRepository;
    private final UserLogService userLogService;

    public Avito save(Avito avito){
        return avitoRepository.save(avito);
    }

    @Async
    public void updateAvitoPrice (User user, ParsedExcelData parsedExcelData){
        Avito avito = user.getAvito();
        Settings settings = user.getSettings();

        List<Item> toUpdate = new ArrayList<>();

        String token = AvitoUtils.getToken(avito);
        List<Item> items = AvitoUtils.getAllItems(avito, settings, token);

        userLogService.createAndSaveLog(user, "AVITO DATA GOT: " + items.toString(), LogType.INFO);


        for (Item item : items) {
            List<CheckOrder> orders = settings.getCheckOrders();
            for (int i = 0; i < orders.size(); i++) {
                CheckOrder order = orders.get(i);
                if (order.compareItems(item, parsedExcelData, settings)) {
                    item.setNewPrice(avito.isPercentage(), avito.getMarkup());
                    if (!item.getOldPrice().equals(item.getPrice())){
                        toUpdate.add(item);
                        log.info("added new price {} with {}", item.getPrice(), order);
                        userLogService.createAndSaveLog(user, "Avito added new price " + item.getPrice() + " for " + item.getTitle() + ", with " + order.toString(), LogType.INFO);
                    }
                    break;


                }
            }

        }

        log.info("AVITO items to update: {}", toUpdate);
        userLogService.createAndSaveLog(user, "TO Avito UPDATE FOUND: " + toUpdate.toString(), LogType.INFO);

        List<Boolean> updates = AvitoUtils.updateAvitoItemsPrice(avito, settings, token, toUpdate);
        log.info("AVITO UPDATED: {}", updates);
        userLogService.createAndSaveLog(user, "Avito updated List: " + updates.toString(), LogType.INFO);
    }
}
