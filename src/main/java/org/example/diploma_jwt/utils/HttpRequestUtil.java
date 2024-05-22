package org.example.diploma_jwt.utils;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestUtil {
    public static JSONObject getJsonFromGetRequest(String url) {
        try {
            HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
            httpClient.setRequestMethod("GET");

            httpClient.setRequestProperty("Content-Type", "application/json");
            int responseCode = httpClient.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(httpClient.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return new JSONObject(response.toString());

            } else {
                return null;
            }

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public static String getParamsString(Map<String, String> params) {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
            result.append("&");
        }

        String resultString = result.toString();
        return !resultString.isEmpty()
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("owner_id", "-211007573");
        parameters.put("access_token", "vk1.a.VXzhkdCRRVDogNebzwE3Vvwb2Kc1H82bsXPnIxFz70ujIxA9maf1b8RTtfvZLyJZ6hk-P9WVwiILR-FINQSq57vj0f1nIA3vToRcwxmIYB3q8hjFiTgugYCVNaHZAi3ntrsYYv1bKDUPKrWl7btH_luFtCTQHfeNWMj-nYOI0aemfuY-e7VmIzfNXT_No0bP9po1M5MK9_FnW6EcKS4awg");
        parameters.put("v", "5.199");
        String url = "https://api.vk.com/method/market.get?" + getParamsString(parameters);

        JSONObject object = getJsonFromGetRequest(url);
        JSONArray jsonArray = object.getJSONObject("response").getJSONArray("items");

//        for (int i = 0; i < jsonArray.length(); i++) {
//            JSONObject jsonObject = jsonArray.getJSONObject(i);
//            String desc = jsonObject.getString("description");
//            Long id = jsonObject.getLong("id");
//            Long price = jsonObject.getJSONObject("price").getLong("amount");
//            String title = jsonObject.getString("title");
//            String article = jsonObject.getString("sku");
//
//            System.out.println(desc + "\n" + id + "\n" + price + "\n" + title + "\n" + article);
//            System.out.println("------------------");
//        }
    }

}
