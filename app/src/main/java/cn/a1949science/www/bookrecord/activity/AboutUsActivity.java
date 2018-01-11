package cn.a1949science.www.bookrecord.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cn.a1949science.www.bookrecord.R;

public class AboutUsActivity extends AppCompatActivity {
Context context=AboutUsActivity.this;
Button returnButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
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

    private void findView() {
        returnButton=findViewById(R.id.about_us_return_button);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
