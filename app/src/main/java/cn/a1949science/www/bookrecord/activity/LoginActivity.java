package cn.a1949science.www.bookrecord.activity;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import cn.a1949science.www.bookrecord.R;
import cn.a1949science.www.bookrecord.utils.AMUtils;
import cn.a1949science.www.bookrecord.utils.MobileMessageCheck;
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
        findView();//寻找地址
        onClick();
    }

    //事件
    private void onClick() {
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

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //验证验证码是否正确
                new Thread(){
                    @Override
                    public void run(){
                        String code = verification.getText().toString();
                        try {
                            String str = MobileMessageCheck.checkMsg(phoneNumber.getText().toString(), code);
                            if(str.equals("success")){
                                Intent intent = new Intent(mContext, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Looper.prepare();
                                Toast.makeText(mContext, "验证失败！", Toast.LENGTH_LONG).show();
                                Looper.loop();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

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

    private void findView() {
        phoneNumber=(EditText)findViewById(R.id.phoneNumber);
        verification=(EditText)findViewById(R.id.verification);
        delete=(Button)findViewById(R.id.phoneNumberDelete);
        getverification=(Button)findViewById(R.id.VerificationButton);
        loginButton=(Button)findViewById(R.id.loginButton);
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
