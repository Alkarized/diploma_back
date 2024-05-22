package org.example.diploma_jwt.utils;

import org.example.diploma_jwt.models.Item;
import org.example.diploma_jwt.models.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BlackListParser {

    private static final String ARTICLE_REGEX = "^([A-zА-я0-9- ]+)$";
    private static final String CODE_REGEX = "^\\[(.*?)]$";
    private static final String COMPANY_REGEX = "^([A-zА-я0-9- ]+)\\((.*?)\\)$";

    private static final Pattern ARTICLE_PATTERN = Pattern.compile(ARTICLE_REGEX);
    private static final Pattern CODE_PATTERN = Pattern.compile(CODE_REGEX);
    private static final Pattern COMPANY_PATTERN = Pattern.compile(COMPANY_REGEX);

    public static List<Item> parseList(String input, Settings settings){
        List<Item> items = new ArrayList<Item>();

        String[] lines = input.split("\\r?\\n");

        for (String line : lines) {
            String article = null;
            String code = null;
            String company = null;

            Matcher articleMatcher = ARTICLE_PATTERN.matcher(line);
            Matcher codeMatcher = CODE_PATTERN.matcher(line);
            Matcher companyMatcher = COMPANY_PATTERN.matcher(line);

            if (codeMatcher.matches()) {
                code = codeMatcher.group(1);
            } else if (companyMatcher.matches()) {
                company = companyMatcher.group(2);
                article = companyMatcher.group(1).strip();
            } else if (articleMatcher.matches()) {
                article = articleMatcher.group(1);
            }

            if (article != null || code != null ) {
                Item item = new Item();

                item.setArticle(article);
                item.setCode(code);
                item.setCompany(company);
                item.setSettings(settings);

                items.add(item);
            }

        }

        return items;
    }
}
