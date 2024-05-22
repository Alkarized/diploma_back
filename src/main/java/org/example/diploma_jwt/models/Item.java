package org.example.diploma_jwt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "t_item")
@ToString
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double price;
    private String article;
    private String code;
    private String company;
    private String title;

    @Transient
    @JsonIgnore
    private Integer rowNum;

    @Transient
    @JsonIgnore
    private Integer itemCompanyID;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "settings_id")
    private Settings settings;

    public Item(Settings settings) {
        this.settings = settings;
    }

}

