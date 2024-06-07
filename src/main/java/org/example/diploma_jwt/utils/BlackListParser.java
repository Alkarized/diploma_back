package org.example.diploma_jwt.utils;

import org.example.diploma_jwt.models.Settings;
import org.example.diploma_jwt.models.usable.item_data.ArticleAndCompanyForm;
import org.example.diploma_jwt.models.usable.item_data.ManyItemsForm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BlackListParser {

    private static final String ARTICLE_REGEX = "(?m)^ *(?<article>[\\wА-я-. ]+[\\wА-я-.]) *(\\| *(?<comment>.*))?$";
    private static final String CODE_REGEX = "(?m)^ *(\\[ *(?<code>[\\wА-я-. ]+[\\wА-я-.]) *]) *(\\| *(?<comment>.*))?$";
    private static final String COMPANY_REGEX = "(?m)^ *(?<article>[\\wА-я-. ]+[\\wА-я-.])( *\\((?<firm>[\\wА-я-. ]+[\\wА-я-.])\\)) *(\\| *(?<comment>.*))?$";

//    private static final String ARTICLE_REGEX = "^([A-zА-я0-9-. ]+)$";
//    private static final String CODE_REGEX = "^\\[(.*?)]$";
//    private static final String COMPANY_REGEX = "^([A-zА-я0-9-. ]+)\\((.*?)\\)$";

    private static final Pattern ARTICLE_PATTERN = Pattern.compile(ARTICLE_REGEX);
    private static final Pattern CODE_PATTERN = Pattern.compile(CODE_REGEX);
    private static final Pattern COMPANY_PATTERN = Pattern.compile(COMPANY_REGEX);

    public static ManyItemsForm parseList(String input) {
        ManyItemsForm items = new ManyItemsForm();

        Matcher articleMatcher = ARTICLE_PATTERN.matcher(input);
        Matcher codeMatcher = CODE_PATTERN.matcher(input);
        Matcher companyMatcher = COMPANY_PATTERN.matcher(input);

        while(articleMatcher.find()){
            items.getArticles().add(articleMatcher.group("article").strip());
        }

        while(codeMatcher.find()){
            items.getCodes().add(codeMatcher.group("code").strip());
        }

        while(companyMatcher.find()){
            items.getArticleAndCompany().add(new ArticleAndCompanyForm(companyMatcher.group("article").strip(), companyMatcher.group("firm").strip()));
        }

//        String[] lines = input.split("\\r?\\n");

//        for (String line : lines) {
//            String article = null;
//            String code = null;
//            String company = null;
//
//            Matcher articleMatcher = ARTICLE_PATTERN.matcher(line);
//            Matcher codeMatcher = CODE_PATTERN.matcher(line);
//            Matcher companyMatcher = COMPANY_PATTERN.matcher(line);

//            if (codeMatcher.matches()) {
//                code = codeMatcher.group(1);
//            } else if (companyMatcher.matches()) {
//                company = companyMatcher.group(2);
//                article = companyMatcher.group(1).strip();
//            } else if (articleMatcher.matches()) {
//                article = articleMatcher.group(1);
//            }
//
//            if (article != null || code != null ) {
//                Item item = new Item();
//
//                item.setArticle(article);
//                item.setCode(code);
//                item.setCompany(company);
//                item.setSettings(settings);
//
//                items.add(item);
//            }
//    }
        return items;

    }
}
