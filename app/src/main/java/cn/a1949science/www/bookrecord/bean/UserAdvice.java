package cn.a1949science.www.bookrecord.bean;

import java.io.Serializable;


public class UserAdvice implements Serializable {
    private int advice_id;
    private int user_id;
    private String advice_content;
    private String creat_time;
    private String advice_result;
    public UserAdvice()
    {

    }
    public UserAdvice(int advice_id,int user_id,String advice_content,String creat_time,String advice_result)
    {
        this.advice_id=advice_id;
        this.user_id=user_id;
        this.advice_content=advice_content;
        this.creat_time=creat_time;
        this.advice_result=advice_result;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getAdvice_id() {
        return advice_id;
    }

    public String getAdvice_content() {
        return advice_content;
    }

    public String getAdvice_result() {
        return advice_result;
    }

    public String getCreat_time() {
        return creat_time;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setAdvice_content(String advice_content) {
        this.advice_content = advice_content;
    }

    public void setAdvice_id(int advice_id) {
        this.advice_id = advice_id;
    }

    public void setAdvice_result(String advice_result) {
        this.advice_result = advice_result;
    }

    public void setCreat_time(String creat_time) {
        this.creat_time = creat_time;
    }

}
