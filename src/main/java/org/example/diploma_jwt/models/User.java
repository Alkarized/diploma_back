package org.example.diploma_jwt.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.diploma_jwt.models.usable.Role;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "t_user",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 5, max = 255, message = "Имя пользователя должно содержать от 5 до 50 символов")
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

    @ElementCollection
    @NotNull
    private Set<Role> roles;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "avito_id", referencedColumnName = "id")
    private Avito avito;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ozon_id", referencedColumnName = "id")
    private Ozon ozon;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vk_id", referencedColumnName = "id")
    private VK vk;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "settings_id", referencedColumnName = "id")
    private Settings settings;

    public User(String username, String email, String password, String firstname) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstname = firstname;

    }

}
