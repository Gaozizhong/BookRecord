package cn.a1949science.www.bookrecord.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.List;

import cn.a1949science.www.bookrecord.R;
import cn.a1949science.www.bookrecord.activity.BookInfoActivity;
import cn.a1949science.www.bookrecord.activity.MainActivity;
import cn.a1949science.www.bookrecord.activity.ReadingActivity;
import cn.a1949science.www.bookrecord.activity.SeenActivity;
import cn.a1949science.www.bookrecord.bean.BookInfo;

/**
 * 首页列表的适配器
 * Created by 高子忠 on 2018/1/9.
 */

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder>{

    private Context mContext;

    private List<BookInfo> mBookInfoList;

    private String mStatus;

    //构造方法
    public BookListAdapter(List<BookInfo> bookInfoList,String status) {
        mBookInfoList = bookInfoList;
        mStatus = status;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (mContext == null) {
            mContext = parent.getContext();
        }
        switch (mStatus) {
            case "want":
                view = LayoutInflater.from(mContext).inflate(R.layout.want_to_read_item, parent, false);
                break;
            case "reading":
                view = LayoutInflater.from(mContext).inflate(R.layout.reading_item, parent, false);
                break;
            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.seen_item, parent, false);
                break;
        }

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final BookInfo bookInfo = mBookInfoList.get(position);
        holder.bookName.setText(bookInfo.getBook_name());
        Glide.with(mContext)
                .load(bookInfo.getBook_image())
                .into(holder.bookImage);
        holder.publicDate.setText(bookInfo.getBook_publish_date());
        holder.authorName.setText(bookInfo.getBook_author());
        holder.publicer.setText(bookInfo.getBook_publisher());
        holder.simpleRatingBar.setRating(Float.parseFloat(bookInfo.getBook_rating())/2);
        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,BookInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bookInfo", bookInfo);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                //overridePendingTransition(R.anim.slide_right_in,R.anim.slide_left_out);
            }
        });

        switch (mStatus) {
            case "want":
                holder.rating.setText(bookInfo.getBook_rating()+" FROM豆瓣");
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext,ReadingActivity.class);
                        mContext.startActivity(intent);
                        //overridePendingTransition(R.anim.slide_right_in,R.anim.slide_left_out);
                    }
                });
                break;
            case "reading":
                holder.rating.setText(bookInfo.getBook_rating());
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext,SeenActivity.class);
                        mContext.startActivity(intent);
                        //overridePendingTransition(R.anim.slide_right_in,R.anim.slide_left_out);
                    }
                });
                break;
            default:
                holder.rating.setText(bookInfo.getBook_rating());
                break;
        }
    }



    @Override
    public int getItemCount() {
        return mBookInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        Button button;
        View mItemView;
        ImageView bookImage;
        ScaleRatingBar simpleRatingBar;
        TextView bookName,publicDate,rating,authorName,publicer;

        ViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            cardView = (CardView) itemView;
            bookImage = itemView.findViewById(R.id.book_image);
            bookName = itemView.findViewById(R.id.book_name);
            publicDate = itemView.findViewById(R.id.public_date);
            rating = itemView.findViewById(R.id.rating);
            authorName = itemView.findViewById(R.id.author_name);
            publicer = itemView.findViewById(R.id.public_name);
            simpleRatingBar = itemView.findViewById(R.id.simpleRatingBar);
            switch (mStatus) {
                case "want":
                    button = itemView.findViewById(R.id.button_reading);
                    break;
                case "reading":
                    button = itemView.findViewById(R.id.button_seen);
                    break;
                default:
                    break;
            }
        }
    }

}
