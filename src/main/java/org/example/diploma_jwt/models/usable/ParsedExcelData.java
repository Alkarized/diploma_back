package org.example.diploma_jwt.models.usable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.example.diploma_jwt.models.Item;
import org.example.diploma_jwt.models.Settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "t_parsedexceldata")
public class ParsedExcelData {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "parsed_data_id")
    private List<Item> itemsWithEmptyArticle = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "parsed_data_id")
    private List<Item> itemsWithSameArticle = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @MapKeyColumn(name = "key")
    @JoinTable(name = "parsed_data_non_empty_and_same_article",
            joinColumns = @JoinColumn(name = "parsed_data_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private Map<String, Item> nonEmptyAndSameArticle = new HashMap<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "parsed_data_id")
    private List<Item> itemsWithEmptyCode = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "parsed_data_id")
    private List<Item> itemsWithSameCode = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @MapKeyColumn(name = "key")
    @JoinTable(name = "parsed_data_non_empty_and_same_code",
            joinColumns = @JoinColumn(name = "parsed_data_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private Map<String, Item> nonEmptyAndSameCode = new HashMap<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "parsed_data_id")
    private List<Item> itemsWithEmptyCompany = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "parsed_data_id")
    private List<Item> itemsWithSameArticleAndCompany = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @MapKeyColumn(name = "key")
    @JoinTable(name = "parsed_data_non_empty_and_same_article_company",
            joinColumns = @JoinColumn(name = "parsed_data_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private Map<String, Item> nonEmptyAndSameArticleCompany = new HashMap<>();

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "settings_id", referencedColumnName = "id")
    private Settings settings;
}
