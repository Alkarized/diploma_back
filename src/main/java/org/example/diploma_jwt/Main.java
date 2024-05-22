package org.example.diploma_jwt;

import org.example.diploma_jwt.models.Item;
import org.example.diploma_jwt.models.Settings;
import org.example.diploma_jwt.utils.BlackListParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String url = "https://www.avito.ru/sankt-peterburg/zapchasti_i_aksessuary/vkladyshi_korennye_std_chevrolet_cobalt_ravon_r4_2243370531";

        String article = "";
        String company = "";
        String description = "";

        try {
            Document document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36")
                    .header("Accept-Language", "*")
                    .ignoreHttpErrors(true)
                    .get();

            description= document.select("div[data-block-name=ItemDescription]").first().text();

            Element data =  document.select("div[data-block-name=ItemParams]").first();
            JSONObject allData = new JSONObject(data.attr("data-props").replaceAll("&quot;", "\""));
            JSONArray tmpArray = allData.getJSONArray("items");

            for (int i = 0; i < tmpArray.length(); i++) {
                JSONObject now = tmpArray.getJSONObject(i);
                if (now.getInt("attributeId") == 110548){
                    company = now.getString("description");
                } else if (now.getInt("attributeId") == 110057){
                    article = now.getString("description");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
