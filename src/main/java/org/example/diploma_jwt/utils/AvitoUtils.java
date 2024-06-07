package org.example.diploma_jwt.utils;

import org.example.diploma_jwt.models.Avito;
import org.example.diploma_jwt.models.Item;
import org.example.diploma_jwt.models.Settings;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class AvitoUtils {
    private static final String URL_TOKEN = "https://api.avito.ru/token/";
    private static final String URL_ITEMS = "https://api.avito.ru/core/v1/items?";
    private static final String URL_ITEM_UPDATE_PART1 = "https://api.avito.ru/core/v1/items/";
    private static final String URL_ITEM_UPDATE_PART2 = "/update_price";

    private static Boolean getItemFromUrl(Item item, String url, Settings settings){
        String article = "";
        String company = "";
        String description = "";

        Map<String, String> headers = getStringStringMap();
        try {
            Document document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36")
                    .headers(headers)
                    .ignoreHttpErrors(false)
                    .get();

            // price = Double.parseDouble(document.select("span[data-marker=item-view/item-price]").first().attr("content"));


            JSONObject descObject = new JSONObject(document.select("div[data-block-name=ItemDescription]").first().attr("data-props"));
            description = descObject.getString("description").replace("<p>", "").replace("</p>", "\n").replace("<br>", "\n");

            Element data =  document.select("div[data-block-name=ItemParams]").first();
            JSONObject allData = new JSONObject(data.attr("data-props").replaceAll("&quot;", "\""));
            JSONArray tmpArray = allData.getJSONArray("items");

            for (int i = 0; i < tmpArray.length(); i++) {
                JSONObject now = tmpArray.getJSONObject(i);
                if (now.getInt("attributeId") == 110548){
                    company = now.getString("description");
                    item.setCompany(company);
                } else if (now.getInt("attributeId") == 110057){
                    article = now.getString("description");
                    if (!article.equals(settings.getNoneField())){
                        item.setArticle(article);
                        item.getComplexItem().setArticleFieldForm(article);
                    }
                }
            }

            DescParser.parseDesc(description, item.getComplexItem());

            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;

    }

    private static Map<String, String> getStringStringMap() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
        headers.put("Accept-Encoding", "gzip, deflate, br, zstd");
        headers.put("Accept-Language", "en-US,en;q=0.9,ru;q=0.8");
        headers.put("Cookie", "uri=sankt-peterburg; srv_id=65jxkRuk7ecjl0HH.w57mHy6EeoJInhIXoKHa2LxDDv1QYkuD_EQ2Ab3w9B4OEMbGZipkmZNIsOH19NE=.nwOJhiaBv8mAHBAqqcQVcnaWjG6Obups8uF4_eaH9tk=.web; u=32e986hx.1eo5j9.1giqmrj66wy00; buyer_popup_location=0; buyer_laas_location=653240; __zzatw-avito=MDA0dBA=Fz2+aQ==; __zzatw-avito=MDA0dBA=Fz2+aQ==; auth=1; cfidsw-avito=hHtE9Zxb0eYO7XKIDmMhBormXQabdLOii2gikTgajKST80gkNeMtIlGz0pGfIxC+JmsipIK+/QwHWfVM3jumf6AAw6FY8iU+hMZvy/fKJibFnRFV0PnXr68BRJCMTlzQswlQi+pm0dfE4Ssa3RKVPlajiowIB1ueoe9pSg==; cfidsw-avito=hHtE9Zxb0eYO7XKIDmMhBormXQabdLOii2gikTgajKST80gkNeMtIlGz0pGfIxC+JmsipIK+/QwHWfVM3jumf6AAw6FY8iU+hMZvy/fKJibFnRFV0PnXr68BRJCMTlzQswlQi+pm0dfE4Ssa3RKVPlajiowIB1ueoe9pSg==; sessid=a38e4b3f60376d44253e19c1ff1f00ca.1715783466; cfidsw-avito=8ieaKoDWor5b4TszdL3H788I0BURptNFx1l9GBtLP+HpLh0xij0g6AULx73ay+6nijG6a8aGE9+fH1tE5wNk/wqTd/0h4XaN10FHdQeadzvrCFHb5TOghSNLwyRZPTHZOINNFeYedaURkzbgiSA171COdOsgkZRfMX7bAQ==; SEARCH_HISTORY_IDS=3%2C; buyer_location_id=653240; v=1717677553; f=5.0c4f4b6d233fb90636b4dd61b04726f147e1eada7172e06c47e1eada7172e06c47e1eada7172e06c47e1eada7172e06cb59320d6eb6303c1b59320d6eb6303c1b59320d6eb6303c147e1eada7172e06c8a38e2c5b3e08b898a38e2c5b3e08b890df103df0c26013a7b0d53c7afc06d0b2ebf3cb6fd35a0ac0df103df0c26013a8b1472fe2f9ba6b9ad42d01242e34c7968e2978c700f15b635c155810a32f63d915ac1de0d034112f12b79bbb67ac37d46b8ae4e81acb9fae2415097439d4047fb0fb526bb39450a46b8ae4e81acb9fa34d62295fceb188dd99271d186dc1cd03de19da9ed218fe2d50b96489ab264edd50b96489ab264edd50b96489ab264ed46b8ae4e81acb9fa38e6a683f47425a8352c31daf983fa077a7b6c33f74d335c84df0fd22b85d35fc34238d0bd261b67cb5ec09fa5c57cfad44c4f58a3d78080550369b42c8b7eb717c7721dca45217b645c65a75089a0c3911f20c5a13b6313e2415097439d404746b8ae4e81acb9fa786047a80c779d5146b8ae4e81acb9faf52baed126e582ae4938c41efda3055a2da10fb74cac1eabb3ae333f3b35fe91de6c39666ae9b0d7312f8fecc8ca5e543486a07687daa291; ft=\"IbuEx77/uMZOdthYkTknyVZ8JjXRy0Kr/xiP/CwOZFPjpgKhgv1OpG3p0kkQeYa4jbzuPkR28Yu/Bmtv/FSBgrXTQiJvAGGF0mGpNd+a8xc67j6HatAZE2XItEZroLNHfBT5MWhM4mM7GP/x+ncN+KjsPsTXvucGA/hLLZeJRYkz7dPtWQOLHR7/DlOV3j5q\"; dfp_group=62; sx=H4sIAAAAAAACA4uOBQApu0wNAgAAAA%3D%3D; gMltIuegZN2COuSe=EOFGWsm50bhh17prLqaIgdir1V0kgrvN; cartCounter=0; buyer_from_page=profile-item");
        return headers;
    }

    public static List<Item> getAllItems(Avito avito, Settings settings, String token){
        List<Item> itemParsed = new ArrayList<>();

        //int page = 25;

        int page = 1;

        while (true){
            Map<String, String> params = new HashMap<>();
            //params.put("per_page", "3");
            params.put("per_page", "100");
            params.put("page", String.valueOf(page));

            String URL = URL_ITEMS + paramsString(params);

            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + token);

            JSONObject object = HttpRequestUtil.getJsonFromGetRequest(URL, headers);
            JSONArray array = object.getJSONArray("resources");

            if (array.isEmpty()){
                return itemParsed;
            }

            for(int i = 0; i < array.length(); i++){
                Item item = new Item();
                JSONObject resource = array.getJSONObject(i);
                String tmpUrl = resource.getString("url");

                item.setTitle(resource.getString("title"));
                item.setPrice(resource.getDouble("price"));
                item.setItemCompanyID(resource.getLong("id"));

                Boolean got = getItemFromUrl(item, tmpUrl, settings);
                if (got){
                    itemParsed.add(item);
                }

                try {
                    Thread.sleep(getRandomNumber());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }

            page++;

            //return itemParsed;

        }

    }

    public static List<Boolean> updateAvitoItemsPrice(Avito avito, Settings settings, String token, List<Item> toUpdate){
        List<Boolean> updatedItems = new ArrayList<>();

        for (Item item : toUpdate){
            String url = URL_ITEM_UPDATE_PART1 + item.getItemCompanyID() + URL_ITEM_UPDATE_PART2;
            JSONObject price = new JSONObject("{price:" + item.getPrice().intValue() + "}");

            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + token);

            JSONObject response = HttpRequestUtil.sendPostRequest(url, price.toString(), "application/json", headers);
            System.out.println(response);

            if(response != null && response.has("result")){
                updatedItems.add(true);
            } else {
                updatedItems.add(false);
            }

            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

        return updatedItems;

    }

    public static String getToken(Avito avito){
        String requestBody = Map.of(
                        "grant_type", "client_credentials",
                        "client_id", avito.getClientId(),
                        "client_secret", avito.getClientSecret()
                ).entrySet().stream()
                .map(entry -> URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) + "=" + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        JSONObject jsonObject = HttpRequestUtil.sendPostRequest(URL_TOKEN, requestBody);
        return jsonObject.getString("access_token");
    }

    private static String paramsString(Map<String, String> parameters) {
        return HttpRequestUtil.getParamsString(parameters);
    }

    private static long getRandomNumber() {
        return (long) (((Math.random() * (12 - 7)) + 7) * 1000);
    }
}

