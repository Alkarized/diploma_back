package org.example.diploma_jwt.services.model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.diploma_jwt.models.Item;
import org.example.diploma_jwt.models.Settings;
import org.example.diploma_jwt.models.User;
import org.example.diploma_jwt.models.VK;
import org.example.diploma_jwt.models.usable.CheckOrder;
import org.example.diploma_jwt.models.usable.LogType;
import org.example.diploma_jwt.models.usable.ParsedExcelData;
import org.example.diploma_jwt.repositories.VKRepository;
import org.example.diploma_jwt.utils.VKUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VKService {
    private final VKRepository vkRepository;
    private final UserLogService userLogService;

    public VK save(VK vk){
        return vkRepository.save(vk);
    }

    @Async
    public void updateVKPrice (User user, ParsedExcelData parsedExcelData){
        VK vk = user.getVk();
        Settings settings = user.getSettings();

        //log.info("parsed in updateVKPrice: {}", parsedExcelData );

        List<Item> toUpdate = new ArrayList<>();

        List<Item> items = VKUtils.getAllVKItems(vk, settings);
        System.out.println("ITEMS GOTTTTT: " + items);

        //log.info("items from VK GOT {}", items.toString());
        System.out.println("size:" + items.size());

        userLogService.createAndSaveLog(user, "VK DATA GOT: " + items.toString(), LogType.INFO);

        //log.info("from vk got list: {}", items);
        for (Item item : items) {
            //System.out.println(settings.getCheckOrders());
            List<CheckOrder> orders = settings.getCheckOrders();
            //System.out.println("orders: " + orders.toString());
            for (int i = 0; i < orders.size(); i++) {
                CheckOrder order = orders.get(i);
                //System.out.println("order NOW:" + order.toString());
                if (order.compareItems(item, parsedExcelData, settings)) {
                    item.setNewPrice(vk.isPercentage(), vk.getMarkup());
                    if (!item.getOldPrice().equals(item.getPrice())) {
                        toUpdate.add(item);
                        userLogService.createAndSaveLog(user, "Vk added new price " + item.getPrice() + " for " + item.getTitle() + ", with " + order.toString(), LogType.INFO);
                        log.info("added new price {} with {}", item.getPrice(), order);
                    }
                    break;
                }
            }

        }
        log.info("VK items to update: {}", toUpdate);
        userLogService.createAndSaveLog(user, "TO VK UPDATE FOUND: " + toUpdate.toString(), LogType.INFO);

        List<Boolean> updates = VKUtils.updateVKItems(vk, settings, toUpdate);
        log.info("VK UPDATED: {}", updates);
        userLogService.createAndSaveLog(user, "VK updated List: " + updates.toString(), LogType.INFO);


    }
}
