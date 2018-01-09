package cn.a1949science.www.bookrecord.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.a1949science.www.bookrecord.R;

public class SeenActivity extends AppCompatActivity {
    Context context=SeenActivity.this;
    private Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seen);
        findView();
        onClick();
    }

    private void onClick() {
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_left_in,R.anim.slide_right_out);
            }
        });
    }

    private void findView() {
        returnButton= findViewById(R.id.seen_return);
    }
}
