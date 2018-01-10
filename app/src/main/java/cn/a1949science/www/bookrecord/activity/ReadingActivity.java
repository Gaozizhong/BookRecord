package cn.a1949science.www.bookrecord.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.a1949science.www.bookrecord.R;

public class ReadingActivity extends AppCompatActivity
{
Context context=ReadingActivity.this;
Button returnButton;
Button adressButton;
TextView adressText;
private Spinner  reading_way=null;
private Spinner  reading_classfy=null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
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
        adressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //初始化定位
                initLocation();
                mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
                mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
            }
        });

    }

    public static String SHA1(Context context) {
        try {
            @SuppressLint("PackageManagerGetSignatures") PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length()-1);
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    //初始化定位
    private void initLocation() {
        //声明定位回调监听器
        AMapLocationListener mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //可在其中解析amapLocation获取相应内容。
                        String country=amapLocation.getCountry();//国家信息
                        String province=amapLocation.getProvince();//省信息
                        String city=amapLocation.getCity();//城市信息
                        String block=amapLocation.getDistrict();//城区信息
                        String street= amapLocation.getStreet();//街道信息
                        String streetNumber=amapLocation.getStreetNum();//街道门牌号信息
                        Toast.makeText(context, country , Toast.LENGTH_LONG).show();
                        adressText.setText(country+province+city+block+street+streetNumber);
                    } else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                    }

                }
            }
        };
    //初始化定位
            mLocationClient = new AMapLocationClient(getApplicationContext());
    //设置定位回调监听
            mLocationClient.setLocationListener(mLocationListener);
    //初始化AMapLocationClientOption对象
            mLocationOption =new AMapLocationClientOption();

            AMapLocationClientOption option = new AMapLocationClientOption();
            /**
             * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
             */
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        if(null!=mLocationClient)
            {
                mLocationClient.setLocationOption(option);
                //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
                mLocationClient.stopLocation();
                mLocationClient.startLocation();
                //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
                mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                //获取一次定位结果：
//该方法默认为false。
                mLocationOption.setOnceLocation(true);

//获取最近3s内精度最高的一次定位结果：
//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
                mLocationOption.setOnceLocationLatest(true);
            }
            //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
            //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
            //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
            //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
//启动定位
        mLocationClient.startLocation();
    }

    //添加控件id
    private void findView()
    {
        returnButton=findViewById(R.id.reading_return_button);
        reading_way=findViewById(R.id.reading_way);
        reading_classfy=findViewById(R.id.reading_classfy);
        adressButton=findViewById(R.id.reading_adress_button);
        adressText=findViewById(R.id.reading_adress_textview);
    }
    public void onBackPressed()
    {
        finish();
    }
    }
