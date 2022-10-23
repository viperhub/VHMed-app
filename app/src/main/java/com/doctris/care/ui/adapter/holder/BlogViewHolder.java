package com.doctris.care.ui.adapter.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doctris.care.R;
import com.doctris.care.entities.Blog;
import com.doctris.care.ui.activity.BlogDetailActivity;
import com.doctris.care.utils.GlideUtil;

public class BlogViewHolder extends RecyclerView.ViewHolder {

    private TextView tvBlogId;
    private ImageView imageBlog;
    private TextView tvBlogTittle;
    private TextView tvBlogDescription;
    private TextView tvBlogCategory;
    private TextView tvDateCreated;

    private final Context context;

    public BlogViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        bindingViews(itemView);
        itemView.setOnClickListener(this::onClickServiceItem);
    }

    private void onClickServiceItem(View view) {
        Intent intent = new Intent(context, BlogDetailActivity.class);
        intent.putExtra("id", tvBlogId.getText().toString());
        context.startActivity(intent);
    }

    private void bindingViews(View itemView){
        tvBlogId = itemView.findViewById(R.id.tv_id_blog);
        imageBlog = itemView.findViewById(R.id.img_blog);
        tvBlogTittle = itemView.findViewById(R.id.tv_tittle_blog);
        tvBlogDescription = itemView.findViewById(R.id.tv_description_blog);
        tvBlogCategory = itemView.findViewById(R.id.tv_category_blog);
        tvDateCreated = itemView.findViewById(R.id.tv_time_created_blog);
    }

    public void setBlogItem(Blog blogItem){
        tvBlogId.setText(String.valueOf(blogItem.getId()));
        GlideUtil.load(imageBlog, blogItem.getImage());
        tvBlogTittle.setText(blogItem.getTittle());
        tvBlogDescription.setText(blogItem.getDescription());
        tvBlogCategory.setText(blogItem.getExpand().getCategory().getCategoryName());
        tvDateCreated.setText(blogItem.getDateCreated());
    }
}
