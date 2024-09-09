package com.hill.api.rest;

import com.hill.web.core.PropertiesWeb;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TryApi {
    public static List<String> ozoneSearchTags(String req) {
        String respBody = null;

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"link\": \"/search/?text={value}&from_global=true\", \"query\": \"" + req + "\", \"widgetVersion\": 2}");
        Request request = new Request.Builder()
                .url(PropertiesWeb.getProperty("base.url") + "api/composer-api.bx/_action/v2/getSearchTapTags")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
            respBody = response.body().string();
        } catch (IOException | NullPointerException ignored) {
        }

        JSONObject obj = new JSONObject(respBody);
        JSONArray itemsJsonArray = obj.getJSONObject("data").getJSONArray("items");
        List<String> tags = new ArrayList<>();

        for (int i = 0; i < itemsJsonArray.length(); i++) {
            JSONObject item = itemsJsonArray.getJSONObject(i);
            String tag = item.getString("text");
            tags.add(tag);
        }
        return tags;
    }
}
