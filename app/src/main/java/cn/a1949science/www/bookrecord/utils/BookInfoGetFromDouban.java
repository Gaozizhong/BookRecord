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
        //JSONObject jsonObject = JSON.parseObject(json);
        BookInfo bookInfo = new BookInfo();

        JSONObject bookItem = JSON.parseObject(json);

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
        //获取豆瓣平均评分
        JSONObject ratingdt=JSON.parseObject(bookItem.getString("rating"));
        String rating=ratingdt.getString("average");
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
        bookInfo.setBook_tags(book_tags.toString());


        /*String imageUrl = jsonObject.getString("image");
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
        bookInfo.setBook_summary(book_summary);*/


        return bookInfo;
    }

}
