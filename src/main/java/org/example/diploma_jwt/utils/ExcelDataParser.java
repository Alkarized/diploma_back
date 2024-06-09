package org.example.diploma_jwt.utils;

import lombok.extern.slf4j.Slf4j;
import org.example.diploma_jwt.models.Excel;
import org.example.diploma_jwt.models.Item;
import org.example.diploma_jwt.models.Settings;
import org.example.diploma_jwt.models.usable.ParsedExcelData;
import org.example.diploma_jwt.playload.request.DataRequest;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class ExcelDataParser {
    private static void forIteration(Item tmpItem, ParsedExcelData parsedExcelData){

        //log.info("item got: {}", tmpItem);

        // --------------------------- Арткул
        if (tmpItem.getArticle().equals("None")) { // Пустой артикул
            parsedExcelData.getItemsWithEmptyArticle().add(tmpItem);
        } else {
            if (parsedExcelData.getNonEmptyAndSameArticle().containsKey(tmpItem.getArticle())) { //Одинаковый артикул
                parsedExcelData.getItemsWithSameArticle().add(tmpItem);
                parsedExcelData.getItemsWithSameArticle().add(parsedExcelData.getNonEmptyAndSameArticle().get(tmpItem.getArticle()));
                parsedExcelData.getNonEmptyAndSameArticle().remove(tmpItem.getArticle());
            } else {
                parsedExcelData.getNonEmptyAndSameArticle().put(tmpItem.getArticle(), tmpItem);
            }
        }

        // --------------------------- Внутренний код

        if (tmpItem.getCode().equals("None")) { // Пустой код
            parsedExcelData.getItemsWithEmptyCode().add(tmpItem);
        } else {
            if (parsedExcelData.getNonEmptyAndSameCode().containsKey(tmpItem.getCode())) { // Одинаковый код
                parsedExcelData.getItemsWithSameCode().add(tmpItem);
                parsedExcelData.getItemsWithSameCode().add(parsedExcelData.getNonEmptyAndSameCode().get(tmpItem.getCode()));
                parsedExcelData.getNonEmptyAndSameCode().remove(tmpItem.getCode());
            } else {
                parsedExcelData.getNonEmptyAndSameCode().put(tmpItem.getCode(), tmpItem);
            }
        }

        // --------------------------- Фирма

        if (tmpItem.getCompany().equals("None")) { // Пустая фирма
            parsedExcelData.getItemsWithEmptyCompany().add(tmpItem);
        } else {
            if (parsedExcelData.getNonEmptyAndSameArticleCompany().containsKey(tmpItem.getArticle() + " " + tmpItem.getCompany())) { // Одинаковые Артикул + фирма
                parsedExcelData.getItemsWithSameArticleAndCompany().add(tmpItem);
                parsedExcelData.getItemsWithSameArticleAndCompany().add(parsedExcelData.getNonEmptyAndSameArticleCompany().get(tmpItem.getArticle() + " " + tmpItem.getCompany()));
                parsedExcelData.getNonEmptyAndSameArticleCompany().remove(tmpItem.getArticle() + " " + tmpItem.getCompany());
            } else {
                parsedExcelData.getNonEmptyAndSameArticleCompany().put(tmpItem.getArticle() + " " + tmpItem.getCompany(), tmpItem);
            }
        }
    }

    public static ParsedExcelData parseData(List<List<String>> data, Settings settings) {
        List<Integer> indexes = getListOfIndexes(data.get(0), settings.getExcel());

        //log.info("got list of indexes: {}", indexes);

        ParsedExcelData parsedExcelData = new ParsedExcelData();

        for (int i = 1; i < data.size(); i++) {
            List<String> row = data.get(i);
            Item tmpItem = makeItemFromRow(indexes, row);
            tmpItem.setRowNum(i);

            forIteration(tmpItem, parsedExcelData);

        }

        return parsedExcelData;

    }

    /*
        0 - Артикул;
        1 - Внутренний код
        2 - Фирма
        3 - Цена
        4 - Название
     */
    private static List<Integer> getListOfIndexes(List<String> headers, Excel excel) {
        List<Integer> indexes = new ArrayList<>();

        indexes.add(headers.indexOf(excel.getArticle()));
        indexes.add(headers.indexOf(excel.getCode()));
        indexes.add(headers.indexOf(excel.getCompany()));
        indexes.add(headers.indexOf(excel.getPrice()));
        indexes.add(headers.indexOf(excel.getTitle()));

        return indexes;
    }

    private static Item makeItemFromRow(List<Integer> indexes, List<String> row) {
        Item item = new Item();

        item.setArticle(row.get(indexes.get(0)).strip());
        item.setCode(row.get(indexes.get(1)).strip());
        item.setCompany(row.get(indexes.get(2)).strip());
        if (!row.get(indexes.get(3)).equals("None")){
            item.setPrice(Double.valueOf(row.get(indexes.get(3))));
        }
        item.setTitle(row.get(indexes.get(4)).strip());

        return item;
    }

    public static ParsedExcelData parseApiData(DataRequest dataRequest){
        ParsedExcelData parsedExcelData = new ParsedExcelData();

        List<DataRequest.LocalItem> items = dataRequest.getItems();

        for (int i = 1; i < items.size(); i++) {
            Item tmpItem = makeItemFromLocalItem(items.get(i));
            forIteration(tmpItem, parsedExcelData);

        }

        return parsedExcelData;
    }

    private static Item makeItemFromLocalItem(DataRequest.LocalItem localItem) {
        Item item = new Item();

        item.setArticle(localItem.getArticle().strip());
        item.setCode(localItem.getCode().strip());
        item.setCompany(localItem.getCompany().strip());
        item.setPrice(localItem.getPrice());
        item.setTitle(localItem.getTitle());

        return item;
    }
}
