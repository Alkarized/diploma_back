package org.example.diploma_jwt.utils;

import org.example.diploma_jwt.models.Excel;
import org.example.diploma_jwt.models.Item;
import org.example.diploma_jwt.models.Settings;
import org.example.diploma_jwt.models.usable.ParsedExcelData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExcelDataParser {
    public static ParsedExcelData parseData(List<List<String>> data, Settings settings) {
        List<Integer> indexes = getListOfIndexes(data.get(0), settings.getExcel());

        List<Item> itemsWithEmptyArticle = new ArrayList<>();
        List<Item> itemsWithSameArticle = new ArrayList<>();
        HashMap<String, Item> nonEmptyAndSameArticle = new HashMap<>();

        List<Item> itemsWithEmptyCode = new ArrayList<>();
        List<Item> itemsWithSameCode = new ArrayList<>();
        HashMap<String, Item> nonEmptyAndSameCode = new HashMap<>();

        List<Item> itemsWithEmptyCompany = new ArrayList<>();
        List<Item> itemsWithSameArticleAndCompany = new ArrayList<>();
        HashMap<String, Item> nonEmptyAndSameArticleCompany = new HashMap<>();

        for (int i = 1; i < data.size(); i++) {
            List<String> row = data.get(i);
            Item tmpItem = makeItemFromRow(indexes, row);
            tmpItem.setRowNum(i);

            // --------------------------- Арткул
            if (row.get(indexes.get(0)).equals("None")) { // Пустой артикул
                itemsWithEmptyArticle.add(tmpItem);
            } else {
                if (nonEmptyAndSameArticle.containsKey(row.get(0))) { //Одинаковый артикул
                    itemsWithSameArticle.add(tmpItem);
                    itemsWithSameArticle.add(nonEmptyAndSameArticle.get(row.get(0)));
                    nonEmptyAndSameArticle.remove(row.get(0));
                } else {
                    nonEmptyAndSameArticle.put(row.get(0), tmpItem);
                }
            }

            // --------------------------- Внутренний код

            if (row.get(indexes.get(1)).equals("None")) { // Пустой код
                itemsWithEmptyCode.add(tmpItem);
            } else {
                if (nonEmptyAndSameCode.containsKey(row.get(1))) { // Одинаковый код
                    itemsWithSameCode.add(tmpItem);
                    itemsWithSameCode.add(nonEmptyAndSameCode.get(row.get(1)));
                    nonEmptyAndSameCode.remove(row.get(1));
                } else {
                    nonEmptyAndSameCode.put(row.get(1), tmpItem);
                }
            }

            // --------------------------- Фирма

            if (row.get(indexes.get(2)).equals("None")) { // Пустая фирма
                itemsWithEmptyCompany.add(tmpItem);
            } else {
                if (nonEmptyAndSameArticleCompany.containsKey(row.get(0) + " " + row.get(2))) { // Одинаковые Артикул + фирма
                    itemsWithSameArticleAndCompany.add(tmpItem);
                    itemsWithSameArticleAndCompany.add(nonEmptyAndSameArticleCompany.get(row.get(0) + " " + row.get(2)));
                    nonEmptyAndSameArticleCompany.remove(row.get(1));
                } else {
                    nonEmptyAndSameArticleCompany.put(row.get(0) + " " + row.get(2), tmpItem);
                }
            }


        }

        return new ParsedExcelData(
                itemsWithEmptyArticle, itemsWithSameArticle, nonEmptyAndSameArticle,
                itemsWithEmptyCode, itemsWithSameCode, nonEmptyAndSameCode,
                itemsWithEmptyCompany, itemsWithSameArticleAndCompany, nonEmptyAndSameArticleCompany);

    }

    /*
        0 - Артикул;
        1 - Внутренний код
        2 - Фирма
        3 - Цена
        4 - Название
     */
    private static List<Integer> getListOfIndexes(List<String> headers, Excel excel) {
        List<Integer> indexes = new ArrayList<Integer>();

        indexes.add(headers.indexOf(excel.getArticle()));
        indexes.add(headers.indexOf(excel.getCode()));
        indexes.add(headers.indexOf(excel.getCompany()));
        indexes.add(headers.indexOf(excel.getPrice()));
        indexes.add(headers.indexOf(excel.getTitle()));

        return indexes;
    }

    private static Item makeItemFromRow(List<Integer> indexes, List<String> row) {
        Item item = new Item();

        item.setArticle(row.get(indexes.get(0)));
        item.setCode(row.get(indexes.get(1)));
        item.setCompany(row.get(indexes.get(2)));
        item.setPrice(Double.valueOf(row.get(indexes.get(3))));
        item.setTitle(row.get(indexes.get(4)));

        return item;
    }
}
