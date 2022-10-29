package com.doctris.care.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doctris.care.R;
import com.doctris.care.domain.ListResponse;
import com.doctris.care.entities.Service;
import com.doctris.care.repository.ServiceRepository;
import com.doctris.care.ui.adapter.ServiceAdapter;

import java.util.ArrayList;
import java.util.List;

public class ServiceByCategoryActivity extends AppCompatActivity {
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
    }

    private void onClickBackHome(View view) {
        finish();
    }

    private void hiddenFeature() {
        searchView.setVisibility(View.GONE);
        recyclerViewCategory.setVisibility(View.GONE);
    }

    private void initLinearLayout() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewService.setLayoutManager(linearLayoutManager);

        getServiceData(listService);

        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY > (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) * 0.8 && page < totalPage) {
                page++;
                getServiceData(listService);
            }
        });
    }

    public void getId() {
        String categoryId = getIntent().getStringExtra("id");
        if (categoryId == null) {
            filter = null;
        } else {
            filter = "(category='" + categoryId + "')";
        }
        page = 1;
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
        hiddenFeature();
        bindingActions();
        getId();
        initLinearLayout();
    }
}
