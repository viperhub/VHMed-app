package com.doctris.care.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.doctris.care.R;
import com.doctris.care.domain.ListResponse;
import com.doctris.care.entities.Category;
import com.doctris.care.entities.Service;
import com.doctris.care.listener.RecyclerTouchListener;
import com.doctris.care.repository.CategoryRepository;
import com.doctris.care.repository.ServiceRepository;
import com.doctris.care.ui.adapter.CategoryAdapter;
import com.doctris.care.ui.adapter.ServiceAdapter;

import java.util.ArrayList;
import java.util.List;

public class ServiceActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCategory;
    private RecyclerView recyclerViewService;
    private ProgressBar progressBar;
    private NestedScrollView nestedScrollView;
    private Button backHome;
    private int page = 1;
    private static final int LIMIT = 10;
    private int totalPage = 0;
    private List<Service> listService;
    private String filter = null;
    private List<Category> listCategory;
    private SearchView searchView;

    private void bindingViews() {
        recyclerViewCategory = findViewById(R.id.recyclerview_category);
        recyclerViewService = findViewById(R.id.recyclerview_service);
        progressBar = findViewById(R.id.progressBar);
        nestedScrollView = findViewById(R.id.idNestedSV);
        backHome = findViewById(R.id.btn_back);
        searchView = findViewById(R.id.search_service);
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
                    filter = "(category.category_name~'" + newText + "' || name~'" + newText + "')";
                    listService.clear();
                    getServiceData(listService);
                } else {
                    filter = null;
                    getServiceData(listService);
                }
                return false;
            }
        });
    }

    private void onClickBackHome(View view) {
        finish();
    }

    private void initLinearLayout() {
        LinearLayoutManager linearLayoutManagerHORIZONTAL = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategory.setLayoutManager(linearLayoutManagerHORIZONTAL);
        listCategory = new ArrayList<>();
        listCategory.add(new Category(null, "Tất cả", ""));
        LiveData<List<Category>> categoryLiveData = CategoryRepository.getInstance().getCategories();
        categoryLiveData.observe(this, categories -> {
            listCategory.addAll(categories);
            CategoryAdapter categoryAdapter = new CategoryAdapter(listCategory, this);
            recyclerViewCategory.setAdapter(categoryAdapter);
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewService.setLayoutManager(linearLayoutManager);

        getServiceData(listService);

        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            progressBar.setVisibility(View.VISIBLE);
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight() && page < totalPage) {
                page++;
                getServiceData(listService);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void onclickCategoryItem() {
        recyclerViewCategory.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerViewCategory, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String categoryId = listCategory.get(position).getId();
                if (categoryId == null) {
                    filter = null;
                } else {
                    filter = "(category='" + categoryId + "')";
                }
                page = 1;
                listService.clear();
                getServiceData(listService);
            }

            @Override
            public void onLongClick(View view, int position) {
                // do nothing
            }
        }));
    }

    private void getServiceData(List<Service> serviceList) {
        progressBar.setVisibility(View.VISIBLE);
        LiveData<ListResponse<Service>> serviceLiveData = ServiceRepository.getInstance().getServices(page, LIMIT, null, filter);
        serviceLiveData.observe(this, services -> {
            if (services != null) {
                totalPage = services.getTotalPages();
                serviceList.addAll(services.getItems());
                ServiceAdapter serviceAdapter = new ServiceAdapter(serviceList, this);
                recyclerViewService.setAdapter(serviceAdapter);
            }
            progressBar.setVisibility(View.GONE);
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        listService = new ArrayList<>();
        bindingViews();
        bindingActions();
        initLinearLayout();
        onclickCategoryItem();
    }
}