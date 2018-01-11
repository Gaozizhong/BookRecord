package cn.a1949science.www.bookrecord.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;

import cn.a1949science.www.bookrecord.R;
import cn.a1949science.www.bookrecord.utils.LocationFromGaode;

public class SeenActivity extends AppCompatActivity {
    Context context=SeenActivity.this;
    private Button addresButton;
    private TextView addressText;
    private Button returnButton;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener;
    //记录获取到的地址
    private  String address;
    //定义一个获取定位消息的类
    LocationFromGaode getAddress;
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
            }
        });
        addresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //实例化定位回调监听器
                mLocationListener = new AMapLocationListener() {
                    @Override
                    public void onLocationChanged(AMapLocation amapLocation)
                    {
                        if (amapLocation!= null) {
                            if (amapLocation.getErrorCode() == 0) {
                                //可在其中解析amapLocation获取相应内容。
                                String country=amapLocation.getCountry();//国家信息
                                String province=amapLocation.getProvince();//省信息
                                String city=amapLocation.getCity();//城市信息
                                String block=amapLocation.getDistrict();//城区信息
                                String street= amapLocation.getStreet();//街道信息
                                String streetNumber=amapLocation.getStreetNum();//街道门牌号信息
                                address=country+province+city+block+street+streetNumber;
                                addressText.setText(address);
                            } else {
                                Toast.makeText(context,"定位失败",Toast.LENGTH_SHORT).show();
                                addressText.setText("定位失败");
                                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                                Log.e("AmapError", "location Error, ErrCode:"
                                        + amapLocation.getErrorCode() + ", errInfo:"
                                        + amapLocation.getErrorInfo());
                            }

                        }
                    }
                };
                //获取定位来触发监听器
                new LocationFromGaode(context,mLocationListener).getLocation();
            }
        });

    }

    private void findView() {
        returnButton=findViewById(R.id.seen_return);
        addresButton=findViewById(R.id.seen_adress_button);
        addressText=findViewById(R.id.seen_adress_textview);
    }
    public void onBackPressed()
    {
        finish();
    }
}
