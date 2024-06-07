package org.example.diploma_jwt.services.model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.diploma_jwt.models.Avito;
import org.example.diploma_jwt.models.User;
import org.example.diploma_jwt.models.UserLog;
import org.example.diploma_jwt.models.usable.LogType;
import org.example.diploma_jwt.repositories.AvitoRepository;
import org.example.diploma_jwt.repositories.UserLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserLogService {
    private final UserLogRepository userLogRepository;

    public UserLog save(UserLog userLog){
        return userLogRepository.save(userLog);
    }

    public UserLog createAndSaveLog(User user, String message, LogType logType){
        UserLog userLog = new UserLog();
        userLog.setUser(user);
        userLog.setMessage(message);
        userLog.setType(logType);
        userLog.setTimestamp(LocalDateTime.now());

        return save(userLog);
    }
}
