package org.example.diploma_jwt;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.example.diploma_jwt.antlr.ArticleEvaluator;
import org.example.diploma_jwt.antlr.generated.ArticlesLexer;
import org.example.diploma_jwt.antlr.generated.ArticlesParser;
import org.example.diploma_jwt.models.Excel;
import org.example.diploma_jwt.models.Item;
import org.example.diploma_jwt.models.Settings;
import org.example.diploma_jwt.models.usable.CheckOrder;
import org.example.diploma_jwt.models.usable.ParsedExcelData;
import org.example.diploma_jwt.models.usable.item_data.ComplexItem;
import org.example.diploma_jwt.playload.request.DataRequest;
import org.example.diploma_jwt.utils.ExcelDataParser;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExcelDataParserTest {
    @Test
    void testParseDataWithEmptyArticle() {
        List<List<String>> data = Arrays.asList(
                Arrays.asList("Артикул", "Внутренний код", "Фирма", "Цена", "Название"),
                Arrays.asList("None", "123", "Компания1", "100", "Название1")
        );

        Settings settings = new Settings();
        Excel excel = new Excel();
        excel.setArticle("Артикул");
        excel.setCode("Внутренний код");
        excel.setCompany("Фирма");
        excel.setPrice("Цена");
        excel.setTitle("Название");
        settings.setExcel(excel);

        ParsedExcelData result = ExcelDataParser.parseData(data, settings);

        assertEquals(1, result.getItemsWithEmptyArticle().size());
        assertEquals("123", result.getItemsWithEmptyArticle().get(0).getCode());
    }

    @Test
    void testParseDataWithDuplicateArticle() {
        List<List<String>> data = Arrays.asList(
                Arrays.asList("Артикул", "Внутренний код", "Фирма", "Цена", "Название"),
                Arrays.asList("ABC123", "123", "Компания1", "100", "Название1"),
                Arrays.asList("ABC123", "124", "Компания2", "200", "Название2")
        );

        Settings settings = new Settings();
        Excel excel = new Excel();
        excel.setArticle("Артикул");
        excel.setCode("Внутренний код");
        excel.setCompany("Фирма");
        excel.setPrice("Цена");
        excel.setTitle("Название");
        settings.setExcel(excel);

        ParsedExcelData result = ExcelDataParser.parseData(data, settings);

        assertEquals(2, result.getItemsWithSameArticle().size());
        assertEquals("123", result.getItemsWithSameArticle().get(0).getCode());
        assertEquals("124", result.getItemsWithSameArticle().get(1).getCode());
    }

    @Test
    void testParseApiData() {
        DataRequest dataRequest = new DataRequest();
        DataRequest.LocalItem item1 = new DataRequest.LocalItem(100.0, "ABC123", "123", "Компания1", "Название1");
        DataRequest.LocalItem item2 = new DataRequest.LocalItem(200.0, "None", "124", "Компания2", "Название2");
        dataRequest.setItems(Arrays.asList(item1, item2));

        ParsedExcelData result = ExcelDataParser.parseApiData(dataRequest);

        assertEquals(1, result.getItemsWithEmptyArticle().size());
        assertEquals("124", result.getItemsWithEmptyArticle().get(0).getCode());
    }

    @Test
    void testCompareItemsWithNullComplex() {
        Item item = new Item();
        item.setComplexItem(new ComplexItem());

        ParsedExcelData data = new ParsedExcelData();
        Settings settings = new Settings();

        boolean result = CheckOrder.COMPLEX.compareItems(item, data, settings);

        assertFalse(result);
    }

}
