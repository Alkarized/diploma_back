package org.example.diploma_jwt.utils;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestUtil {

    public static JSONObject getJsonFromGetRequest(String url) {
        return getJsonFromGetRequest(url, null);

    }


    public static JSONObject getJsonFromGetRequest(String url, Map<String, String> headers) {
        try {
            HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
            httpClient.setRequestMethod("GET");
            httpClient.setRequestProperty("Content-Type", "application/json");

            if (headers != null){
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpClient.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            int responseCode = httpClient.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                return getJsonObject(httpClient);
            } else {
                httpClient.disconnect();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static JSONObject sendPostRequest(String url, String data) {
        return sendPostRequest(url, data, "application/x-www-form-urlencoded", null);
    }

    public static JSONObject sendPostRequest(String url, String data, String contentType, Map<String, String> headers) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", contentType) //application/x-www-form-urlencoded
                    .POST(HttpRequest.BodyPublishers.ofString(data));

            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder = builder.header(entry.getKey(), entry.getValue());
            }

            HttpRequest request = builder.build();


            HttpResponse<String> response = null;
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new JSONObject(response.body());
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }

    private static JSONObject getJsonObject(HttpURLConnection httpClient) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(httpClient.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        httpClient.disconnect();
        return new JSONObject(response.toString());
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
//        Map<String, String> parameters = new HashMap<>();
//        parameters.put("owner_id", "-211007573");
//        parameters.put("access_token", "vk1.a.VXzhkdCRRVDogNebzwE3Vvwb2Kc1H82bsXPnIxFz70ujIxA9maf1b8RTtfvZLyJZ6hk-P9WVwiILR-FINQSq57vj0f1nIA3vToRcwxmIYB3q8hjFiTgugYCVNaHZAi3ntrsYYv1bKDUPKrWl7btH_luFtCTQHfeNWMj-nYOI0aemfuY-e7VmIzfNXT_No0bP9po1M5MK9_FnW6EcKS4awg");
//        parameters.put("v", "5.199");
//        String url = "https://api.vk.com/method/market.get?" + getParamsString(parameters);
//
//        JSONObject object = getJsonFromGetRequest(url);
//        JSONArray jsonArray = object.getJSONObject("response").getJSONArray("items");

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
