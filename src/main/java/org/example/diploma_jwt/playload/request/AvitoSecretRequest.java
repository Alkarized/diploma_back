package org.example.diploma_jwt.playload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AvitoSecretRequest {
    @NotNull
    private String clientID;

    @NotNull
    private String clientSecret;
}
