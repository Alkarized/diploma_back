package org.example.diploma_jwt.playload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTokenRequest {
    private String token;
    private Long id;
}
