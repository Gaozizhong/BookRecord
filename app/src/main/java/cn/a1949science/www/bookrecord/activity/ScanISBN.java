package cn.a1949science.www.bookrecord.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import cn.a1949science.www.bookrecord.R;

public class ScanISBN extends AppCompatActivity {

    Context mContext = ScanISBN.this;
    Toolbar toolbar;
    public static boolean isOpen = false;
    ImageView light;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_isbn);
    }
}
