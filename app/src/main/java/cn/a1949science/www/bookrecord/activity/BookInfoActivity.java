package cn.a1949science.www.bookrecord.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import cn.a1949science.www.bookrecord.R;
import cn.a1949science.www.bookrecord.adapter.CommentAdapter;
import cn.a1949science.www.bookrecord.bean.BookInfoComment;
import cn.a1949science.www.bookrecord.database.BitmapBytes;
import cn.a1949science.www.bookrecord.database.MyDatabaseHelper;
import cn.a1949science.www.bookrecord.widget.MyListView;

public class BookInfoActivity extends AppCompatActivity {
    private LinkedList<BookInfoComment> mData = null;
    private Context mContext = BookInfoActivity.this;
    private CommentAdapter mAdapter = null;
    private MyListView bookInfoList;
    Button wantRead, reading, havaRead, returnButton;
    private MyDatabaseHelper db;//sqlite数据库
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        //将资源加载到listview中
        findView();
        onClick();//监听事件
        test();//c此方法用于在测试阶段，将样本数据导入数据库
        setListview();
    }

    //测试
    private void test() {
        db = new MyDatabaseHelper(mContext, "book.db3", 1);
        //将插入语句注释掉，防止重复插入
        //db.insertBookInfoListview(mContext, db);
    }

    private void findView() {
        wantRead = (Button) findViewById(R.id.book_info_wantRead);
        reading = (Button) findViewById(R.id.book_info_reading);
        havaRead = (Button) findViewById(R.id.book_info_haveRead);
        returnButton = (Button) findViewById(R.id.book_info_return);
    }

    //监听事件
    private void onClick() {
        wantRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WantReadActivity.class);
                startActivity(intent);
            }
        });
        reading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ReadingActivity.class);
                startActivity(intent);
            }
        });
        havaRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SeenActivity.class);
                startActivity(intent);
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //设置listview
    private void setListview() {

        ArrayList<Map<String, Object>> result;
        result = db.resultBookInfoListview(mContext, db);
        bookInfoList = (MyListView) findViewById(R.id.book_info_list);
        mData = new LinkedList<BookInfoComment>();
        //对数据库得到的结果遍历
        for (int i = 0; i < result.size(); i++) {
            mData.add(new BookInfoComment((Bitmap) result.get(i).get("icon"), result.get(i).get("usernick").toString(), (int) result.get(i).get("rate"), result.get(i).get("comment").toString(), result.get(i).get("data").toString()));
            mAdapter = new CommentAdapter(mData, mContext);
            bookInfoList.setAdapter(mAdapter);
        }
    }
    public void OnDestroy() {
        super.onDestroy();
        if (db != null) {
            db.close();
        }
    }

    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}