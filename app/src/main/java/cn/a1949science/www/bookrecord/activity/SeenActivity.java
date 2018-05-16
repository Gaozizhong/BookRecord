package cn.a1949science.www.bookrecord.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.util.ArrayList;

import cn.a1949science.www.bookrecord.R;
import cn.a1949science.www.bookrecord.utils.LocationFromGaode;

public class SeenActivity extends AppCompatActivity {
    Context context=SeenActivity.this;
    private Button addresButton;
    private TextView addressText,classfy;
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
    private void findView() {
        returnButton=findViewById(R.id.seen_return);
        addresButton=findViewById(R.id.seen_adress_button);
        addressText=findViewById(R.id.seen_adress_textview);
        classfy=findViewById(R.id.seen_classfy);
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
        classfy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classfyinit();
            }
        });
    }

    //        初始化分类picker
    private void classfyinit()
    {
        String[] book={"推理悬疑","影视原著","青春言情","经济管理","互联网+","职场提升","成功励志","心理课堂","历史小说","职场小说"};
        final ArrayList<String> bookcalssfy=new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            bookcalssfy.add(book[i]);
        }
        OptionsPickerView classOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                String selectclass=bookcalssfy.get(options1);
                classfy.setText(selectclass);
            }
        })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("选择图书分类")//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(19)//标题文字大小
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(0XFF47E3E6)//确定按钮文字颜色
                .setCancelColor(0XFF47E3E6)//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setContentTextSize(17)//滚轮文字大小
                .setSelectOptions(1)  //设置默认选中项
                .setOutSideCancelable(true)//点击外部dismiss default true
                .setCyclic(true,true,true)
                .isDialog(true)//是否显示为对话框样式
                .build();
//        修改对话框
        Dialog mDialog = classOptions.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER);

            classOptions.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
            }
        }
        classOptions.setPicker(bookcalssfy);//添加数据源
        classOptions.show();
    }

    public void onBackPressed()
    {
        finish();
    }
}
