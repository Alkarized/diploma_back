package org.example.diploma_jwt.utils;

import org.example.diploma_jwt.models.*;
import org.example.diploma_jwt.models.usable.ParsedExcelData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Async;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OzonUtils {

    static final String ITEMS_DATA_URL = "https://api-seller.ozon.ru/v2/product/info";
    static final String ITEMS_IDS_URL = "https://api-seller.ozon.ru/v2/product/list";
    static final String ITEMS_PRICE_URL = "https://api-seller.ozon.ru/v1/product/import/prices";

    public static List<Item> getAllItems(Ozon ozon, Settings settings){
        List<Item> items = new ArrayList<Item>();


        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Client-Id", String.valueOf(ozon.getClientID()));
        headers.put("Api-Key", ozon.getToken());

        JSONArray responseIds = HttpRequestUtil.sendPostRequest(ITEMS_IDS_URL, "{}", "application/json", headers).getJSONObject("result").getJSONArray("items");

        List<Long> ids = new ArrayList<>();

        for(int i=0; i<responseIds.length(); i++){
            JSONObject now = responseIds.getJSONObject(i);
            ids.add(now.getLong("product_id"));
        }

        String sendData = new JSONArray(ids).toString();

        JSONArray itemData = HttpRequestUtil.sendPostRequest(ITEMS_DATA_URL, new JSONObject("{product_id:" + sendData + "}").toString(), "application/json", headers).getJSONObject("result").getJSONArray("items");

        for (int i = 0; i < itemData.length(); i++) {
            JSONObject now = responseIds.getJSONObject(i);
            Item item = new Item();

            item.setOldPrice(now.getDouble("price"));
            item.setItemCompanyID(now.getLong("id"));
            item.setTitle(now.getString("name"));
            item.getComplexItem().setArticleFieldForm(now.getString("sku"));
            item.getComplexItem().setCodeForm(now.getString("sku"));

            items.add(item);
        }

        return items;


    }

    public static Boolean updateOzonItemsPrice(Ozon ozon, Settings settings, List<Item> toUpdate){
        Boolean ans;

        JSONArray itemsArray = new JSONArray();

        for(Item item : toUpdate){
            JSONObject itemData = new JSONObject();
            itemData.append("price", item.getPrice());
            itemData.append("product_id", item.getItemCompanyID());
            itemData.append("old_price", 0);

            itemsArray.put(itemData);
        }

        JSONObject toSend = new JSONObject("{items:" + itemsArray.toString() + "}");

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Client-Id", String.valueOf(ozon.getClientID()));
        headers.put("Api-Key", ozon.getToken());

        return HttpRequestUtil.sendPostRequest(ITEMS_PRICE_URL, toSend.toString(), "application/json", headers) != null;


    }


}
