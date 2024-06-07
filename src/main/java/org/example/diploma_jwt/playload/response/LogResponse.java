package org.example.diploma_jwt.playload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.diploma_jwt.models.UserLog;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class LogResponse {
    List<UserLog> userLogList;
}
