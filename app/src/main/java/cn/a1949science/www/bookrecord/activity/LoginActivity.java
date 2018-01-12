package cn.a1949science.www.bookrecord.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.a1949science.www.bookrecord.R;
import cn.a1949science.www.bookrecord.utils.AMUtils;
import cn.a1949science.www.bookrecord.utils.HttpUtils;
import cn.a1949science.www.bookrecord.utils.MobileMessageSend;

public class LoginActivity extends AppCompatActivity {

    Context mContext = LoginActivity.this;
    EditText phoneNumber,verification;
    Button delete,getverification,loginButton;
    MyCountTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //寻找地址
        findView();
        onClick();
    }

    //事件
    private void onClick() {
        SharedPreferences sp = mContext.getSharedPreferences("userData", Context.MODE_PRIVATE);
        if (sp.getString("phoneNumber",null) != null) {
            phoneNumber.setText(sp.getString("phoneNumber",null));
        }
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumber.setText("");
            }
        });
        //对输入的电话号码进行判断
        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 11) {
                    AMUtils.onInactive(mContext, phoneNumber);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //登录按钮的触发事件
        loginButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View v) {
                addDataToMysql(phoneNumber.getText().toString(), System.currentTimeMillis());
                //Intent intent = new Intent(mContext, MainActivity.class);
                //startActivity(intent);
                //finish();
                /*if (!phoneNumber.getText().toString().equals("") && !verification.getText().toString().equals("")) {
                    //验证验证码是否正确
                    new Thread() {
                        @Override
                        public void run() {
                            String code = verification.getText().toString();
                            try {
                                String str = MobileMessageCheck.checkMsg(phoneNumber.getText().toString(), code);
                                if (str.equals("success")) {
                                    String userId = addDataToMysql(phoneNumber.getText().toString(), System.currentTimeMillis());
                                    addDataToLocal(userId, phoneNumber.getText().toString(), System.currentTimeMillis() + 30 * 24 * 60 * 60 * 1000);
                                    Intent intent = new Intent(mContext, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Looper.prepare();
                                    Toast.makeText(mContext, "验证失败！", Toast.LENGTH_LONG).show();
                                    Looper.loop();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                } else {
                    Toast.makeText(mContext, "请填写正确的信息！", Toast.LENGTH_LONG).show();
                }*/

            }
        });

        getverification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = phoneNumber.getText().toString();
                if(number.length()!=11)
                {
                    Toast.makeText(mContext, "请输入11位有效号码", Toast.LENGTH_LONG).show();
                    return;
                }
                //如果输入的手机号不为空的话
                if (!TextUtils.isEmpty(number)) {

                    timer = new MyCountTimer(60000, 1000);
                    timer.start();
                    //请求发送验证码
                    new Thread(){
                        @Override
                        public void run(){
                            try {
                                String number = phoneNumber.getText().toString();
                                String str = MobileMessageSend.sendMsg(number);
                                if (str.equals("success")) {
                                    Looper.prepare();
                                    Toast.makeText(mContext, "发送成功", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                } else {
                                    Looper.prepare();
                                    Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();

                } else {
                    Toast.makeText(mContext, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //把用户数据写入到本地
    private void addDataToLocal(String userId,String phoneNumber,Long deadline) {
        SharedPreferences sp = mContext.getSharedPreferences("userData", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sp.edit();
        editor.putString("phoneNumber", phoneNumber);
        editor.putString("userId", userId);
        editor.putLong("deadline", deadline);
        editor.apply();
    }

    //把用户数据写入到数据库中
    private void addDataToMysql(final String phoneNumber, final long time) {
        //把两个参数存到服务器中，返回userId
        //创建一个Map对象
        Map<String,String> map = new HashMap<>();
        map.put("user_phone_number", phoneNumber);
        //map.put("creat_time", time.toString());
        //转成JSON数据
        final String json = JSON.toJSONString(map,true);
        //Toast.makeText(mContext, json, Toast.LENGTH_SHORT).show();
        HttpUtils.doPostAsy("http://139.199.123.55:8080/login1/Login.jsp", json, new HttpUtils.CallBack() {
            @Override
            public void onRequestComplete(final String result) {
                JSONObject jsonObject = JSON.parseObject(result.trim());
                final String userId = jsonObject.getString("user_id");
                addDataToLocal(userId, phoneNumber, time + (long)30 * 24 * 60 * 60 * 1000);
                Intent intent = new Intent(mContext,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void findView() {
        phoneNumber= findViewById(R.id.phoneNumber);
        verification= findViewById(R.id.verification);
        delete= findViewById(R.id.phoneNumberDelete);
        getverification= findViewById(R.id.VerificationButton);
        loginButton= findViewById(R.id.loginButton);
    }


    //按钮效果
    private class MyCountTimer extends CountDownTimer {

        MyCountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            getverification.setEnabled(false);
            getverification.setText((millisUntilFinished / 1000) +"秒后重发");
        }
        @Override
        public void onFinish() {
            getverification.setEnabled(true);
            getverification.setText("重新发送验证码");
        }
    }
}
