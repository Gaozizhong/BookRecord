package cn.a1949science.www.bookrecord.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.LinkedList;

import cn.a1949science.www.bookrecord.R;
import cn.a1949science.www.bookrecord.adapter.BookInfoAdapter;
import cn.a1949science.www.bookrecord.bean.BookInfoComment;
import cn.a1949science.www.bookrecord.widget.MyListView;

public class BookInfoActivity extends AppCompatActivity {
    private LinkedList<BookInfoComment> mData = null;
    private Context mContext=BookInfoActivity.this;
    private BookInfoAdapter mAdapter = null;
    private MyListView bookInfoList;
    Button wantRead,reading,havaRead,returnButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        //将资源加载到listview中
        findView();
        onClick();//监听事件
        setListview();

    }

    private void findView() {
        wantRead=(Button)findViewById(R.id.book_info_wantRead);
        reading=(Button)findViewById(R.id.book_info_reading);
        havaRead=(Button)findViewById(R.id.book_info_haveRead);
        returnButton=(Button)findViewById(R.id.book_info_return);
    }

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

    private void setListview() {
        bookInfoList = (MyListView) findViewById(R.id.book_info_list);
        mData = new LinkedList<BookInfoComment>();
        mData.add(new BookInfoComment(R.mipmap.text,"用户1",3,"有些人只拥吻影子，于是只拥有幸福的幻影。 ——莎士比亚 爱情里最需要的，是想象力。每个人必须用尽全力和全部的想象力来形塑对方，并丝毫不向现实低头。那么，当双方的幻想相遇……就再也没有比这更美的景象了。 ——罗曼·加里 爱情，就像影子一样，如果你踩中了，就请带走我的心。 所有流言蜚语都为人津津乐道，人人都热衷于他人的不幸。 谈到时间，我常搞不懂，我的日子所剩无几，为何要用尽方法来跟我们过不去...","From BookRecord At 2017/12/31"));
        mData.add(new BookInfoComment(R.mipmap.text,"用户2",2,"写的没看懂","From BookRecord At 2018/1/2"));
        mData.add(new BookInfoComment(R.mipmap.bookdemo,"用户3",5,"一个很操蛋的故事","From BookRecord At 2017/12/31"));
        mAdapter = new BookInfoAdapter( mData, mContext);
        bookInfoList.setAdapter(mAdapter);

    }
}

