package cn.a1949science.www.bookrecord.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.a1949science.www.bookrecord.R;
import cn.a1949science.www.bookrecord.bean.BookInfo;
import cn.a1949science.www.bookrecord.bean.ReadInfo;
import cn.a1949science.www.bookrecord.bean._User;
import cn.a1949science.www.bookrecord.database.OperationReadInfo;
import cn.a1949science.www.bookrecord.utils.HttpUtils;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class WantReadActivity extends AppCompatActivity {

    Boolean bmob_if_hava_read_info = false;
    Context context=WantReadActivity.this;
    Button returnButton,wantButton;
    String book_isbn,book_score;
    ScaleRatingBar bookRating;
    EditText wantReason,wantHope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_want_read);
        getInfoFromBookInfo();
        //getInfoFromBmob();
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
                progress.setMessage("正在记录...");
                progress.setCanceledOnTouchOutside(false);
                progress.show();

                Integer read_state = 0;
                String want_read_reason = wantReason.getText().toString();
                String want_read_hope = wantHope.getText().toString();

                _User bmobUser = BmobUser.getCurrentUser(_User.class);
                ReadInfo readInfo = new ReadInfo(bmobUser, book_isbn, read_state, want_read_reason, want_read_hope, new BmobDate(new Date(System.currentTimeMillis())));
                if (OperationReadInfo.addBookInfo(readInfo)) {
                    progress.dismiss();
                }
                /*SharedPreferences sp = context.getSharedPreferences("userData", Context.MODE_PRIVATE);
                //创建一个Map对象
                Map<String,String> map = new HashMap<>();
                map.put("user_id", sp.getString("userId",null));
                map.put("book_isbn", book_isbn);
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
                });*/
            }
        });

    }

    private void getInfoFromBookInfo() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("id_score");
        book_isbn = bundle.getString("bookISBN");
        book_score = bundle.getString("bookScore");
    }

    //通过ISBN先查询一下Bmob数据库中是否有此条记录
    private void getInfoFromBmob() {
        _User user = BmobUser.getCurrentUser(_User.class);
        BmobQuery<ReadInfo> query1 = new BmobQuery<>();
        // 查询当前用户的所有记录
        query1.addWhereEqualTo("user_id", user);
        BmobQuery<ReadInfo> query2 = new BmobQuery<>();
        query2.addWhereEqualTo("book_isbn", book_isbn);
        List<BmobQuery<ReadInfo>> andQuerys = new ArrayList<>();
        andQuerys.add(query1);
        andQuerys.add(query2);
        //查询符合整个and条件的人
        BmobQuery<ReadInfo> query = new BmobQuery<>();
        query.and(andQuerys);
        query.findObjects(new FindListener<ReadInfo>() {
            @Override
            public void done(List<ReadInfo> list, BmobException e) {
                if(e==null){
                    bmob_if_hava_read_info = list.size() != 0;
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    private void findView() {
        returnButton = findViewById(R.id.want_read_return);
        wantButton = findViewById(R.id.want_read_Button);
        bookRating =  findViewById(R.id.bookRating);
        wantReason = findViewById(R.id.want_read_reason);
        wantHope = findViewById(R.id.want_read_hope);
        bookRating.setRating(Float.parseFloat(book_score)/2);
        _User bmobUser = BmobUser.getCurrentUser(_User.class);
    }
    public void onBackPressed()
    {
        finish();
    }
}
