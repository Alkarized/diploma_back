package org.example.diploma_jwt.utils;

import org.example.diploma_jwt.models.usable.ManyType;
import org.example.diploma_jwt.models.usable.item_data.ArticleAndCompanyForm;
import org.example.diploma_jwt.models.usable.item_data.ComplexItem;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DescParser {
    final static String ARTICLE_REGEX = "(?m)^(Артикул: *(?<article>[\\wА-я-. ]+[\\wА-я-.]) *(\\| *(?<comment>.*))?)$";
    final static String ARTICLE_FIRM_REGEX = "(?m)^(Артикул: *(?<article>[\\wА-я-. ]+[\\wА-я-.])( *\\((?<firm>[\\wА-я-. ]+[\\wА-я-.])\\)) *(\\| *(?<comment>.*))?)$";
    final static String FORMULA_REGEX = "(?m)^(Формула: *(?<formula>.+))$";
    final static String MANY_ARTICLES_REGEX = "(?m)^(Артикулы: *(\\((?<type>min|max|mean|sum)\\) *)?[\r\n\f](?<articles>[\\S\\s]+?);)";
    final static String CODE_REGEX = "(?m)^Код: *\\[(?<code>[\\wА-я-. ]+[\\wА-я-.])]$";

    final static Pattern ARTICLE_PATTERN = Pattern.compile(ARTICLE_REGEX);
    final static Pattern ARTICLE_FIRM_PATTERN = Pattern.compile(ARTICLE_FIRM_REGEX);
    final static Pattern FORMULA_PATTERN = Pattern.compile(FORMULA_REGEX);
    final static Pattern MANY_ARTICLES_PATTERN = Pattern.compile(MANY_ARTICLES_REGEX);
    final static Pattern CODE_PATTERN = Pattern.compile(CODE_REGEX);

    public static void parseDesc(String desc, ComplexItem complexItem) {
        final Matcher articleMatcher = ARTICLE_PATTERN.matcher(desc);
        final Matcher articleFirmMatcher = ARTICLE_FIRM_PATTERN.matcher(desc);
        final Matcher formulaMatcher = FORMULA_PATTERN.matcher(desc);
        final Matcher manyArticlesMatcher = MANY_ARTICLES_PATTERN.matcher(desc);
        final Matcher codeMatcher = CODE_PATTERN.matcher(desc);

        if (articleMatcher.find()) {
            if (articleMatcher.group("article") != null) {
                complexItem.setArticleForm(articleMatcher.group("article"));
            }
        }

        if(articleFirmMatcher.find()) {
            if (articleFirmMatcher.group("firm") != null) {
                complexItem.setArticleAndCompanyForm(new ArticleAndCompanyForm(articleFirmMatcher.group("article"),  articleFirmMatcher.group("firm")));
            }
        }

        if (codeMatcher.find()){
            if (codeMatcher.group("code") != null) {
                complexItem.setCodeForm(codeMatcher.group("code"));
            }
        }

        if (formulaMatcher.find()){
            if (formulaMatcher.group("formula") != null) {
                complexItem.setComplexForm(formulaMatcher.group("formula"));
            }
        }

        if (manyArticlesMatcher.find()){
            if (manyArticlesMatcher.group("articles") != null) {
                complexItem.setManyItemsForm(BlackListParser.parseList(manyArticlesMatcher.group("articles")));
                if (manyArticlesMatcher.group("type") != null){
                    complexItem.getManyItemsForm().setType(ManyType.valueOf(manyArticlesMatcher.group("type").toUpperCase()));
                }
            }
        }
    }
}
