package cn.a1949science.www.bookrecord.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

import cn.a1949science.www.bookrecord.R;
import cn.a1949science.www.bookrecord.adapter.CommentAdapter;
import cn.a1949science.www.bookrecord.bean.BookInfo;
import cn.a1949science.www.bookrecord.bean.BookInfoComment;
import cn.a1949science.www.bookrecord.database.MyDatabaseHelper;
import cn.a1949science.www.bookrecord.widget.MyListView;

public class BookInfoActivity extends AppCompatActivity {
    private LinkedList<BookInfoComment> mData = null;
    private Context mContext = BookInfoActivity.this;
    private CommentAdapter mAdapter = null;
    private MyListView bookInfoList;
    Button wantRead, reading, havaRead, returnButton;
    TextView bookName,bookScore,bookWriter,bookPressName,bookPressData,bookISBN,book_summary,title;
    ScaleRatingBar bookRating;
    ImageView bookImage;
    ScrollView scrollView;
    private MyDatabaseHelper db;//sqlite数据库
    private long exitTime = 0;
    private BookInfo bookInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        //将资源加载到listview中
        findView();
        getBookInfo();
        //监听事件
        onClick();
        test();//c此方法用于在测试阶段，将样本数据导入数据库
        setListview();
    }

    //从上一页面获取图书信息类，来填充控件
    @SuppressLint("SetTextI18n")
    private void getBookInfo() {
        Intent intent = this.getIntent();
        bookInfo = (BookInfo) intent.getSerializableExtra("bookInfo");
        bookName.setText(bookInfo.getBookName());
        title.setText(bookInfo.getBookName());
        bookWriter.setText(bookInfo.getAuthorName());
        bookPressName.setText(bookInfo.getPublish());
        bookPressData.setText(bookInfo.getPublishDate());
        bookISBN.setText("ISBN:"+bookInfo.getISBN());
        Glide.with(mContext)
                .load(bookInfo.getImageUrl())
                .into(bookImage);
        bookScore.setText(bookInfo.getRating());
        bookRating.setRating(Float.parseFloat(bookInfo.getRating())/2);
        book_summary.setText(bookInfo.getBook_summary());
    }

    //测试
    private void test() {
        db = new MyDatabaseHelper(mContext, "book.db3", 1);
        //将插入语句注释掉，防止重复插入
        //db.insertBookInfoListview(mContext, db);
    }

    private void findView() {
        wantRead = findViewById(R.id.book_info_wantRead);
        reading = findViewById(R.id.book_info_reading);
        havaRead = findViewById(R.id.book_info_haveRead);
        returnButton = findViewById(R.id.book_info_return);
        bookName = findViewById(R.id.bookName);
        bookScore = findViewById(R.id.bookScore);
        bookWriter = findViewById(R.id.bookWriter);
        bookPressName = findViewById(R.id.bookPressName);
        bookPressData = findViewById(R.id.bookPressData);
        bookISBN = findViewById(R.id.bookISBN);
        bookRating = findViewById(R.id.bookRating);
        bookImage = findViewById(R.id.bookImage);
        book_summary = findViewById(R.id.book_summary);
        title = findViewById(R.id.title);
        scrollView = findViewById(R.id.scrollView);
    }

    //监听事件
    private void onClick() {

        //scrollView从顶部显示
        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                scrollView.fullScroll(View.FOCUS_UP);
            }
        });
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
              finish();
            }
        });
    }

    //设置listview
    private void setListview() {

        ArrayList<Map<String, Object>> result;
        result = db.resultBookInfoListview(mContext, db);
        bookInfoList = findViewById(R.id.book_info_list);
        mData = new LinkedList<>();
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
        finish();
    }

}