package org.example.diploma_jwt.models.usable;

import lombok.*;
import org.example.diploma_jwt.models.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParsedExcelData {
    List<Item> itemsWithEmptyArticle;
    List<Item> itemsWithSameArticle;
    HashMap<String, Item> nonEmptyAndSameArticle;

    List<Item> itemsWithEmptyCode;
    List<Item> itemsWithSameCode;
    HashMap<String, Item> nonEmptyAndSameCode;

    List<Item> itemsWithEmptyCompany;
    List<Item> itemsWithSameArticleAndCompany;
    HashMap<String, Item> nonEmptyAndSameArticleCompany;
}
