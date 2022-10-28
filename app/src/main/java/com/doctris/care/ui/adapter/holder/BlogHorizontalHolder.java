package com.doctris.care.ui.adapter.holder;

import android.content.Context;
import android.content.Intent;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doctris.care.R;
import com.doctris.care.entities.Blog;
import com.doctris.care.ui.activity.BlogDetailActivity;
import com.doctris.care.utils.GlideUtil;

public class BlogHorizontalHolder extends RecyclerView.ViewHolder {

    private TextView tvId;
    private ImageView imgBlog;
    private TextView tvTittle;

    private final Context context;


    public BlogHorizontalHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        bindingViews(itemView);
        itemView.setOnClickListener(this::onClickBlogItem);
    }

    private void onClickBlogItem(View view) {
        Intent intent = new Intent(context, BlogDetailActivity.class);
        intent.putExtra("id", tvId.getText().toString());
        context.startActivity(intent);
    }

    public void bindingViews(View itemView) {
        tvId = itemView.findViewById(R.id.tv_id_blog_horizontal);
        imgBlog = itemView.findViewById(R.id.tv_img_blog_horizontal);
        tvTittle = itemView.findViewById(R.id.tv_tittle_blog_horizontal);
    }
    public void setBlogHorizontalItem(Blog blogHorizontalItem){
        tvId.setText(String.valueOf(blogHorizontalItem.getId()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            GlideUtil.load(imgBlog, blogHorizontalItem.getImage());
            imgBlog.setRenderEffect(RenderEffect.createBlurEffect(1, 1, Shader.TileMode.MIRROR));
        }
        tvTittle.setText(blogHorizontalItem.getTittle());
    }
}
