package cn.a1949science.www.bookrecord.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import cn.a1949science.www.bookrecord.R;

public class ReadingActivity extends AppCompatActivity
{
Context context=ReadingActivity.this;
Button returnButton;
private Spinner  reading_way=null;
private Spinner  reading_classfy=null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        findView();
        onClick();
    }



    //点击事件
    private void onClick()
    {
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }
    //添加控件id
    private void findView()
    {
        returnButton=findViewById(R.id.reading_return_button);
        reading_way=findViewById(R.id.reading_way);
        reading_classfy=findViewById(R.id.reading_classfy);
    }
    }
