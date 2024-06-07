package org.example.diploma_jwt.models.usable.item_data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "t_articleandcompanyform")
@Data
public class ArticleAndCompanyForm {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String article;
    private String company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "many_items_form_id")
    private ManyItemsForm manyItemsForm;

    public ArticleAndCompanyForm(String article, String company) {
        this.article = article;
        this.company = company;
    }
}
