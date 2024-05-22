package org.example.diploma_jwt.playload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PercentageRequest {
    @NotNull
    private boolean isPercentage;

    @NotNull
    private Double markup;
}
