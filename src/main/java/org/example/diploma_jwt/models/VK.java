package org.example.diploma_jwt.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "t_vk")
public class VK {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String token = "";
    private Long clubID = 0L;

    private boolean isPercentage= false;
    private Double markup = 0.0;

    public VK(User user) {
        this.user = user;
    }
}
