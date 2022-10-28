package com.doctris.care.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doctris.care.R;
import com.doctris.care.domain.ListResponse;
import com.doctris.care.entities.Blog;
import com.doctris.care.repository.BlogRepository;
import com.doctris.care.ui.adapter.BlogAdapter;
import com.doctris.care.ui.adapter.BlogHorizontalAdapter;

import java.util.ArrayList;
import java.util.List;

public class BlogActivity extends AppCompatActivity {

    private RecyclerView recyclerViewBlog;
    private RecyclerView recyclerViewBlogHorizontal;
    private ProgressBar progressBar;
    private NestedScrollView nestedScrollView;
    private Button backHome;
    private int page = 1;
    private static final int LIMIT = 10;
    private int totalPage = 0;
    private List<Blog> listBlog;
    private List<Blog> listBlogHorizontal;
    private String fillter = null;
    private SearchView searchView;

    private void bindingViews() {
        recyclerViewBlogHorizontal = findViewById(R.id.recyclerview_blog_horizontal);
        recyclerViewBlog = findViewById(R.id.recyclerview_blog);
        progressBar = findViewById(R.id.progressBar);
        nestedScrollView = findViewById(R.id.idNestedSV);
        backHome = findViewById(R.id.btn_back);
    }

    private void bindingActions() {
        backHome.setOnClickListener(this::onClickBackHome);
    }

    private void onClickBackHome(View view) {
        finish();
    }

    private void getBlogData(List<Blog> blogList) {
        progressBar.setVisibility(View.VISIBLE);
        LiveData<ListResponse<Blog>> blogLiveData = BlogRepository.getInstance().getBlog(page, LIMIT, null, fillter);
        blogLiveData.observe(this, blogs -> {
            if (blogs != null) {
                totalPage = blogs.getTotalPages();
                blogList.addAll(blogs.getItems());
                BlogAdapter blogAdapter = new BlogAdapter(blogList, this);
                recyclerViewBlog.setAdapter(blogAdapter);
            }
            progressBar.setVisibility(View.GONE);
        });
    }

    private void getBlogHorizontalData(List<Blog> listBlogHorizontal){
        progressBar.setVisibility(View.VISIBLE);
        LiveData<ListResponse<Blog>> blogHorizontalLiveData = BlogRepository.getInstance().getBlog(page, 5, "-viewer", fillter);
        blogHorizontalLiveData.observe(this, blogHorizontals -> {
            if (blogHorizontals != null) {
                totalPage = blogHorizontals.getTotalPages();
                listBlogHorizontal.addAll(blogHorizontals.getItems());
                BlogHorizontalAdapter blogHorizontalAdapter = new BlogHorizontalAdapter(listBlogHorizontal, this);
                recyclerViewBlogHorizontal.setAdapter(blogHorizontalAdapter);
            }
            progressBar.setVisibility(View.GONE);
        });
    }

    private void initLinearLayout() {
        LinearLayoutManager linearLayoutManagerHORIZONTAL = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewBlogHorizontal.setLayoutManager(linearLayoutManagerHORIZONTAL);

        getBlogHorizontalData(listBlogHorizontal);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewBlog.setLayoutManager(linearLayoutManager);

        getBlogData(listBlog);

        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            progressBar.setVisibility(View.VISIBLE);
            if (scrollY > (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) * 0.8 && page < totalPage) {
                page++;
                getBlogHorizontalData(listBlogHorizontal);
                getBlogData(listBlog);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        listBlog = new ArrayList<>();
        listBlogHorizontal = new ArrayList<>();
        bindingViews();
        bindingActions();
        initLinearLayout();
    }
}
