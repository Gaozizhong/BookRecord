package cn.a1949science.www.bookrecord.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.a1949science.www.bookrecord.R;

public class ReadingActivity extends AppCompatActivity {
Context context=ReadingActivity.this;
Button returnButton;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        findView();
        onClick();
    }
    private void onClick()
    {
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,BookInfoActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void findView()
    {
        returnButton=(Button)findViewById(R.id.reading_adress_button);
    }
    }
