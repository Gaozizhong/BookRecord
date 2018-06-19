package cn.a1949science.www.bookrecord.database;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.a1949science.www.bookrecord.bean.ReadInfo;
import cn.a1949science.www.bookrecord.bean._User;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 对WantRead列表进行操作
 * 数据库操作类
 * Created by 高子忠 on 2018/6/19.
 */

public class OperationReadInfo {
    /**
     * 为read_info添加一行数据
     * 数据库操作类
     * 返回值 Boolean
     */
    public static Boolean addBookInfo(ReadInfo readInfo) {
        final Boolean[] add = {false};
        readInfo.save(new SaveListener<String>() {

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
     * 查询ReadInfo表中是否存在这条信息
     * 数据库操作类
     * 传入值：_User user, String book_isbn
     * 返回值：ReadInfo对象
     */
    public static ReadInfo queryBookInfo(_User user, String book_isbn) {
        final ReadInfo[] readInfo = {new ReadInfo()};
        //--and条件1
        BmobQuery<ReadInfo> eq1 = new BmobQuery<ReadInfo>();
        eq1.addWhereEqualTo("book_isbn", book_isbn);//ISBN比较
        //--and条件2
        BmobQuery<ReadInfo> eq2 = new BmobQuery<ReadInfo>();
        eq2.addWhereGreaterThanOrEqualTo("user_id", user);//用户ID

        //最后组装完整的and条件
        List<BmobQuery<ReadInfo>> andQuerys = new ArrayList<BmobQuery<ReadInfo>>();
        andQuerys.add(eq1);
        andQuerys.add(eq2);
        //查询符合整个and条件的信息
        BmobQuery<ReadInfo> query = new BmobQuery<ReadInfo>();
        query.and(andQuerys);
        query.include("user_id");//查询结果包含user_id
        query.findObjects(new FindListener<ReadInfo>() {
            @Override
            public void done(List<ReadInfo> object, BmobException e) {
                if(e==null){
                    //toast("查询年龄6-29岁之间，姓名以'y'或者'e'结尾的人个数："+object.size());
                    readInfo[0] = object.get(0);
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
        return readInfo[0];
    }


    /**
     * 对ReadInfo表中存在某条信息进行更改
     * 数据库操作类
     * 传入值：带有User_id和Book_isbn以及更新信息的ReadInfo
     * 返回值：NUll
     */
    public static void updateReadInfo(ReadInfo readInfo) {
        ReadInfo queryResult = OperationReadInfo.queryBookInfo(readInfo.getUser_id(),readInfo.getBook_isbn());
        readInfo.update(queryResult.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.i("bmob","更新成功");
                }else{
                    Log.i("bmob","更新失败："+e.getMessage()+","+e.getErrorCode());
                }
            }

        });

    }

    /**
     * 对ReadInfo表中存在某条信息进行删除
     * 数据库操作类
     * 返回值：Boolean 是否更新
     */
    public static Boolean deleteBookInfo(_User user, String book_isbn) {
        final Boolean[] delete = {false};
        ReadInfo queryResult = OperationReadInfo.queryBookInfo(user,book_isbn);
        ReadInfo r1=new ReadInfo();
        r1.setObjectId(queryResult.getObjectId());
        r1.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    delete[0] = true;
                    Log.i("bmob","成功");

                }else{
                    delete[0] = true;
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
        return delete[0];
    }

}
