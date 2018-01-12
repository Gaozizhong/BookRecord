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
import cn.a1949science.www.bookrecord.bean.BookComment;
import cn.a1949science.www.bookrecord.widget.CircleImageView;

/**
 * 图书评论列表的适配器
 * Created by 高子忠 on 2018/1/11.
 */

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder>{
    private Context mContext;

    private List<BookComment> mBookCommentList;

    //构造方法
    public CommentListAdapter(List<BookComment> bookCommentList) {
        mBookCommentList = bookCommentList;
    }

    @Override
    public CommentListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (mContext == null) {
            mContext = parent.getContext();
        }
        view = LayoutInflater.from(mContext).inflate(R.layout.comment_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(CommentListAdapter.ViewHolder holder, int position) {
        BookComment bookComment = mBookCommentList.get(position);
        Glide.with(mContext)
                .load(bookComment.getUser_favicon())
                .into(holder.user_favicon);
        holder.user_nickname.setText(bookComment.getUser_nickname());
        holder.read_rating.setRating(Float.parseFloat(bookComment.getRead_rating()));
        holder.read_review.setText(bookComment.getRead_review());
        holder.finish_time.setText("From BookRecord At " + bookComment.getFinish_time());
        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //单击评论的触发事件
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBookCommentList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        CircleImageView user_favicon;
        View mItemView;
        ScaleRatingBar read_rating;
        TextView user_nickname,read_review,finish_time;
        public ViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            cardView = (CardView) itemView;
            user_favicon = itemView.findViewById(R.id.user_favicon);
            user_nickname = itemView.findViewById(R.id.user_nickname);
            read_rating = itemView.findViewById(R.id.read_rating);
            read_review = itemView.findViewById(R.id.read_review);
            finish_time = itemView.findViewById(R.id.finish_time);
        }
    }
}
