package org.example.diploma_jwt.services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.diploma_jwt.models.Settings;
import org.example.diploma_jwt.models.User;
import org.example.diploma_jwt.models.usable.LogType;
import org.example.diploma_jwt.models.usable.ParsedExcelData;
import org.example.diploma_jwt.services.model.AvitoService;
import org.example.diploma_jwt.services.model.OzonService;
import org.example.diploma_jwt.services.model.UserLogService;
import org.example.diploma_jwt.services.model.VKService;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskSchedulerService {
    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private final Map<Long, ScheduledFuture<?>> scheduledTasks = new HashMap<>();
    private final AvitoService avitoService;
    private final OzonService ozonService;
    private final VKService vkService;
    private final UserLogService userLogService;

    public ScheduledFuture<?> scheduleUserEvent(User user) {
        Settings settings = user.getSettings();
        ParsedExcelData parsedExcelData = settings.getParsedExcelData();
        log.info("data parsed: {}", parsedExcelData.toString());

        Runnable task = () -> {
            userLogService.createAndSaveLog(user, "Started scheduled Task", LogType.INFO);
            if (parsedExcelData != null){
                if (settings.isAvitoEnable()) {
                    avitoService.updateAvitoPrice(user, parsedExcelData);
                }

                if (settings.isOzonEnable()) {
                    ozonService.updateOzonPrice(user, parsedExcelData);
                }

                if (settings.isVkEnable()) {
                    vkService.updateVKPrice(user, parsedExcelData);
                }
            }
        };
        ScheduledFuture<?> scheduledTask = threadPoolTaskScheduler.schedule(task, new CronTrigger("0 " + settings.getMinutes() + " " + settings.getHours() + " * * *"));
        scheduledTasks.put(user.getId(), scheduledTask);
        return scheduledTask;
    }

    public void cancelUserEvent(Long userId) {
        ScheduledFuture<?> scheduledTask = scheduledTasks.get(userId);
        if (scheduledTask != null) {
            scheduledTask.cancel(true);
            scheduledTasks.remove(userId);
        }
    }

}
