package cn.a1949science.www.bookrecord.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

import cn.a1949science.www.bookrecord.R;
import cn.a1949science.www.bookrecord.utils.HttpUtils;

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
                    progress.setMessage("正在查询...");
                    progress.setCanceledOnTouchOutside(false);
                    progress.show();
                    //创建一个Map对象
                    Map<String, String> map = new HashMap<>();
                    SharedPreferences sp = mContext.getSharedPreferences("userData", Context.MODE_PRIVATE);
                    map.put("user_id", sp.getString("userId",null));
                    map.put("advice_content", advice_text.getText().toString());
                    //转成JSON数据
                    final String adviceJson = JSON.toJSONString(map, true);
                    HttpUtils.doPostAsy("http://139.199.123.55:8080/book/UserAdvice.jsp", adviceJson, new HttpUtils.CallBack() {
                        @Override
                        public void onRequestComplete(String result) {
                            progress.dismiss();
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
