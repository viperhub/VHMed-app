package com.doctris.care.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import androidx.appcompat.widget.SearchView;

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
    private static final int LIMIT = 15;
    private int totalPage = 0;
    private List<Blog> listBlog;
    private List<Blog> listBlogHorizontal;
    private String filter = null;
    private SearchView searchView;
    private BlogAdapter blogAdapter;

    private void bindingViews() {
        recyclerViewBlogHorizontal = findViewById(R.id.recyclerview_blog_horizontal);
        recyclerViewBlog = findViewById(R.id.recyclerview_blog);
        progressBar = findViewById(R.id.progressBar);
        nestedScrollView = findViewById(R.id.idNestedSV);
        backHome = findViewById(R.id.btn_back);
        searchView = findViewById(R.id.search_blog);
    }

    private void bindingActions() {
        backHome.setOnClickListener(this::onClickBackHome);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // not support yet
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null && !newText.isEmpty()) {
                    filter = "(category.category_name~'" + newText + "' || tittle~'" + newText + "')";
                    listBlog.clear();
                    getBlogData(listBlog);
                } else {
                    filter = null;
                    getBlogData(listBlog);
                }
                return false;
            }
        });
    }

    private void onClickBackHome(View view) {
        finish();
    }

    private void getBlogData(List<Blog> blogList) {
        progressBar.setVisibility(View.VISIBLE);
        LiveData<ListResponse<Blog>> blogLiveData = BlogRepository.getInstance().getBlog(page, LIMIT, "-created", filter);
        blogLiveData.observe(this, blogs -> {
            if (blogs != null) {
                int lastIndex = blogList.isEmpty() ? 0 : blogList.size() - 1;
                totalPage = blogs.getTotalPages();

                if(lastIndex == 0) {
                    blogList.addAll(blogs.getItems());
                    blogAdapter = new BlogAdapter(blogList, this);
                    recyclerViewBlog.setAdapter(blogAdapter);
                } else {
                    blogList.addAll(lastIndex + 1, blogs.getItems());
                    blogAdapter.notifyItemRangeInserted(lastIndex, blogs.getItems().size());
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void getBlogHorizontalData(List<Blog> listBlogHorizontal){
        LiveData<ListResponse<Blog>> blogHorizontalLiveData = BlogRepository.getInstance().getBlog(1, 5, "-viewer", filter);
        blogHorizontalLiveData.observe(this, blogHorizontals -> {
            if (blogHorizontals != null) {
                listBlogHorizontal.addAll(blogHorizontals.getItems());
                BlogHorizontalAdapter blogHorizontalAdapter = new BlogHorizontalAdapter(listBlogHorizontal, this);
                recyclerViewBlogHorizontal.setAdapter(blogHorizontalAdapter);
            }
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
            if (scrollY > (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) * 0.5 && page < totalPage) {
                page++;
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
