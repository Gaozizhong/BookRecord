package cn.a1949science.www.bookrecord.bean;

import android.graphics.Bitmap;

/**
 * 书的评价信息类
 * Created by 高子忠 on 2018/1/9.
 */
public class BookComment {

    private String user_favicon;

    private String user_nickname;

    private String read_rating;

    private String read_review;

    private String finish_time;

    public BookComment(String user_favicon, String user_nickname, String read_rating,
                       String read_review, String finish_time)
    {
       this.user_favicon=user_favicon;
       this.user_nickname=user_nickname;
       this.read_rating=read_rating;
       this.read_review=read_review;
       this.finish_time=finish_time;
    }

    public String getUser_favicon() {
        return user_favicon;
    }

    public void setUser_favicon(String user_favicon) {
        this.user_favicon = user_favicon;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getRead_rating() {
        return read_rating;
    }

    public void setRead_rating(String read_rating) {
        this.read_rating = read_rating;
    }

    public String getRead_review() {
        return read_review;
    }

    public void setRead_review(String read_review) {
        this.read_review = read_review;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

}
