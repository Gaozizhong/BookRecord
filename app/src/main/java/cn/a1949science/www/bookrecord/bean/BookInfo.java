package cn.a1949science.www.bookrecord.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * 图书信息类
 * Created by 高子忠 on 2018/1/7.
 */

public class BookInfo extends BmobObject implements Serializable{

    private Integer book_id;

    private String book_name;

    private String book_origin_title;

    private String book_image;

    private String book_author;

    private String book_translator;

    private String book_summary;

    private String book_isbn10;

    private String book_isbn13;

    private String book_publisher;

    private String book_publish_date;

    private String book_rating;

    private String book_author_intro;

    private String book_price;

    private String book_pages;

    private String book_tags;

    public BookInfo() {
        this.setTableName("book_info");
    }

    public BookInfo(String book_image, String book_name, String book_publish_date
            , String book_rating, String book_author, String book_publisher) {
        this.book_image = book_image;
        this.book_name = book_name;
        this.book_publish_date = book_publish_date;
        this.book_rating = book_rating;
        this.book_author = book_author;
        this.book_publisher = book_publisher;
        this.book_isbn13 = book_isbn13;
        this.book_summary = book_summary;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    public Integer getBook_id() {
        return book_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_origin_title() {
        return book_origin_title;
    }

    public void setBook_origin_title(String book_origin_title) {
        this.book_origin_title = book_origin_title;
    }

    public String getBook_image() {
        return book_image;
    }

    public void setBook_image(String book_image) {
        this.book_image = book_image;
    }

    public String getBook_author() {
        return book_author;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public String getBook_translator() {
        return book_translator;
    }

    public void setBook_translator(String book_translator) {
        this.book_translator = book_translator;
    }

    public String getBook_summary() {
        return book_summary;
    }

    public void setBook_summary(String book_summary) {
        this.book_summary = book_summary;
    }

    public String getBook_isbn10() {
        return book_isbn10;
    }

    public void setBook_isbn10(String book_isbn10) {
        this.book_isbn10 = book_isbn10;
    }

    public String getBook_isbn13() {
        return book_isbn13;
    }

    public void setBook_isbn13(String book_isbn13) {
        this.book_isbn13 = book_isbn13;
    }

    public String getBook_publisher() {
        return book_publisher;
    }

    public void setBook_publisher(String book_publisher) {
        this.book_publisher = book_publisher;
    }

    public String getBook_publish_date() {
        return book_publish_date;
    }

    public void setBook_publish_date(String book_publish_date) {
        this.book_publish_date = book_publish_date;
    }

    public String getBook_rating() {
        return book_rating;
    }

    public void setBook_rating(String book_rating) {
        this.book_rating = book_rating;
    }

    public String getBook_author_intro() {
        return book_author_intro;
    }

    public void setBook_author_intro(String book_author_intro) {
        this.book_author_intro = book_author_intro;
    }

    public String getBook_price() {
        return book_price;
    }

    public void setBook_price(String book_price) {
        this.book_price = book_price;
    }

    public String getBook_pages() {
        return book_pages;
    }

    public void setBook_pages(String book_pages) {
        this.book_pages = book_pages;
    }

    public String getBook_tags() {
        return book_tags;
    }

    public void setBook_tags(String book_tags) {
        this.book_tags = book_tags;
    }
}
