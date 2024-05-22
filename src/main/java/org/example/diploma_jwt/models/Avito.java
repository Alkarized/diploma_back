package org.example.diploma_jwt.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "t_avito")
public class Avito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String clientId = "";
    private String clientSecret = "";
    private String token = "";

    private boolean isPercentage = false;
    private Double markup = 0D;

    public Avito(User user) {
        this.user = user;
    }
}
