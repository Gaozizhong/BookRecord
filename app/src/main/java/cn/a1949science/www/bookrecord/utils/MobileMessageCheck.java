package cn.a1949science.www.bookrecord.utils;

import com.alibaba.fastjson.JSON;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * 短信工具类
 * 验证验证码
 * Created by 高子忠 on 2018/1/7.
 */

public class MobileMessageCheck {
    private static final String SERVER_URL="https://api.netease.im/sms/verifycode.action";//请求的URL
    private static final String APP_KEY="0d24f47090a642edb13393c25d361f37";//账号
    private static final String APP_SECRET="58d6f3ab4344";//密码
    private static final String NONCE="123456";//随机数

    public static String checkMsg(String phone,String sum) throws IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(SERVER_URL);

        String curTime=String.valueOf((new Date().getTime()/1000L));
        String checkSum=CheckSumBuilder.getCheckSum(APP_SECRET,NONCE,curTime);

        //设置请求的header
        post.addHeader("AppKey",APP_KEY);
        post.addHeader("Nonce",NONCE);
        post.addHeader("CurTime",curTime);
        post.addHeader("CheckSum",checkSum);
        post.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");

        //设置请求参数
        ArrayList<NameValuePair> nameValuePairs =new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("mobile",phone));
        nameValuePairs.add(new BasicNameValuePair("code",sum));


        post.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));

        //执行请求
        HttpResponse response=httpClient.execute(post);
        String responseEntity= EntityUtils.toString(response.getEntity(),"utf-8");

        //判断是否发送成功，发送成功返回true
        String code= JSON.parseObject(responseEntity).getString("code");
        System.out.println(code);
        if (code.equals("200")){
            return "success";
        }
        return "error";
    }
}
