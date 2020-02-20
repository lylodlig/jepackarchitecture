package com.example.jepack_architecture.net.intercepter;

import com.google.gson.Gson;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

public class MockInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Gson gson = new Gson();
        Response response = null;
        Response.Builder responseBuilder = new Response.Builder()
                .code(200)
                .message("")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .addHeader("content-type", "application/json");
        Request request = chain.request();
        String url = request.url().toString();

        JSONObject object = new JSONObject();
        try {
            object.put("code", 0);
            object.put("message", "");
            object.put("data", "");

            if (url.contains("v1/signature")) {
                JSONArray array = new JSONArray();
                for (int i = 0; i < 6; i++) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("province", "四川省" + i);
                    jsonObject.put("city", "成都市" + i);
                    jsonObject.put("district", "武侯区" + i);
                    jsonObject.put("signature", "四川省减灾所" + i);
                    array.put(jsonObject);
                }
                object.put("data", array);
                responseBuilder.body(ResponseBody.create(MediaType.parse("application/json"), object.toString().getBytes()));//将数据设置到body中
                response = responseBuilder.build(); //builder模式构建response
                return response;
            } else {
                return chain.proceed(request);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}