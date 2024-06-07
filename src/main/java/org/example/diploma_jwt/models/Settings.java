package org.example.diploma_jwt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.example.diploma_jwt.models.usable.CheckOrder;
import org.example.diploma_jwt.models.usable.ManyType;
import org.example.diploma_jwt.models.usable.ParsedExcelData;
import org.example.diploma_jwt.models.usable.item_data.ManyItemsForm;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_settings")
public class Settings {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean avitoEnable = false;
    private boolean vkEnable = false;
    private boolean ozonEnable = false;

    private String blackList = "";

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "black_list_form_id", referencedColumnName = "id")
    private ManyItemsForm blackListForm = new ManyItemsForm();

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parsed_data_id", referencedColumnName = "id")
    private ParsedExcelData parsedExcelData;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<CheckOrder> checkOrders = Arrays.asList(CheckOrder.ARTICLE_FIELD, CheckOrder.ONLY_ARTICLE, CheckOrder.ONLY_CODE, CheckOrder.ARTICLE_COMPANY, CheckOrder.MANY_ARTICLES, CheckOrder.COMPLEX);

    private boolean timeEnable = false;
    private int hours = 0;
    private int minutes = 0;

    @Enumerated
    private ManyType manyType = ManyType.MAX; //todo

    private Boolean failOnNoneFound = false; //todo

    @Transient
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "excel_id", referencedColumnName = "id")
    private Excel excel = new Excel(this);

    @Transient
    @JsonIgnore
    private String noneField = "None"; //todo



    public Settings(User user) {
        this.user = user;
    }


}
