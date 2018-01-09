package cn.a1949science.www.bookrecord.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.a1949science.www.bookrecord.R;
import cn.a1949science.www.bookrecord.bean.BookInfo;

/**
 * Created by 高子忠 on 2018/1/9.
 */

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder>{

    private Context mContext;

    private List<BookInfo> mBookInfoList;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.want_to_read_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BookInfo bookInfo = mBookInfoList.get(position);
        holder.bookName.setText(bookInfo.getBookName());
        Glide.with(mContext).load(bookInfo.getImageUrl()).into(holder.bookImage);
        holder.publicDate.setText(bookInfo.getPublishDate());
        holder.authorName.setText(bookInfo.getAuthorName());
        holder.publicer.setText(bookInfo.getPublish());
    }

    @Override
    public int getItemCount() {
        return mBookInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView bookImage;
        TextView bookName,publicDate,authorName,publicer;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            bookImage = itemView.findViewById(R.id.want_to_read_item);
            bookName = itemView.findViewById(R.id.book_name);
            publicDate = itemView.findViewById(R.id.public_date);
            authorName = itemView.findViewById(R.id.author_name);
            publicer = itemView.findViewById(R.id.public_name);
        }
    }

    //构造方法
    public BookListAdapter(List<BookInfo> bookInfoList) {
        mBookInfoList = bookInfoList;
    }


}
