package cn.a1949science.www.bookrecord.database;

import android.os.Looper;
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
    public static BookInfo queryBookInfo(String book_isbn13) {
        final BookInfo[] bookInfo1 = {new BookInfo()};
        //--查询条件
        BmobQuery query =new BmobQuery("book_info");
        //ISBN比较
        query.addWhereEqualTo("book_isbn13", book_isbn13);
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                if(e==null){
                    List<BookInfo> bookInfo = JSON.parseArray(jsonArray.toString(), BookInfo.class);
                    bookInfo1[0] = bookInfo.get(0);
                    Log.i("bmob","查询成功"+bookInfo1[0].getObjectId());
                }else{
                    Log.i("bmob","查询失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });

        return bookInfo1[0];
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
    public static Boolean updateBookInfo(String objectId,BookInfo bookInfo) {
        final Boolean[] update = {false};
        bookInfo.update(objectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.i("bmob","更新成功");
                    update[0] =true;
                }else{
                    Log.i("bmob","更新失败："+e.getMessage()+","+e.getErrorCode());
                    update[0] =false;
                }
            }

        });

        return update[0];
    }


}
