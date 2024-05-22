package org.example.diploma_jwt.models;

import jakarta.persistence.*;
import lombok.*;
import org.example.diploma_jwt.models.usable.CheckOrder;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "t_settings")
public class Settings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean avitoEnable = false;
    private boolean vkEnable = false;
    private boolean ozonEnable = false;

    private String blackList = "";

    @OneToMany(mappedBy = "settings", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> blackListItems = new ArrayList<>();

    @ElementCollection
    private List<CheckOrder> checkOrders = Arrays.asList(CheckOrder.ARTICLE_COMPANY, CheckOrder.ONLY_ARTICLE);

    private boolean timeEnable = false;
    private LocalTime jobTime;

    @Transient
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "excel_id", referencedColumnName = "id")
    private Excel excel = new Excel(this);

    public Settings(User user) {
        this.user = user;
    }

    public void setBlackListItems(List<Item> blackListItems) {
        this.blackListItems.clear();

        if (blackListItems != null) {
            this.blackListItems.addAll(blackListItems);
        }

    }

}
