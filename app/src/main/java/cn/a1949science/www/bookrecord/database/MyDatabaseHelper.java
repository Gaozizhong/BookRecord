package cn.a1949science.www.bookrecord.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.a1949science.www.bookrecord.R;


public class MyDatabaseHelper extends SQLiteOpenHelper {
    //指定数据库的名字和版本号
    final String book_info_listview="create table book_info_listview(_id integer primary key autoincrement,icon BLOB,usernick TEXT,rate INTEGER,comment TEXT,data TEXT)";//book_info中用户评论信息的缓存数据库,_id为主码
    final String book_info_bookinformation="create table book_info_bookinformation(_id integer primary key autoincrement,book BLOB,bookName TEXT,rate INTEGER,bookScore TEXT,bookWriter TEXT,bookPressName TEXT,bookPressData TEXT,bookISBN TEXT,bookcontext TEXT)";//book_info中图书信息的缓存数据库，表的信息与BookInfoComment类相对应
    private BitmapBytes bb;//Bitmap和byte格式相互转化的类
    public MyDatabaseHelper(Context context, String name,int version) {
        super(context, name, null, version);
        bb=new BitmapBytes();
    }
    //第一次打开软件会创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(book_info_bookinformation);
            db.execSQL(book_info_listview);
    }
    //当数据库修改后会更新数据库
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insertBookInfoListview(Context context,MyDatabaseHelper db)//后期有网络数据的时候会加上参数
    {//将数据插入数据库
        Bitmap bookImage=null;
        final BitmapFactory.Options options = new BitmapFactory.Options();
        bookImage= BitmapFactory.decodeResource(context.getResources(), R.mipmap.bookdemo);
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inDither = true;
        byte[] bookByte=bb.bitmapIntobytes(bookImage);
        if (bookImage!=null&&!bookImage.isRecycled())//回收资源
        {
            bookImage.recycle();
            bookImage=null;
        }
       // db.getReadableDatabase().execSQL("delete from book_info_listview");
        //将参数传递给insertBookInfoListview函数
        insertBookInfoListview(db.getReadableDatabase(),bookByte,context.getResources().getString(R.string.usernick),3,context.getResources().getString(R.string.comment),context.getResources().getString(R.string.data));
    }

    private void insertBookInfoListview(SQLiteDatabase db, byte[] bookByte, String usernick, int i, String comment, String data) {
        db.execSQL("insert into book_info_listview values(null,?,?,?,?,?)",new Object[]{bookByte,usernick,i,comment,data});
    }

    //将对BookInfo表的查询结果放到ArrayList中，每项包含一个hashmap，每个map包含一行的值
    public ArrayList<Map<String,Object>> resultBookInfoListview(Context context,MyDatabaseHelper db)
    {
        Cursor cursor=db.getWritableDatabase().rawQuery("select * from book_info_listview",null);
        ArrayList<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
        while(cursor.moveToNext())
        {
            Map<String,Object> map=new HashMap<>();
            map.put("icon",bb.bytesIntobitmap(cursor.getBlob(1)));
            map.put("usernick",cursor.getString(2));
            map.put("rate",cursor.getInt(3));
            map.put("comment",cursor.getString(4));
            map.put("data",cursor.getString(5));
            result.add(map);
        }
        cursor.close();
        return result;
    }

}
