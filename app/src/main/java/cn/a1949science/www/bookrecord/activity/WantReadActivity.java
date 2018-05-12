package cn.a1949science.www.bookrecord.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.HashMap;
import java.util.Map;

import cn.a1949science.www.bookrecord.R;
import cn.a1949science.www.bookrecord.bean.BookInfo;
import cn.a1949science.www.bookrecord.utils.HttpUtils;

public class WantReadActivity extends AppCompatActivity {

    Context context=WantReadActivity.this;
    Button returnButton,wantButton;
    String book_id,book_score;
    ScaleRatingBar bookRating;
    EditText wantReason,wantHope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_want_read);
        getInfoFromBookInfo();
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

        wantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progress = new ProgressDialog(context);
                progress.setMessage("正在查询...");
                progress.setCanceledOnTouchOutside(false);
                progress.show();
                String want_read_reason = wantReason.getText().toString();
                String want_read_hope = wantHope.getText().toString();
                SharedPreferences sp = context.getSharedPreferences("userData", Context.MODE_PRIVATE);
                //创建一个Map对象
                Map<String,String> map = new HashMap<>();
                map.put("user_id", sp.getString("userId",null));
                map.put("book_id", book_id);
                map.put("read_reason", want_read_reason);
                map.put("read_except", want_read_hope);
                //转成JSON数据
                final String ISBNjson = JSON.toJSONString(map,true);
                HttpUtils.doPostAsy(getString(R.string.WantReadInterface), ISBNjson, new HttpUtils.CallBack() {
                    @Override
                    public void onRequestComplete(final String result2) {
                        finish();
                        progress.dismiss();
                    }
                });
            }
        });

    }

    private void getInfoFromBookInfo() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("id_score");
        book_id = bundle.getString("bookId");
        book_score = bundle.getString("bookScore");

    }

    private void findView() {
        returnButton = findViewById(R.id.want_read_return);
        wantButton = findViewById(R.id.want_read_Button);
        bookRating =  findViewById(R.id.bookRating);
        wantReason = findViewById(R.id.want_read_reason);
        wantHope = findViewById(R.id.want_read_hope);
        bookRating.setRating(Float.parseFloat(book_score)/2);
    }
    public void onBackPressed()
    {
        finish();
    }
}
