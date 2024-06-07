package org.example.diploma_jwt.models.usable.item_data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.example.diploma_jwt.models.Settings;
import org.example.diploma_jwt.models.usable.ManyType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "t_manyitemsform")
@Data
public class ManyItemsForm {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JsonIgnore
    @Transient
    private ManyType type;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "many_items_form_articles", joinColumns = @JoinColumn(name = "many_items_form_id"))
    @Column(name = "article")
    private List<String> articles = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "many_items_form_id")
    private List<ArticleAndCompanyForm> articleAndCompany = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "many_items_form_codes", joinColumns = @JoinColumn(name = "many_items_form_id"))
    @Column(name = "code")
    private List<String> codes = new ArrayList<>();

    @JsonIgnore
    @OneToOne(mappedBy = "blackListForm", cascade = CascadeType.ALL)
    private Settings settings;
}
