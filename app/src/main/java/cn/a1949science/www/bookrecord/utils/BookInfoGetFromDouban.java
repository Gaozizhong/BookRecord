package cn.a1949science.www.bookrecord.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import cn.a1949science.www.bookrecord.bean.BookInfo;

/**
 * 通过ISBN从豆瓣获取图书信息
 * 工具类
 * Created by 高子忠 on 2018/1/10.
 */

public class BookInfoGetFromDouban {

    //解析从豆瓣服务器获取相应的图书信息JSON
    public static BookInfo parsingBookInfo(final String json) throws IOException {
        //解析从豆瓣传回来的json数据
        JSONObject jsonObject = JSON.parseObject(json);
        BookInfo bookInfo = new BookInfo();
        String imageUrl = jsonObject.getString("image");
        bookInfo.setBook_image(imageUrl);
        String bookName = jsonObject.getString("title");
        bookInfo.setBook_name(bookName);
        String publishDate = jsonObject.getString("pubdate");
        bookInfo.setBook_publish_date(publishDate);
        String rating = jsonObject.getString("rating");
        JSONObject ratingObject = JSON.parseObject(rating);
        rating = ratingObject.getString("average");
        bookInfo.setBook_rating(rating);
        //解析作者组
        JSONArray authors = jsonObject.getJSONArray("author");
        StringBuilder book_author = new StringBuilder();
        for (int i = 0;i<authors.size();i++) {
            book_author.append(" ").append(authors.get(i));
        }
        bookInfo.setBook_author(book_author.toString());
        String publish = jsonObject.getString("publisher");
        bookInfo.setBook_publisher(publish);
        String ISBN = jsonObject.getString("isbn13");
        bookInfo.setBook_isbn13(ISBN);
        String book_summary = jsonObject.getString("summary");
        bookInfo.setBook_summary(book_summary);
        return bookInfo;
    }

}
