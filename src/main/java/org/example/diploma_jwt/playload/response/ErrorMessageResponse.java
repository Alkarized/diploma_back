package org.example.diploma_jwt.playload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessageResponse {
    private String errorMessage;
}
