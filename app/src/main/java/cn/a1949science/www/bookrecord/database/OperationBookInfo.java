package cn.a1949science.www.bookrecord.database;

import android.util.Log;

import java.util.List;

import cn.a1949science.www.bookrecord.bean.BookInfo;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
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
    public static BookInfo queryBookInfo(final String book_isbn13) {
        final BookInfo[] bookInfo = {null};
        //--查询条件
        BmobQuery<BookInfo> query = new BmobQuery<BookInfo>();
        query.addWhereEqualTo("book_isbn13", book_isbn13);//ISBN比较

        query.findObjects(new FindListener<BookInfo>() {
            @Override
            public void done(List<BookInfo> object, BmobException e) {
                if(e==null){
                    bookInfo[0] = object.get(0);
                    Log.i("bmob","成功");
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
        return bookInfo[0];
    }



    /**
     * 向BookInfo表添加这条信息
     * 数据库操作类
     * 传入值：BookInfo对象
     * 返回值：Boolean值
     */
    public static Boolean addBookInfo(BookInfo bookInfo) {
        final Boolean[] add = {false};
        bookInfo.save(new SaveListener<String>() {

            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    //toast("创建数据成功：" + objectId);
                    add[0]=true;
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    add[0]=false;
                }
            }
        });
        return add[0];

    }



    /**
     * 更新BookInfo表中的这条信息
     * 数据库操作类
     * 传入值：BookInfo对象
     * 返回值：Boolean值
     */
    public static Boolean updateBookInfo(BookInfo bookInfo) {
        final Boolean[] update = {false};
        BookInfo queryResult = OperationBookInfo.queryBookInfo(bookInfo.getBook_isbn13());
        bookInfo.update(queryResult.getObjectId(), new UpdateListener() {
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
