package cn.a1949science.www.bookrecord.bean;
//创建一个书的信息类
public class BookInfoComment {
    int icon,rate;
    String usernick,comment,date;
    public BookInfoComment(int icon,String usernick,int rate,String comment,String date)
    {
       this.icon=icon;
       this.usernick=usernick;
       this.rate=rate;
       this.comment=comment;
       this.date=date;
    }
    public int getIcon()
    {
        return icon;
    }

    public String getUsernick() {
        return usernick;
    }

    public String getComment() {
        return comment;
    }

    public String getDate() {
        return date;
    }

    public int getRate() {
        return rate;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setUsernick(String usernick) {
        this.usernick = usernick;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
