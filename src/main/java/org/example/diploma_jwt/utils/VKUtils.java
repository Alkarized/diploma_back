package org.example.diploma_jwt.utils;

import lombok.extern.slf4j.Slf4j;
import org.example.diploma_jwt.models.Item;
import org.example.diploma_jwt.models.Settings;
import org.example.diploma_jwt.models.VK;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class VKUtils {
    private final static String API_URL = "https://api.vk.com/method/market";
    private final static String MARKET_GET = ".get?";
    private final static String MARKET_EDIT = ".edit?";

    public static List<Item> getAllVKItems(VK vk, Settings settings) {
        String url = API_URL + MARKET_GET + paramsString(vk);
        List<Item> itemGot = new ArrayList<>();
        JSONObject allItems = HttpRequestUtil.getJsonFromGetRequest(url);

        if (allItems != null) {
            JSONArray jsonArray = allItems.getJSONObject("response").getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject vkItem = jsonArray.getJSONObject(i);
                Item item = new Item();

                String desc = vkItem.getString("description");
                Long itemVKID = vkItem.getLong("id");
                Double price = vkItem.getJSONObject("price").getDouble("amount") / 100; //todo check
                String title = vkItem.getString("title");
                String article = vkItem.getString("sku");

                item.setItemCompanyID(itemVKID);
                item.setTitle(title);
                item.setOldPrice(price);

                if (!article.equals(settings.getNoneField())){
                    item.getComplexItem().setArticleFieldForm(article);
                }

                DescParser.parseDesc(desc, item.getComplexItem());
                itemGot.add(item);
            }
        }

        return itemGot;

    }

    public static List<Boolean> updateVKItems(VK vk, Settings settings, List<Item> toUpdate) {
        List<Boolean> updatedItems = new ArrayList<>();
        for (Item item : toUpdate) {
            HashMap<String, String> params = new HashMap<>();
            params.put("item_id", String.valueOf(item.getItemCompanyID()));
            params.put("price", String.valueOf(item.getPrice()));
            params.put("stock_amount", "-1");
//            if (item.getPrice() < item.getOldPrice()){
//                params.put("old_price", String.valueOf(item.getOldPrice()));
//            } else {
//                params.put("old_price", String.valueOf(""));
//            }
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String url = API_URL + MARKET_EDIT + paramsString(vk, params);

            JSONObject result = HttpRequestUtil.getJsonFromGetRequest(url);

            log.info("params string: {}", params);
            log.info("price updated with {}", result);

            if (result != null && result.getInt("response") == 1){
                updatedItems.add(true);
            } else {
                updatedItems.add(false);
            }

        }


        return updatedItems;
    }


    private static String paramsString(VK vk) {
        return paramsString(vk, null);
    }

    private static String paramsString(VK vk, Map<String, String> parameters) {
        if (parameters == null){
            parameters = new HashMap<>();
        }

        parameters.put("owner_id", "-" + vk.getClubID());
        parameters.put("access_token", vk.getToken());
        parameters.put("v", "5.199");

        return HttpRequestUtil.getParamsString(parameters);
    }
}
