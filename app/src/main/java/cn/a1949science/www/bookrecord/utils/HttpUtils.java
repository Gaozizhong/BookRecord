package cn.a1949science.www.bookrecord.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 网络工具类
 * Created by 高子忠 on 2018/1/10.
 */

public class HttpUtils {
    //设置网络连接超时时间
    public static final int TIMEOUT_IN_MILLIONS = 5000;


    //设置请求结果回调
    public interface CallBack {
        void onRequestComplete(String result);
    }

    /**
     * 异步get请求
     *
     * @param urlStr
     * @param callBack 以接口对象为参数，特别好用
     */
    public static void doGetAsy(final String urlStr, final CallBack callBack) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = doGet(urlStr);
                if (callBack != null) {
                    callBack.onRequestComplete(result);
                }
            }
        }).start();
    }

    /**
     * 异步post请求
     *
     * @param urlStr
     * @param params
     * @param callBack
     */
    public static void doPostAsy(final String urlStr, final String params, final CallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = doPost(urlStr, params);
                if (callBack != null) {
                    callBack.onRequestComplete(result);
                }
            }
        }).start();
    }

    /**
     * 向指定url发送post方式的请求
     *
     * @param urlStr
     * @param params
     * @return
     */
    private static String doPost(String urlStr, String params) {
        URL url;
        StringBuilder result = new StringBuilder();
        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;
        try {
            url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            //设置一系列的请求属性，让服务器端知道(我发送了什么类型的数据)
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "keep-Alive");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //发送POST请求必须设置如下两行
            conn.setDoInput(true);
            conn.setDoOutput(true);
            //对请求参数的处理
            if (params != null && !params.trim().equals("")) {
                //获取URLConnection对象对应的输出流
                OutputStream os = conn.getOutputStream();
                printWriter = new PrintWriter(os);
                // 发送请求参数
                printWriter.print(params);
                printWriter.flush();
            }
            //定义BufferedReader输入流来读取URL的响应
            InputStream is = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(is);
            //主要是为了每次读取一行
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //使用finally块来关闭输出流、输入流
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (printWriter != null) {
                    printWriter.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }


    /**
     * get请求
     *
     * @param urlStr
     * @return
     */
    private static String doGet(String urlStr) {
        URL url = null;
        HttpURLConnection conn = null;
        InputStream is = null;//输入流
        ByteArrayOutputStream baos = null; //输出流
        try {
            url = new URL(urlStr);
            //获取一个连接对象
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "keep-Alive");
            //获取响应码 说明请求成功
            if (conn.getResponseCode() == 200) {
                //获取一个输入流
                is = conn.getInputStream();
                //创建一个字节数组输出流
                baos = new ByteArrayOutputStream();
                int len = -1;
                //获取一个字节数组
                byte[] bytes = new byte[1024];
                while ((len = is.read(bytes)) != -1) {
                    baos.write(bytes, 0, len);
                }
                baos.flush();
                //返回字符串
                return baos.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //处理管理流的逻辑
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            assert conn != null;
            conn.disconnect();
        }
        return null;
    }



}
