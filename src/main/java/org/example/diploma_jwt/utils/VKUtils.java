package org.example.diploma_jwt.utils;

import lombok.SneakyThrows;
import org.example.diploma_jwt.models.VK;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class VKUtils {
    private final static String API_URL = "https://api.vk.com/method/market";
    private final static String MARKET_GET = ".get?";

    public static void getAllVKItems(VK vk) {
        String url = API_URL + MARKET_GET + paramsString(vk);

        JSONObject allItems = HttpRequestUtil.getJsonFromGetRequest(url);

        if (allItems != null) {
            JSONArray jsonArray = allItems.getJSONObject("response").getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject vkItem = jsonArray.getJSONObject(i);

                String desc = vkItem.getString("description");
                Long itemVKID = vkItem.getLong("id");
                Double price = vkItem.getJSONObject("price").getDouble("amount");
                String title = vkItem.getString("title");
                String article = vkItem.getString("sku");


            }
        }

    }

    private static String paramsString(VK vk) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("owner_id", "-" + vk.getClubID());
        parameters.put("access_token", vk.getToken());
        parameters.put("v", "5.199");

        return HttpRequestUtil.getParamsString(parameters);
    }
}
