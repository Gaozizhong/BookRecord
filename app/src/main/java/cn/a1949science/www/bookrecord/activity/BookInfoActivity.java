package cn.a1949science.www.bookrecord.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

import cn.a1949science.www.bookrecord.R;
import cn.a1949science.www.bookrecord.adapter.CommentAdapter;
import cn.a1949science.www.bookrecord.bean.BookInfo;
import cn.a1949science.www.bookrecord.bean.BookInfoComment;
import cn.a1949science.www.bookrecord.database.MyDatabaseHelper;
import cn.a1949science.www.bookrecord.utils.BookInfoGetFromDouban;
import cn.a1949science.www.bookrecord.utils.HttpUtils;
import cn.a1949science.www.bookrecord.widget.MyListView;

public class BookInfoActivity extends AppCompatActivity {
    private LinkedList<BookInfoComment> mData = null;
    private Context mContext = BookInfoActivity.this;
    private CommentAdapter mAdapter = null;
    private MyListView bookInfoList;
    Button wantRead, reading, havaRead, returnButton,scan;
    private MyDatabaseHelper db;//sqlite数据库
    private long exitTime = 0;
    private int REQUEST_CODE = 5,REQUEST_CAMERA_PERMISSION = 0;
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
        scan=findViewById(R.id.book_info_scan);
        ZXingLibrary.initDisplayOpinion(this);
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
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent it = new Intent(mContext, CaptureActivity.class);
                    startActivityForResult(it, REQUEST_CODE);
                } else {//没有权限
                    ActivityCompat.requestPermissions(BookInfoActivity.this,new String[]{Manifest.permission.CAMERA},REQUEST_CAMERA_PERMISSION);
                }
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
        bookInfoList = (MyListView) findViewById(R.id.book_info_list);
        mData = new LinkedList<BookInfoComment>();
        //对数据库得到的结果遍历
        for (int i = 0; i < result.size(); i++) {
            mData.add(new BookInfoComment((Bitmap) result.get(i).get("icon"), result.get(i).get("usernick").toString(), (int) result.get(i).get("rate"), result.get(i).get("comment").toString(), result.get(i).get("data").toString()));
            mAdapter = new CommentAdapter(mData, mContext);
            bookInfoList.setAdapter(mAdapter);
        }
    }

   // Activity结束后执行
    public void OnDestroy() {
        super.onDestroy();
        if (db != null) {
            db.close();
        }
    }
    //对返回键的监听处理
    public void onBackPressed() {
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    final String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(mContext, HttpUtils.doGetAsy("https://api.douban.com/v2/book/isbn/" + result), Toast.LENGTH_LONG).show();
                    //开启线程来发起网络请求
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                BookInfo bookInfo = BookInfoGetFromDouban.BookInfoGet(result);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(mContext, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}