package org.example.diploma_jwt;

import org.example.diploma_jwt.models.usable.ManyType;
import org.example.diploma_jwt.models.usable.item_data.ArticleAndCompanyForm;
import org.example.diploma_jwt.models.usable.item_data.ComplexItem;
import org.example.diploma_jwt.utils.DescParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DescParserTest {
    @Test
    void testParseArticle() {
        String desc = "Артикул: ABC123";
        ComplexItem complexItem = new ComplexItem();
        DescParser.parseDesc(desc, complexItem);
        assertEquals("ABC123", complexItem.getArticleForm());
    }

    @Test
    void testParseArticleWithFirm() {
        String desc = "Артикул: ABC123 (FirmName)";
        ComplexItem complexItem = new ComplexItem();
        DescParser.parseDesc(desc, complexItem);
        ArticleAndCompanyForm articleAndCompanyForm = complexItem.getArticleAndCompanyForm();
        assertNotNull(articleAndCompanyForm);
        assertEquals("ABC123", articleAndCompanyForm.getArticle());
        assertEquals("FirmName", articleAndCompanyForm.getCompany());
    }

    @Test
    void testParseCode() {
        String desc = "Код: [XYZ789]";
        ComplexItem complexItem = new ComplexItem();
        DescParser.parseDesc(desc, complexItem);
        assertEquals("XYZ789", complexItem.getCodeForm());
    }

    @Test
    void testParseFormula() {
        String desc = "Формула: A + B = C";
        ComplexItem complexItem = new ComplexItem();
        DescParser.parseDesc(desc, complexItem);
        assertEquals("A + B = C", complexItem.getComplexForm());
    }

    @Test
    void testParseManyArticles() {
        String desc = "Артикулы: (min) \narticle1\narticle2\narticle3;";
        ComplexItem complexItem = new ComplexItem();
        DescParser.parseDesc(desc, complexItem);
        assertNotNull(complexItem.getManyItemsForm());
        assertEquals(ManyType.MIN, complexItem.getManyItemsForm().getType());
        assertTrue(complexItem.getManyItemsForm().getArticles().contains("article1"));
        assertTrue(complexItem.getManyItemsForm().getArticles().contains("article2"));
        assertTrue(complexItem.getManyItemsForm().getArticles().contains("article3"));
    }


    @Test
    void testParseInvalidDesc() {
        String desc = "Some invalid description";
        ComplexItem complexItem = new ComplexItem();
        DescParser.parseDesc(desc, complexItem);
        assertNull(complexItem.getArticleForm());
        assertNull(complexItem.getArticleAndCompanyForm());
        assertNull(complexItem.getCodeForm());
        assertNull(complexItem.getComplexForm());
        assertNull(complexItem.getManyItemsForm());
    }
}
