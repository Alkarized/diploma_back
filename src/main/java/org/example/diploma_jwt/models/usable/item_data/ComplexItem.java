package org.example.diploma_jwt.models.usable.item_data;

import lombok.Data;

@Data
public class ComplexItem {
    private String articleFieldForm = null;
    private ArticleAndCompanyForm articleAndCompanyForm = null;
    private String codeForm = null;
    private String  articleForm = null;
    private ManyItemsForm manyItemsForm = null;
    private String complexForm = null;

}
