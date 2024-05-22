package org.example.diploma_jwt.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "t_ozon")
public class Ozon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String token = "";
    private Long clientID = 0L;

    private boolean isPercentage = false;
    private Double markup = 0.0;

    public Ozon(User user) {
        this.user = user;
    }
}
