package cn.a1949science.www.bookrecord.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cn.a1949science.www.bookrecord.R;

public class AdviceActivity extends AppCompatActivity {
Context context=AdviceActivity.this;
Button returnButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
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
        returnButton.findViewById(R.id.advice_return_button);

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
