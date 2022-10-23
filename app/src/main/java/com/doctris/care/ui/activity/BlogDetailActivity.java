package com.doctris.care.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doctris.care.R;
import com.doctris.care.domain.ListResponse;
import com.doctris.care.entities.Blog;
import com.doctris.care.repository.BlogRepository;
import com.doctris.care.ui.adapter.BlogDetailAdapter;
import com.doctris.care.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class BlogDetailActivity extends AppCompatActivity {
    private ImageView imvBlog;
    private TextView tvdateCreate;
    private TextView tvTittle;
    private TextView tvDescription;
    private Button btnBack;

    private RecyclerView recyclerViewBlog;
    private ProgressBar progressBar;
    private NestedScrollView nestedScrollView;
    private int page = 1;
    private static final int LIMIT = 10;
    private int totalPage = 0;
    private List<Blog> listBlog;
    private String fillter = null;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_detail);
        listBlog = new ArrayList<>();
        bindingView();
        getData();
        bindingAction();
        initLinearLayout();
    }

    public void getData() {
        Intent intent = getIntent();
        LiveData<Blog> liveData = BlogRepository.getInstance().getBlogById(intent.getStringExtra("id"));
        liveData.observe(this, blog -> {
            if (blog != null){
                GlideUtil.load(imvBlog, blog.getImage());
                tvdateCreate.setText(blog.getDateCreated());
                tvTittle.setText(blog.getTittle());
                tvDescription.setText(blog.getDescription());
            }
        });
    }

    public void bindingView() {
        imvBlog = findViewById(R.id.iv_image_blog);
        tvdateCreate = findViewById(R.id.tv_time_created_blog);
        tvTittle = findViewById(R.id.iv_tittle_blog);
        tvDescription = findViewById(R.id.iv_description_blog);
        btnBack = findViewById(R.id.btn_back);

        recyclerViewBlog = findViewById(R.id.recyclerview_blog);
        progressBar = findViewById(R.id.progressBar);
        nestedScrollView = findViewById(R.id.idNestedSV);
    }

    private void getBlogData(List<Blog> blogList) {
        progressBar.setVisibility(View.VISIBLE);
        LiveData<ListResponse<Blog>> blogLiveData = BlogRepository.getInstance().getBlog(page, LIMIT, "-created", fillter);
        blogLiveData.observe(this, blogs -> {
            if (blogs != null) {
                totalPage = blogs.getTotalPages();
                blogList.addAll(blogs.getItems());

                BlogDetailAdapter blogDetailAdapter = new BlogDetailAdapter(blogList, this);
                recyclerViewBlog.setAdapter(blogDetailAdapter);
            }
            progressBar.setVisibility(View.GONE);
        });
    }

    private void initLinearLayout() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewBlog.setLayoutManager(linearLayoutManager);

        getBlogData(listBlog);

        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            progressBar.setVisibility(View.VISIBLE);
            if (scrollY > (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) * 0.8 && page < totalPage) {
                page++;
                getBlogData(listBlog);
            }
        });
    }

    public void bindingAction(){
        btnBack.setOnClickListener(this::onClickBtnBack);
    }

    private void onClickBtnBack(View view) {
        finish();
    }
}
