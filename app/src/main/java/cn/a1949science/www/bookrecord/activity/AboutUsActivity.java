package cn.a1949science.www.bookrecord.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cn.a1949science.www.bookrecord.R;
import cn.a1949science.www.bookrecord.widget.PermissionsChecker;

public class AboutUsActivity extends AppCompatActivity {
    Context context = AboutUsActivity.this;
    Button returnButton, contact_us;
    Intent intent;
    private static final int REQUEST_CALL_PHONE_PERMISSION = 0; // 请求码
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CALL_PHONE
    };
    private PermissionsChecker mPermissionsChecker; // 权限检测器

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
        contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //如果有权限直接执行
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //用intent启动拨打电话
                    intent = new Intent(Intent.ACTION_CALL);
                    Uri data = Uri.parse("tel:17302221002");
                    intent.setData(data);
                    startActivity(intent);

                }
                //如果没有权限那么申请权限
                else {
                    ActivityCompat.requestPermissions((Activity) context,new String[]{android.Manifest.permission.CALL_PHONE},REQUEST_CALL_PHONE_PERMISSION);
                }
            }
        });
    }

    private void findView() {
        returnButton=findViewById(R.id.about_us_return_button);
        contact_us = findViewById(R.id.contactUs);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL_PHONE_PERMISSION) {
            intent = new Intent(Intent.ACTION_CALL);
            Uri data = Uri.parse("tel:17302221002");
            intent.setData(data);
            startActivity(intent);
        } else {
            Toast.makeText(this, "拒绝了权限", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
