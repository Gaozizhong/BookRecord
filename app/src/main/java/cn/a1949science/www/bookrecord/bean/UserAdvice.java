package cn.a1949science.www.bookrecord.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;


public class UserAdvice extends BmobObject implements Serializable {
    private Integer advice_id;
    private _User user_id;
    private String advice_content;
    private String advice_result;

    public UserAdvice()
    {
        this.setTableName("user_advice");
    }
    public UserAdvice(_User user_id,String advice_content)
    {
        this.user_id=user_id;
        this.advice_content=advice_content;
    }

    public _User getUser_id() {
        return user_id;
    }

    public Integer getAdvice_id() {
        return advice_id;
    }

    public String getAdvice_content() {
        return advice_content;
    }

    public String getAdvice_result() {
        return advice_result;
    }

    public void setUser_id(_User user_id) {
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

}
