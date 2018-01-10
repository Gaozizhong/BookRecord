package cn.a1949science.www.bookrecord.utils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 网络工具类
 * Created by 高子忠 on 2018/1/10.
 */

public class HttpUtils {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * 异步get请求
     *
     * @param urlStr URL对象
     */
    public static String doGetAsy(final String urlStr) {
        final String[] result = {null};
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    result[0] = doGet(urlStr);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return result[0];
    }

    /**
     * 异步post请求
     *  @param urlStr
     * @param json
     */
    public static String doPostAsy(final String urlStr, final String json) {
        final String[] result = {null};
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    result[0] = doPost(urlStr, json);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        return result[0];
    }

    /**
     * 向指定url发送post方式的请求
     *
     * @param urlStr  URL对象
     * @param json 提交Json数据
     * @return
     */
    private static String doPost(String urlStr, String json) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(urlStr)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }


    /**
     * get请求
     *
     * @param urlStr URL
     * @return GET到的字符串
     */
    private static String doGet(String urlStr) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(urlStr).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }



}
