package cn.a1949science.www.bookrecord.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.ycl.tabview.library.TabView;
import com.ycl.tabview.library.TabViewChild;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.a1949science.www.bookrecord.R;
import cn.a1949science.www.bookrecord.fragment.ReadingFragment;
import cn.a1949science.www.bookrecord.fragment.SeenFragment;
import cn.a1949science.www.bookrecord.fragment.WantFragment;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, WantFragment.OnFragmentInteractionListener
        ,ReadingFragment.OnFragmentInteractionListener,SeenFragment.OnFragmentInteractionListener {

    Context mContext = MainActivity.this;
    private long exitTime = 0;
    Toolbar toolbar;
    DrawerLayout drawer;
    View headerLayout;
    TabView tabView;
    MaterialSearchView searchView;
    private int REQUEST_CODE = 5,REQUEST_CAMERA_PERMISSION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*沉浸式标题栏*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        initView();
        onClick();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headerLayout = navigationView.getHeaderView(0);
        headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent it = new Intent(mContext,User_Info.class);
                //startActivity(it);
                //overridePendingTransition(R.anim.slide_right_in,R.anim.slide_left_out);
            }
        });
    }

    //点击事件
    private void onClick() {
        //对搜索的文字监听
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Snackbar.make(findViewById(R.id.container), "Query: " + query, Snackbar.LENGTH_LONG)
                        .show();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        //
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
            //Do some magic
            }
        });

        //点击事件
        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(mContext,"第"+i+"行",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initView() {
        //执行二维码扫描的初始化操作
        ZXingLibrary.initDisplayOpinion(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        //为底部导航栏添加数据源
        List<TabViewChild> tabViewChildList=new ArrayList<>();
        TabViewChild tabViewChild01=new TabViewChild(R.drawable.wanting,R.drawable.wanting,"想读", WantFragment.newInstance("想读","1"));
        TabViewChild tabViewChild02=new TabViewChild(R.drawable.reading,R.drawable.reading,"在读",  ReadingFragment.newInstance("在读","1"));
        TabViewChild tabViewChild03=new TabViewChild(R.drawable.seen,R.drawable.seen,"读过",  SeenFragment.newInstance("读过","1"));
        tabViewChildList.add(tabViewChild01);
        tabViewChildList.add(tabViewChild02);
        tabViewChildList.add(tabViewChild03);
        tabView = findViewById(R.id.tabView);
        tabView.setTabViewChild(tabViewChildList,getSupportFragmentManager());
        searchView = findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        //Add suggestions
        searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));

    }

    //返回键监听事件
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if(searchView.isSearchOpen()){
            searchView.closeSearch();
        }//点击两次返回键退出程序
        else if(System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    //toolbar右面按钮
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    //toolbar右面按钮的事件监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        int menuItemId = item.getItemId();
        if (menuItemId == R.id.action_scan) {
            //如果有权限
            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                Intent it = new Intent(mContext, CaptureActivity.class);
                startActivityForResult(it, REQUEST_CODE);
            } else {//没有权限
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},REQUEST_CAMERA_PERMISSION);
            }

        }
        return true;
    }

    //侧滑菜单中的点击事件
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_statistic) {
            // Handle the camera action
        } else if (id == R.id.nav_advice) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_update) {

        }else if (id == R.id.nav_quit) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    getBookInfo(result);
                    //Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    //从豆瓣服务器获取相应的图书信息
    private void getBookInfo(final String result) {
        //开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                //HttpURLConnection实现
                /*HttpURLConnection connection = null;
                BufferedReader reader = null;
                try{
                    URL url = new URL("https://api.douban.com/v2/book/isbn/" + result);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    //下面对获取到的输入流进行读取
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    decodeBookInfo(response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }*/
                //okHttp实现
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://api.douban.com/v2/book/isbn/" + result)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    decodeBookInfo(response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //解析从豆瓣传回来的json数据
    private void decodeBookInfo(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, response , Toast.LENGTH_LONG).show();
            }
        });
    }

}
