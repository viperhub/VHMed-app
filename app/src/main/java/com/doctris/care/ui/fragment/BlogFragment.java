package com.doctris.care.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.doctris.care.R;
import com.doctris.care.domain.ListResponse;
import com.doctris.care.entities.Blog;
import com.doctris.care.repository.BlogRepository;
import com.doctris.care.ui.adapter.BlogAdapter;
import com.doctris.care.ui.adapter.BlogHorizontalAdapter;

import java.util.ArrayList;
import java.util.List;

public class BlogFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    private RecyclerView recyclerViewBlog;
    private RecyclerView recyclerViewBlogHorizontal;
    private ProgressBar progressBar;
    private NestedScrollView nestedScrollView;
    private int page = 1;
    private static final int LIMIT = 15;
    private int totalPage = 0;
    private List<Blog> listBlog;
    private List<Blog> listBlogHorizontal;
    private String filter = null;
    private BlogAdapter blogAdapter;
    private Activity activity;

    private void bindingViews(View view) {
        recyclerViewBlogHorizontal = view.findViewById(R.id.recyclerview_blog_horizontal);
        recyclerViewBlog = view.findViewById(R.id.recyclerview_blog);
        progressBar = view.findViewById(R.id.progressBar);
        nestedScrollView = view.findViewById(R.id.idNestedSV);
    }
    private void getBlogData(List<Blog> blogList) {
        progressBar.setVisibility(View.VISIBLE);
        LiveData<ListResponse<Blog>> blogLiveData = BlogRepository.getInstance().getBlog(page, LIMIT, "-created", filter);
        blogLiveData.observe(getViewLifecycleOwner(), blogs -> {
            if (blogs != null) {
                int lastIndex = blogList.isEmpty() ? 0 : blogList.size() - 1;
                totalPage = blogs.getTotalPages();

                if(lastIndex == 0) {
                    blogList.addAll(blogs.getItems());
                    blogAdapter = new BlogAdapter(blogList, activity);
                    recyclerViewBlog.setAdapter(blogAdapter);
                } else {
                    blogList.addAll(lastIndex, blogs.getItems());
                    blogAdapter.notifyItemRangeInserted(lastIndex, blogs.getItems().size());
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void getBlogHorizontalData(List<Blog> listBlogHorizontal){
        LiveData<ListResponse<Blog>> blogHorizontalLiveData = BlogRepository.getInstance().getBlog(1, 5, "-viewer", filter);
        blogHorizontalLiveData.observe(getViewLifecycleOwner(), blogHorizontals -> {
            if (blogHorizontals != null) {
                listBlogHorizontal.addAll(blogHorizontals.getItems());
                BlogHorizontalAdapter blogHorizontalAdapter = new BlogHorizontalAdapter(listBlogHorizontal, activity);
                recyclerViewBlogHorizontal.setAdapter(blogHorizontalAdapter);
            }
        });
    }

    private void initLinearLayout() {
        LinearLayoutManager linearLayoutManagerHORIZONTAL = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewBlogHorizontal.setLayoutManager(linearLayoutManagerHORIZONTAL);
        getBlogHorizontalData(listBlogHorizontal);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = getActivity();
        if (isAdded() && activity != null) {
            bindingViews(view);
            listBlog = new ArrayList<>();
            listBlogHorizontal = new ArrayList<>();
            initLinearLayout();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = context instanceof Activity ? (Activity) context : null;
    }
}