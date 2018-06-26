package cn.a1949science.www.bookrecord.database;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cn.a1949science.www.bookrecord.activity.MainActivity;
import cn.a1949science.www.bookrecord.bean.BookInfo;
import cn.a1949science.www.bookrecord.bean.ReadInfo;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 对BookInfo表进行操作
 * 数据库操作类
 * Created by 高子忠 on 2018/6/19.
 */

public class OperationBookInfo {

    /**
     * 查询BookInfo表中是否存在这条信息
     * 数据库操作类
     * 传入值：book_isbn13
     * 返回值：BookInfo对象
     */
    public static void queryBookInfo(String book_isbn13, final Handler handler) {
        //--查询条件
        BmobQuery<BookInfo> query = new BmobQuery<>();
        //ISBN比较
        query.addWhereEqualTo("book_isbn13", book_isbn13);
        query.findObjects(new FindListener<BookInfo>() {
            @Override
            public void done(List<BookInfo> list, BmobException e) {
                if(e==null){
                    //handler+message传递数据
                    Message message = handler.obtainMessage();
                    message.what = 0;
                    //以消息为载体
                    message.obj = list;//这里的list就是查询出list
                    //向handler发送消息
                    handler.sendMessage(message);
                    Log.i("bmob","查询成功:"+list.get(0).getObjectId());
                }else{
                    Log.i("bmob","查询失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });

    }



    /**
     * 向BookInfo表添加这条信息
     * 数据库操作类
     * 传入值：BookInfo对象
     * 返回值：Boolean值
     */
    public static void addBookInfo(BookInfo bookInfo) {
        bookInfo.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    Log.i("bmob","添加成功："+objectId);
                }else{
                    Log.i("bmob","添加失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });

    }



    /**
     * 更新BookInfo表中的这条信息
     * 数据库操作类
     * 传入值：BookInfo对象
     * 返回值：Boolean值
     */
    public static Boolean updateBookInfo(final BookInfo bookInfo) {
        final Boolean[] update = {false};
        final BookInfo[] queryResult = {new BookInfo()};
        @SuppressLint("HandlerLeak")
        //这个应该通过方法里的参数传进来
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        List<BookInfo> list = (List<BookInfo>) msg.obj;
                        if (list != null) {
                            queryResult[0] = list.get(0);
                            bookInfo.update(queryResult[0].getObjectId(), new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e==null){
                                        update[0] = true;
                                        Log.i("bmob","更新成功");
                                    }else{
                                        update[0] = false;
                                        Log.i("bmob","更新失败："+e.getMessage()+","+e.getErrorCode());
                                    }
                                }

                            });
                        }
                        break;
                }
            }
        };
        OperationBookInfo.queryBookInfo(bookInfo.getBook_isbn13(),handler);

        return update[0];
    }


}
