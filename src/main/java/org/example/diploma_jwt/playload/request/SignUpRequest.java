package org.example.diploma_jwt.playload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class SignUpRequest implements Serializable {
    @Size(min = 5, max = 50, message = "Имя пользователя должно содержать от 5 до 50 символов")
    @NotNull(message = "Имя пользователя не может быть пустыми")
    private String username;

    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @NotNull(message = "Адрес электронной почты не может быть пустыми")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;

    @NotNull(message = "Пароль не может быть пустыми")
    @Size(min = 5, max = 255, message = "Длина пароля должна быть не более 255 символов")
    private String password;

    @NotNull(message = "Имя не может быть пустыми")
    @Size(min = 2, max = 255, message = "Длина пароля должна быть не более 255 символов")
    private String firstname;

}