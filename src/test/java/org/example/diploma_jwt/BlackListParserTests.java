package org.example.diploma_jwt;

import org.example.diploma_jwt.models.usable.item_data.ArticleAndCompanyForm;
import org.example.diploma_jwt.models.usable.item_data.ManyItemsForm;
import org.example.diploma_jwt.utils.BlackListParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BlackListParserTests {
    @Test
    public void testParseListWithArticles() {
        String input = """
                article1
                article2
                """;

        ManyItemsForm result = BlackListParser.parseList(input);

        assertEquals(2, result.getArticles().size());
        assertTrue(result.getArticles().contains("article1"));
        assertTrue(result.getArticles().contains("article2"));
    }

    @Test
    public void testParseListWithCodes() {
        String input = """
                [code1]
                [code2]
                """;

        ManyItemsForm result = BlackListParser.parseList(input);

        assertEquals(2, result.getCodes().size());
        assertTrue(result.getCodes().contains("code1"));
        assertTrue(result.getCodes().contains("code2"));
    }

    @Test
    public void testParseListWithArticleAndCompany() {
        String input = """
                article1 (company1)
                article2 (company2)
                """;

        ManyItemsForm result = BlackListParser.parseList(input);

        assertEquals(2, result.getArticleAndCompany().size());
        assertTrue(result.getArticleAndCompany().contains(new ArticleAndCompanyForm("article1", "company1")));
        assertTrue(result.getArticleAndCompany().contains(new ArticleAndCompanyForm("article2", "company2")));
    }

    @Test
    public void testParseListWithMixedInput() {
        String input = """
                article1
                [code1]
                article2 (company2)
                """;

        ManyItemsForm result = BlackListParser.parseList(input);

        assertEquals(1, result.getArticles().size());
        assertTrue(result.getArticles().contains("article1"));

        assertEquals(1, result.getCodes().size());
        assertTrue(result.getCodes().contains("code1"));

        assertEquals(1, result.getArticleAndCompany().size());
        assertTrue(result.getArticleAndCompany().contains(new ArticleAndCompanyForm("article2", "company2")));
    }

    @Test
    public void testParseListWithComments() {
        String input = """
                article1      |    comment1
                [code1]     |    comment2
                article2           (company2) | comment3""";

        ManyItemsForm result = BlackListParser.parseList(input);

        assertEquals(1, result.getArticles().size());
        assertTrue(result.getArticles().contains("article1"));

        assertEquals(1, result.getCodes().size());
        assertTrue(result.getCodes().contains("code1"));

        assertEquals(1, result.getArticleAndCompany().size());
        assertTrue(result.getArticleAndCompany().contains(new ArticleAndCompanyForm("article2", "company2")));
    }
}
