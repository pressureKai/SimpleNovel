package com.simplynovel.zekai.simplynovel.utils;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by 15082 on 2018/9/5.
 */

public class HttpUtils {

    public static void sendOkHttpRequest(String address, RequestBody requestBody, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .addHeader("user-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

}
