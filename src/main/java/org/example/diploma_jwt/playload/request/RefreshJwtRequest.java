package org.example.diploma_jwt.playload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshJwtRequest {

    @NotNull(message = "Токен не может быть пустыми")
    @Size(min = 5, max = 255, message = "Длина пароля должна быть не более 255 символов")
    public String refreshToken;

}
