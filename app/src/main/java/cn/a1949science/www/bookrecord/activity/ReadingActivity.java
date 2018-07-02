package cn.a1949science.www.bookrecord.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.a1949science.www.bookrecord.R;
import cn.a1949science.www.bookrecord.bean.ReadInfo;
import cn.a1949science.www.bookrecord.bean._User;
import cn.a1949science.www.bookrecord.database.OperationReadInfo;
import cn.a1949science.www.bookrecord.utils.LocationFromGaode;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;

public class ReadingActivity extends AppCompatActivity {

    Context context=ReadingActivity.this;
    Button returnButton,addressButton,reading_button;
    TextView addressText,classfy;
    EditText read_notes,read_reason;
    String book_isbn,book_score,book_way;
    ScaleRatingBar bookRating;
    ReadInfo readInfo;
    private Spinner reading_way=null;
    Boolean bmob_if_hava_read_info = false;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener;
    //记录获取到的地址
    private  String address;
    //定义一个获取定位消息的类
    LocationFromGaode getAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        //从图书详情页面获取信息
        getInfoFromBookInfo();
        findView();
        onClick();
        //放入初始化信息
        initInfo();
    }

    private void getInfoFromBookInfo() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("id_score");
        book_isbn = bundle.getString("bookISBN");
        book_score = bundle.getString("bookScore");
    }

    private void initInfo() {
        //定义传给数据库操作类的handler
        @SuppressLint("HandlerLeak")
        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        List<ReadInfo> list = (List<ReadInfo>) msg.obj;
                        if (list.size()!=0) {
                            ReadInfo readInfo = list.get(0);
                            Log.i("bmob","handler传送成功:"+readInfo.getObjectId());
                            //bookRating.setRating(Float.parseFloat(book_score)/2);
                            //bookRating.setRating(Float.parseFloat(readInfo.getRead_rating())/2);
                            read_notes.setText(readInfo.getRead_notes());
                            read_reason.setText(readInfo.getRead_reason());

                            bmob_if_hava_read_info = true;
                        }
                        break;
                }
            }
        };
        //通过ISBN先查询一下Bmob数据库中是否有此条记录,有的话就显示出来
        _User bmobUser = BmobUser.getCurrentUser(_User.class);
        OperationReadInfo.queryReadInfo(bmobUser, book_isbn,handler);
    }

    //添加控件id
    private void findView()
    {
        returnButton=findViewById(R.id.reading_return_button);
        read_notes = findViewById(R.id.reading_note_text);
        read_reason = findViewById(R.id.reading_reason);
        reading_way=findViewById(R.id.reading_way);
        addressText=findViewById(R.id.reading_adress_textview);
        addressButton=findViewById(R.id.reading_adress_button);
        classfy=findViewById(R.id.reading_classfy);
        reading_button = findViewById(R.id.reading_button);
    }

    //点击事件
    private void onClick(){
        //返回按钮
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        //点击进行定位
        addressButton.setOnClickListener(new View.OnClickListener() {
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

        //读书分类按钮
        classfy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classfyinit();
            }
        });

        //完成记录
        reading_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*final ProgressDialog progress = new ProgressDialog(context);
                progress.setMessage("正在记录...");
                progress.setCanceledOnTouchOutside(false);
                progress.show();

                _User bmobUser = BmobUser.getCurrentUser(_User.class);
                readInfo = new ReadInfo();
                readInfo.setRead_state(1);
                readInfo.setRead_rating(String.valueOf(bookRating.getRating()));
                readInfo.setRead_notes(read_notes.getText().toString());
                readInfo.setRead_reason(read_reason.getText().toString());
                //readInfo.setRead_way();
                Log.i("bmob","现在ReadInfo状态:"+bmob_if_hava_read_info);
                if (!bmob_if_hava_read_info) {
                    OperationReadInfo.addReadInfo(readInfo);
                    progress.dismiss();
                } else {
                    OperationReadInfo.updateReadInfo(readInfo);
                    progress.dismiss();
                }
                finish();*/
            }
        });
  }

    //初始化分类picker
    private void classfyinit(){
        String[] book={"推理悬疑","影视原著","青春言情","经济管理","互联网+","职场提升","成功励志","心理课堂","历史小说","职场小说"};
        final ArrayList<String> bookcalssfy=new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            bookcalssfy.add(book[i]);
        }
        OptionsPickerView  classOptions = new  OptionsPickerBuilder(this, new OnOptionsSelectListener() {
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
                .setContentTextSize(19)//滚轮文字大小
                .setSelectOptions(1)  //设置默认选中项
                .setOutSideCancelable(true)//点击外部dismiss default true
                .isDialog(true)//是否显示为对话框样式
                .build();
        classOptions.setPicker(bookcalssfy);//添加数据源
        classOptions.show();
    }


    public void onBackPressed()
    {
        finish();
    }
}
