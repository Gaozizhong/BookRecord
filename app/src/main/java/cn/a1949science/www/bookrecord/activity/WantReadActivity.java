package cn.a1949science.www.bookrecord.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.willy.ratingbar.ScaleRatingBar;

import cn.a1949science.www.bookrecord.R;

public class WantReadActivity extends AppCompatActivity {

    Context context=WantReadActivity.this;
    Button returnButton;
    String book_id,book_score;
    ScaleRatingBar bookRating;

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
    }

    private void getInfoFromBookInfo() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        book_id = bundle.getString("bookId");
        book_score = bundle.getString("bookScore");
    }

    private void findView() {
        returnButton= findViewById(R.id.want_read_return);
        bookRating =  findViewById(R.id.bookRating);
        bookRating.setRating(Float.parseFloat(book_score)/2);
    }
    public void onBackPressed()
    {
        finish();
    }
}
