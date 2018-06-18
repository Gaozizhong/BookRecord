package cn.a1949science.www.bookrecord.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;


public class ReadInfo extends BmobObject implements Serializable {
    private Integer read_id;
    private _User user_id;
    private String book_isbn;
    private Integer read_state;
    private String read_reason;
    private String read_except;
    private BmobDate want_time;
    private String read_notes;
    private String read_way;
    private String read_location;
    private String read_sort;
    private String read_rating;
    private String read_time;
    private String read_review;
    private String finish_time;

    public ReadInfo()
    {
        this.setTableName("book_info");
    }
    public ReadInfo(_User user_id,String book_isbn ,Integer read_state,String read_reason,String read_except,BmobDate want_time)
    {
        this.user_id=user_id;
        this.book_isbn=book_isbn;
        this.read_state=read_state;
        this.read_reason=read_reason;
        this.read_except=read_except;
        this.want_time=want_time;
    }

    public String getBook_isbn() {
        return book_isbn;
    }

    public Integer getRead_id() {
        return read_id;
    }

    public _User getUser_id() {
        return user_id;
    }

    public Integer getRead_state() {
        return read_state;
    }

    public String getRead_except() {
        return read_except;
    }

    public String getRead_location() {
        return read_location;
    }

    public String getRead_notes() {
        return read_notes;
    }

    public String getRead_reason() {
        return read_reason;
    }

    public String getRead_sort() {
        return read_sort;
    }

    public String getRead_way() {
        return read_way;
    }

    public BmobDate getWant_time() {
        return want_time;
    }

    public String getRead_rating() {
        return read_rating;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public String getRead_review() {
        return read_review;
    }

    public String getRead_time() {
        return read_time;
    }

    public void setBook_isbn(String book_isbn) {
        this.book_isbn = book_isbn;
    }

    public void setRead_except(String read_except) {
        this.read_except = read_except;
    }

    public void setRead_id(Integer read_id) {
        this.read_id = read_id;
    }

    public void setRead_location(String read_location) {
        this.read_location = read_location;
    }

    public void setRead_notes(String read_notes) {
        this.read_notes = read_notes;
    }

    public void setRead_rating(String read_rating) {
        this.read_rating = read_rating;
    }

    public void setRead_reason(String read_reason) {
        this.read_reason = read_reason;
    }

    public void setRead_sort(String read_sort) {
        this.read_sort = read_sort;
    }

    public void setRead_state(Integer read_state) {
        this.read_state = read_state;
    }

    public void setRead_way(String read_way) {
        this.read_way = read_way;
    }

    public void setUser_id(_User user_id) {
        this.user_id = user_id;
    }

    public void setWant_time(BmobDate want_time) {
        this.want_time = want_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public void setRead_review(String read_review) {
        this.read_review = read_review;
    }

    public void setRead_time(String read_time) {
        this.read_time = read_time;
    }
}
