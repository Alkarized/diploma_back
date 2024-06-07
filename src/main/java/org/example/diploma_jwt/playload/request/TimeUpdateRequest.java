package org.example.diploma_jwt.playload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeUpdateRequest {
    private int minutes;
    private int hours;
    private boolean enabled;
}
