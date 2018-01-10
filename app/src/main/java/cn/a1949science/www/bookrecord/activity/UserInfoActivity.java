package cn.a1949science.www.bookrecord.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cn.a1949science.www.bookrecord.R;

public class UserInfoActivity extends AppCompatActivity {
    Context context=UserInfoActivity.this;
    Button userReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        findView();
        onClick();
    }

    private void onClick() {
        userReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void findView() {
        userReturn=findViewById(R.id.user_info_return_button);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
