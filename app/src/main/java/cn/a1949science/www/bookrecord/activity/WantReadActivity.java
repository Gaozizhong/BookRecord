package cn.a1949science.www.bookrecord.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.willy.ratingbar.ScaleRatingBar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.a1949science.www.bookrecord.R;
import cn.a1949science.www.bookrecord.bean.ReadInfo;
import cn.a1949science.www.bookrecord.bean._User;
import cn.a1949science.www.bookrecord.database.OperationReadInfo;
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
    ReadInfo readInfo,readInfo2;

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
                readInfo2 = new ReadInfo(bmobUser, book_isbn, read_state, want_read_reason, want_read_hope, new BmobDate(new Date(System.currentTimeMillis())));
                if (readInfo == null) {
                    OperationReadInfo.addReadInfo(readInfo2);
                    progress.dismiss();
                } else {
                    OperationReadInfo.updateReadInfo(readInfo2);
                }
                finish();
            }
        });

    }

    private void getInfoFromBookInfo() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("id_score");
        book_isbn = bundle.getString("bookISBN");
        book_score = bundle.getString("bookScore");
    }



    private void findView() {
        returnButton = findViewById(R.id.want_read_return);
        wantButton = findViewById(R.id.want_read_Button);
        bookRating =  findViewById(R.id.bookRating);
        wantReason = findViewById(R.id.want_read_reason);
        wantHope = findViewById(R.id.want_read_hope);
        bookRating.setRating(Float.parseFloat(book_score)/2);
        //通过ISBN先查询一下Bmob数据库中是否有此条记录,有的话就显示出来
        _User bmobUser = BmobUser.getCurrentUser(_User.class);
        ReadInfo readInfo = OperationReadInfo.queryReadInfo(bmobUser, book_isbn);
        if (readInfo != null) {
            wantReason.setText(readInfo.getRead_reason());
            wantHope.setText(readInfo.getRead_except());
        }
    }
    public void onBackPressed()
    {
        finish();
    }
}
