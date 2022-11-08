package com.doctris.care.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doctris.care.R;
import com.doctris.care.entities.Comment;
import com.doctris.care.ui.adapter.holder.BlogCommentViewHolder;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<BlogCommentViewHolder> {

    private final List<Comment> commentList;
    private final Context mContext;
    private final LayoutInflater layoutInflater;

    public CommentAdapter(List<Comment> commentList, Context mContext) {
        this.commentList = commentList;
        this.mContext = mContext;
        this.layoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public BlogCommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BlogCommentViewHolder(layoutInflater.inflate(R.layout.comment_item, parent, false), mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull BlogCommentViewHolder holder, int position) {
        holder.setCommentItem(commentList.get(position));
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }
}
