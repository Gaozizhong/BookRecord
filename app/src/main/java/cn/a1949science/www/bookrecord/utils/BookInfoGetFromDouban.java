package cn.a1949science.www.bookrecord.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import cn.a1949science.www.bookrecord.bean.BookInfo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 通过ISBN从豆瓣获取图书信息
 * 工具类
 * Created by 高子忠 on 2018/1/10.
 */

public class BookInfoGetFromDouban {

    //从豆瓣服务器获取相应的图书信息JSON
    public static BookInfo BookInfoGet(final String ISBN_scan) throws IOException {
        //HttpURLConnection实现
        /*HttpURLConnection connection = null;
        BufferedReader reader = null;
        try{
            URL url = new URL("https://api.douban.com/v2/book/isbn/" + result);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            InputStream in = connection.getInputStream();
            //下面对获取到的输入流进行读取
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            decodeBookInfo(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }*/
        //okHttp实现
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.douban.com/v2/book/isbn/" + ISBN_scan)
                .build();
        Response response = client.newCall(request).execute();
        String responseData = response.body().string();
        //解析从豆瓣传回来的json数据
        JSONObject jsonObject = JSON.parseObject(responseData);
        BookInfo bookInfo = new BookInfo();
        String imageUrl = jsonObject.getString("image");
        bookInfo.setImageUrl(imageUrl);
        String bookName = jsonObject.getString("title");
        bookInfo.setBookName(bookName);
        String publishDate = jsonObject.getString("pubdate");
        bookInfo.setPublishDate(publishDate);
        String rating = jsonObject.getString("rating");
        JSONObject ratingObject = JSON.parseObject(rating);
        rating = ratingObject.getString("average");
        bookInfo.setRating(rating);
        String authorName = jsonObject.getString("author");
        bookInfo.setAuthorName(authorName);
        String publish = jsonObject.getString("publisher");
        bookInfo.setPublish(publish);
        String ISBN = jsonObject.getString("isbn13");
        bookInfo.setISBN(ISBN);
        String book_summary = jsonObject.getString("summary");
        bookInfo.setBook_summary(book_summary);
        return bookInfo;
    }

}
