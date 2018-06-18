package cn.a1949science.www.bookrecord.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.a1949science.www.bookrecord.bean.BookInfo;

import static com.amap.api.mapcore2d.q.i;

/**
 * Created by 洋葱 on 2018/6/18.
 */

public class SearchFromDouban {
    //解析从豆瓣服务器获取相应的图书信息JSON
    public static List<BookInfo> parsingBookInfos(final String json) throws IOException {
        //解析从豆瓣传回来的json数据
        JSONObject jsonObject = JSON.parseObject(json);
        List<BookInfo> bookInfos = new ArrayList<BookInfo>();
        int count = jsonObject.getInteger( "count");
        int start =jsonObject.getInteger("start");
        //int total =jsonObject.getInteger("total");
        JSONArray books= jsonObject.getJSONArray("books");
        for(i=start;i<count;i++){
            BookInfo bookInfo=new BookInfo();
            JSONObject bookItem = JSON.parseObject(books.get(i).toString());
            String bookName=bookItem.getString("title");
            bookInfo.setBook_name(bookName);
            String originTitle=bookItem.getString("origin_title");
            bookInfo.setBook_origin_title(originTitle);
            String image=bookItem.getString("image");
            bookInfo.setBook_image(image);
            //解析作者组
            JSONArray authors = bookItem.getJSONArray("author");
            StringBuilder book_author = new StringBuilder();
            for (int i = 0; i < authors.size(); i++) {
                book_author.append(" ").append(authors.get(i));
            }
            bookInfo.setBook_author(book_author.toString());
            String translator = bookItem.getString("translator");
            bookInfo.setBook_translator(translator);
            String summary = bookItem.getString("summary");
            bookInfo.setBook_summary(summary);
            String isbn10 = bookItem.getString("isbn10");
            bookInfo.setBook_isbn10(isbn10);
            String isbn13 = bookItem.getString("isbn13");
            bookInfo.setBook_isbn13(isbn13);
            String publisher = bookItem.getString("publisher");
            bookInfo.setBook_publisher(publisher);
            String publish_date = bookItem.getString("pubdate");
            bookInfo.setBook_publish_date(publish_date);
            String rating = bookItem.getString("rating");
            bookInfo.setBook_rating(rating);
            String author_intro = bookItem.getString("author_intro");
            bookInfo.setBook_author_intro(author_intro);
            String price = bookItem.getString("price");
            bookInfo.setBook_price(price);
            String pages = bookItem.getString("pages");
            bookInfo.setBook_pages(pages);
            //解析标签组
            JSONArray tags = bookItem.getJSONArray("tags");
            StringBuilder book_tags = new StringBuilder();
            for (int j = 0; j< tags.size(); j++) {
                JSONObject tagItem = JSON.parseObject(tags.get(j).toString());
                book_tags.append(" ").append(tagItem.getString("title"));
            }
            bookInfos.add(bookInfo);
        }

        return bookInfos;
    }
}
