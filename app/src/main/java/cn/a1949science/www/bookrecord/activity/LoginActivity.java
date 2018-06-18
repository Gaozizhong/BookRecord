package cn.a1949science.www.bookrecord.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.a1949science.www.bookrecord.R;
import cn.a1949science.www.bookrecord.bean._User;
import cn.a1949science.www.bookrecord.utils.AMUtils;
import cn.a1949science.www.bookrecord.utils.HttpUtils;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;

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

    //查找地址
    private void findView() {
        phoneNumber= findViewById(R.id.phoneNumber);
        verification= findViewById(R.id.verification);
        delete= findViewById(R.id.phoneNumberDelete);
        getverification= findViewById(R.id.VerificationButton);
        loginButton= findViewById(R.id.loginButton);
    }

    //事件
    private void onClick() {
        //新建一个缓存
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
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(phoneNumber.getText().toString()) && !TextUtils.isEmpty(verification.getText().toString())) {
                    loginButton.setClickable(false);
                    final ProgressDialog progress = new ProgressDialog(mContext);
                    progress.setMessage("正在登录中...");
                    progress.setCanceledOnTouchOutside(false);
                    progress.show();

                    //验证验证码是否正确
                    BmobUser.signOrLoginByMobilePhone(phoneNumber.getText().toString(), verification.getText().toString(), new LogInListener<_User>() {

                        @Override
                        public void done(_User user, BmobException e) {
                            if(user!=null){
                                //Toast.makeText(mContext, "用户登陆成功！", Toast.LENGTH_SHORT).show();
                                addDataToLocal(user.getObjectId(), phoneNumber.getText().toString(),  System.currentTimeMillis() + (long)30 * 24 * 60 * 60 * 1000);
                                progress.dismiss();
                                Intent intent = new Intent(mContext, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(mContext, "验证失败！", Toast.LENGTH_LONG).show();
                                loginButton.setClickable(true);
                                progress.dismiss();
                            }
                        }
                    });

                } else {
                    Toast.makeText(mContext, "手机号码或验证码不能为空！", Toast.LENGTH_LONG).show();
                }
            }
        });

        //获取验证码按钮的触发事件
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
                    BmobSMS.requestSMSCode(phoneNumber.getText().toString(), "登录验证", new QueryListener<Integer>() {
                        @Override
                        public void done(Integer integer, BmobException e) {
                            if (e == null) {//验证码发送成功
                                Toast.makeText(mContext, "发送成功！", Toast.LENGTH_SHORT).show();
                                Log.i("smile", "短信id：" + integer);//用于查询本次短信发送详情
                            } else {
                                Toast.makeText(mContext, "发送失败！"+e, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

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
        //转成JSON数据
        final String json = JSON.toJSONString(map,true);
        HttpUtils.doPostAsy(getString(R.string.LoginInterface), json, new HttpUtils.CallBack() {
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


    //按钮效果
    private class MyCountTimer extends CountDownTimer {

        MyCountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @SuppressLint("SetTextI18n")
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


    protected void onDestroy() {
        super.onDestroy();
    };
}
