package org.example.diploma_jwt.playload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtRequest {

    @Size(min = 5, max = 50, message = "Имя пользователя должно содержать от 5 до 50 символов")
    @NotNull(message = "Имя пользователя не может быть пустыми")
    private String username;

    @NotNull(message = "Пароль не может быть пустыми")
    @Size(min = 5, max = 255, message = "Длина пароля должна быть не более 255 символов")
    private String password;

}
