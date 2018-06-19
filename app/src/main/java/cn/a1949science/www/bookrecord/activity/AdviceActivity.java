package cn.a1949science.www.bookrecord.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

import cn.a1949science.www.bookrecord.R;
import cn.a1949science.www.bookrecord.bean.UserAdvice;
import cn.a1949science.www.bookrecord.bean._User;
import cn.a1949science.www.bookrecord.utils.HttpUtils;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class AdviceActivity extends AppCompatActivity {
Context mContext=AdviceActivity.this;
Button returnButton,advice_confirm;
EditText advice_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
        findView();
        onClick();
    }

    private void onClick() {
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        advice_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!advice_text.getText().toString().equals("")) {
                    final ProgressDialog progress = new ProgressDialog(mContext);
                    progress.setMessage("正在反馈...");
                    progress.setCanceledOnTouchOutside(false);
                    progress.show();
                    //把信息存入Bmob数据库
                    _User bmobUser = BmobUser.getCurrentUser(_User.class);
                    UserAdvice userAdvice = new UserAdvice(bmobUser,advice_text.getText().toString());
                    userAdvice.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null){
                                Toast.makeText(mContext, "创建数据成功：" + s, Toast.LENGTH_LONG).show();
                                progress.dismiss();
                                finish();
                            }else{
                                Toast.makeText(mContext, "失败："+e.getMessage()+","+e.getErrorCode(), Toast.LENGTH_LONG).show();
                                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                            }
                        }
                    });

                } else {
                    Toast.makeText(mContext, "请输入内容！", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void findView() {
        returnButton=findViewById(R.id.advice_return_button);
        advice_confirm = findViewById(R.id.advice_confirm);
        advice_text = findViewById(R.id.advice_text);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
