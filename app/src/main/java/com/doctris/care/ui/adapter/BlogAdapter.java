package com.doctris.care.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doctris.care.R;
import com.doctris.care.entities.Blog;
import com.doctris.care.ui.adapter.holder.BlogViewHolder;

import java.util.List;

public class BlogAdapter extends RecyclerView.Adapter<BlogViewHolder> {

    private final List<Blog> listBlog;
    private final Context mContext;
    private final LayoutInflater layoutInflater;

    public BlogAdapter(List<Blog> listBlog, Context mContext) {
        this.listBlog = listBlog;
        this.mContext = mContext;
        this.layoutInflater = LayoutInflater.from(mContext);
    }


    @NonNull
    @Override
    public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BlogViewHolder(layoutInflater.inflate(R.layout.blog_view_item, parent, false), mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull BlogViewHolder holder, int position) {
        holder.setBlogItem(listBlog.get(position));
    }

    @Override
    public int getItemCount() {
        return listBlog.size();
    }
}
