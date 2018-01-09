package cn.a1949science.www.bookrecord.bean;

/**
 * 图书信息类
 * Created by 高子忠 on 2018/1/7.
 */

public class BookInfo {

    private String imageUrl;

    private String bookName;

    private String publishDate;

    private String rating;

    private String authorName;

    private String publish;

    public BookInfo(String imageUrl,String bookName,String publishDate,String rating,String authorName,String publish) {
        this.imageUrl = imageUrl;
        this.bookName = bookName;
        this.publishDate = publishDate;
        this.rating = rating;
        this.authorName = authorName;
        this.publish = publish;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRating() {
        return rating;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public String getPublish() {
        return publish;
    }
}
