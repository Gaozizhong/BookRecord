package cn.a1949science.www.bookrecord;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText phoneNumber=(EditText)findViewById(R.id.phoneNumber);

        final EditText verification=(EditText)findViewById(R.id.verification);
        Button delete=(Button)findViewById(R.id.phoneNumberDelete);
        Button getverification=(Button)findViewById(R.id.VerificationButton);
        //对输入的电话号码进行判断
        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String temp=s.toString();
                if(temp.length()>11)
                {
                    phoneNumber.setText(s);

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
