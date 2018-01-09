package cn.a1949science.www.bookrecord.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.io.InputStream;

/**
 * 网络工具类
 * Created by 高子忠 on 2018/1/10.
 */

public class HttpUtils {
    private final static String TAG = "EasyTokenSevice";
    private final static int connectionTimeout = 5000;
    private static InputStream inputStream = null;
    private static String urlStr = null;
    private static boolean isConnecting;

    /**
     * 封装http get请求
     *
     * @param url
     * @return is
     */
    public static InputStream get(String url)throws IOException,Exception {

        urlStr = url;
        isConnecting = true;

        HttpGet httpGet = new HttpGet(urlStr);
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters,
                connectionTimeout);

        DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
        HttpResponse response = httpClient.execute(httpGet);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            inputStream = entity.getContent();
            return inputStream;}
        else return null;




    }
}
