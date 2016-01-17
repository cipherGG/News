package com.gg.news.engine;

import android.text.TextUtils;
import android.util.Log;

import com.gg.news.bean.Query;
import com.gg.news.bean.Word;
import com.gg.news.utils.HttpUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * author cipherGG
 * Created by Administrator on 2016/1/2.
 * describe
 */
public class News {

    public static ArrayList<String> getWords() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("key", HttpUtils.NEWS_KEY);

        String data = HttpUtils.getString(HttpUtils.NEWS_WORDS_PATH, map);

        if (TextUtils.isEmpty(data)) {
            return new ArrayList<>();
        } else {
            Gson gson = new Gson();
            Word word = gson.fromJson(data, Word.class);
            return word.result;
        }
    }

    public static List<Query.Result> getQuerys(String title) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("key", HttpUtils.NEWS_KEY);
        map.put("q", title);

        String data = HttpUtils.getString(HttpUtils.NEWS_QUERY_PATH, map);

        if (TextUtils.isEmpty(data)) {
            return new ArrayList<>();
        } else {
            Gson gson = new Gson();
            Query query = gson.fromJson(data, Query.class);

            if (query.result == null || query.result.size() == 0) {
                return new ArrayList<>();
            } else {
                return query.result;
            }
        }
    }
}
