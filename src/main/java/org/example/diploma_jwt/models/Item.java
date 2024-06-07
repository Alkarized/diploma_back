package org.example.diploma_jwt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.example.diploma_jwt.models.usable.item_data.ComplexItem;

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
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double price;
    private String article;
    private String code;
    private String company;

    @JsonIgnore
    private String title;

    @JsonIgnore
    private Integer rowNum;

    @JsonIgnore
    private Long itemCompanyID;

    @JsonIgnore
    private Double oldPrice;

    @Transient
    @JsonIgnore
    private ComplexItem complexItem = new ComplexItem();

    public void setNewPrice(boolean isPerctange, Double markup){
        if (isPerctange) {
            setPrice(getPrice() * (1 + markup/100));
        } else {
            setPrice(getPrice() + markup);
        }
    }

}

