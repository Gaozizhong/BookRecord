package cn.a1949science.www.bookrecord.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import cn.a1949science.www.bookrecord.R;
import cn.a1949science.www.bookrecord.utils.AMUtils;

public class LoginActivity extends AppCompatActivity {

    Context mContext = LoginActivity.this;
    EditText phoneNumber,verification;
    Button delete,getverification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        phoneNumber=(EditText)findViewById(R.id.phoneNumber);

        verification=(EditText)findViewById(R.id.verification);
        delete=(Button)findViewById(R.id.phoneNumberDelete);
        getverification=(Button)findViewById(R.id.VerificationButton);
        //对输入的电话号码进行判断
        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 11) {
                    AMUtils.onInactive(mContext, phoneNumber);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
            public void ifphoneNumber() {


            }
        });
    }
}
