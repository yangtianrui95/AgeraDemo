package com.example.yangtianrui.doubanapitest;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.agera.Result;
import com.google.android.agera.Supplier;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yangtianrui on 16-5-29.
 * 根据关键字,提供书籍数据
 */
public class BooksSupplier implements Supplier<Result<List<Book>>> {
    private String key;
    // 用于建立连接
    private OkHttpClient client = new OkHttpClient();
    private Gson gson = new Gson();
    private static final String API_URL = "https://api.douban.com/v2/book/search";

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public BooksSupplier(String key) {
        this.key = key;
    }

    public BooksSupplier() {
    }


    private List<Book> getBooks() {
        Log.v("LOG", "getBooks");
        HttpUrl httpUrl = HttpUrl.parse(API_URL).newBuilder()
                // 设置查询参数
                .addQueryParameter("q", key)
                .addQueryParameter("start", "0")
                .addQueryParameter("count", "15")
                .build();
        Request request = new Request.Builder().url(httpUrl).build();
        try {
            // 建立连接
            Response response = client.newCall(request).execute();
            // 获取返回的Json
//            Log.v("LOG",response.body().toString());
            JSONObject jsonObject = new JSONObject(response.body().string());
            JSONArray jsonArray = jsonObject.optJSONArray("books");
            List<Book> list = gson.fromJson(jsonArray.toString(), new TypeToken<List<Book>>() {
            }.getType());
//            Log.v("LOG", list.toString());
            return list;
        } catch (Exception e) {
            Log.v("LOG","error in getBooks()"+e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    // 获取数据
    @NonNull
    @Override
    public Result<List<Book>> get() {
        List<Book> list = getBooks();
        if (list != null) {
            return Result.success(list);
        } else {
            return Result.failure();
        }
    }
}
