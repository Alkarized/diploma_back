package org.example.diploma_jwt.playload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SettingsEnableRequest {
    private boolean avitoEnable ;
    private boolean vkEnable;
    private boolean ozonEnable;
}
