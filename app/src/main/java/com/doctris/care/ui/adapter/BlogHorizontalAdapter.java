package com.doctris.care.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doctris.care.R;
import com.doctris.care.entities.Blog;
import com.doctris.care.ui.adapter.holder.BlogHorizontalHolder;

import java.util.List;

public class BlogHorizontalAdapter extends RecyclerView.Adapter<BlogHorizontalHolder> {

    private final List<Blog> listBlog;
    private final Context mContext;
    private final LayoutInflater layoutInflater;

    public BlogHorizontalAdapter(List<Blog> listBlog, Context mContext) {
        this.listBlog = listBlog;
        this.mContext = mContext;
        this.layoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public BlogHorizontalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BlogHorizontalHolder(layoutInflater.inflate(R.layout.blog_horizontal_item, parent, false), mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull BlogHorizontalHolder holder, int position) {
        holder.setBlogHorizontalItem(listBlog.get(position));
    }

    @Override
    public int getItemCount() {
        return listBlog.size();
    }
}
