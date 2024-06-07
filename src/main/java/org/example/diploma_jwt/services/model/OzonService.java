package org.example.diploma_jwt.services.model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.diploma_jwt.models.Item;
import org.example.diploma_jwt.models.Ozon;
import org.example.diploma_jwt.models.Settings;
import org.example.diploma_jwt.models.User;
import org.example.diploma_jwt.models.usable.CheckOrder;
import org.example.diploma_jwt.models.usable.LogType;
import org.example.diploma_jwt.models.usable.ParsedExcelData;
import org.example.diploma_jwt.repositories.AvitoRepository;
import org.example.diploma_jwt.repositories.OzonRepository;
import org.example.diploma_jwt.utils.OzonUtils;
import org.example.diploma_jwt.utils.VKUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OzonService {
    private final OzonRepository ozonRepository;
    private final UserLogService userLogService;

    public Ozon save(Ozon ozon) {return ozonRepository.save(ozon);}

    @Async
    public void updateOzonPrice (User user, ParsedExcelData parsedExcelData){
        Ozon ozon = user.getOzon();
        Settings settings = user.getSettings();

        List<Item> toUpdate = new ArrayList<>();

        List<Item> items = OzonUtils.getAllItems(ozon, settings);
        userLogService.createAndSaveLog(user, "OZON DATA GOT: " + items.toString(), LogType.INFO);


        //log.info("from vk got list: {}", items);
        for (Item item : items) {
            List<CheckOrder> orders = settings.getCheckOrders();
            for (int i = 0; i < orders.size(); i++) {
                CheckOrder order = orders.get(i);
                if (order.compareItems(item, parsedExcelData, settings)) {
                    item.setNewPrice(ozon.isPercentage(), ozon.getMarkup());
                    if (!item.getOldPrice().equals(item.getPrice())) {
                        toUpdate.add(item);
                        log.info("added new price {} with {}", item.getPrice(), order);
                        userLogService.createAndSaveLog(user, "Ozon added new price " + item.getPrice() + " for " + item.getTitle() + ", with " + order.toString(), LogType.INFO);
                    }
                    break;

                }
            }

        }
        log.info("Ozon items to update: {}", toUpdate);
        userLogService.createAndSaveLog(user, "TO OZON UPDATE FOUND: " + toUpdate.toString(), LogType.INFO);


        Boolean updated = OzonUtils.updateOzonItemsPrice(ozon, settings, toUpdate);
        log.info("Ozon UPDATED: {}", updated);
        userLogService.createAndSaveLog(user, "Ozon update: " + updated, LogType.INFO);
    }
}
