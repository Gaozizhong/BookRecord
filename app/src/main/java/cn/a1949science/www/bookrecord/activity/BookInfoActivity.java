package cn.a1949science.www.bookrecord.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
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
import cn.a1949science.www.bookrecord.adapter.BookInfoAdapter;
import cn.a1949science.www.bookrecord.bean.BookInfoComment;
import cn.a1949science.www.bookrecord.database.BitmapBytes;
import cn.a1949science.www.bookrecord.database.MyDatabaseHelper;
import cn.a1949science.www.bookrecord.widget.MyListView;

public class BookInfoActivity extends AppCompatActivity {
    private LinkedList<BookInfoComment> mData = null;
    private Context mContext=BookInfoActivity.this;
    private BookInfoAdapter mAdapter = null;
    private MyListView bookInfoList;
    Button wantRead,reading,havaRead,returnButton;
    private MyDatabaseHelper db;//sqlite数据库

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
        db=new MyDatabaseHelper(mContext,"book.db3",1);
        db.insertBookInfoListview(mContext,db);

    }

    private void findView() {
        wantRead=(Button)findViewById(R.id.book_info_wantRead);
        reading=(Button)findViewById(R.id.book_info_reading);
        havaRead=(Button)findViewById(R.id.book_info_haveRead);
        returnButton=(Button)findViewById(R.id.book_info_return);
    }
    //监听事件
    private void onClick() {
        wantRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast=Toast.makeText(mContext,"已添加至想读",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        reading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast=Toast.makeText(mContext,"已添加至在读",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        havaRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast=Toast.makeText(mContext,"已添加至读过",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,WantReadActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
//设置listview
    private void setListview() {

        ArrayList<Map<String,Object>> result;
        result=db.resultBookInfoListview(mContext,db);
        bookInfoList = (MyListView) findViewById(R.id.book_info_list);
        mData = new LinkedList<BookInfoComment>();
        Iterator it = result.iterator();
        //对数据库得到的结果遍历
        while(it.hasNext()) {
            mData.add(new BookInfoComment((Bitmap) result.get(0).get("icon"), result.get(0).get("usernick").toString(), (int) result.get(0).get("rate"), result.get(0).get("comment").toString(), result.get(0).get("data").toString()));
        }
        mAdapter = new BookInfoAdapter( mData, mContext);
        bookInfoList.setAdapter(mAdapter);
    }
    public void OnDestroy()
    {
        super.onDestroy();
        if (db!=null)
        {
            db.close();
        }
    }
}

//将图片转化为字节
