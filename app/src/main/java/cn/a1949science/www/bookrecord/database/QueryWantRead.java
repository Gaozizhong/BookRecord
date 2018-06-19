package cn.a1949science.www.bookrecord.database;

import cn.a1949science.www.bookrecord.bean.ReadInfo;
import cn.a1949science.www.bookrecord.bean._User;

/**
 * 对WantRead列表进行操作
 * 数据库操作类
 * Created by 高子忠 on 2018/6/19.
 */

public class QueryWantRead {
    /**
     * 查询ReadInfo表中是否存在这条信息
     * 数据库操作类
     * 返回值：ReadInfo对象
     */
    public static ReadInfo queryReadInfo(_User user, String book_isbn) {

        return null;
    }

    /**
     * 对ReadInfo表中存在某条信息进行更改
     * 数据库操作类
     * 返回值：ReadInfo对象
     */
    public static ReadInfo updateReadInfo(ReadInfo bookInfo, String book_isbn) {

        return null;
    }

    /**
     * 对ReadInfo表中存在某条信息进行删除
     * 数据库操作类
     * 返回值：Info对象
     */
    public static ReadInfo deleteReadInfo(ReadInfo bookInfo, String book_isbn) {

        return null;
    }

}
