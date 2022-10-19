package com.doctris.care.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.doctris.care.R;
import com.doctris.care.entities.Category;
import com.doctris.care.entities.Service;
import com.doctris.care.repository.CategoryRepository;
import com.doctris.care.repository.ServiceRepository;
import com.doctris.care.ui.adapter.CategoryAdapter;
import com.doctris.care.ui.adapter.ServiceAdapter;

import java.util.List;

public class ServiceActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCategory;
    private RecyclerView recyclerViewService;

    private void bindingViews(){
        recyclerViewCategory = findViewById(R.id.recyclerview_category);
        recyclerViewService = findViewById(R.id.recyclerview_service);
    }

    private void initLinearLayout(){
        LinearLayoutManager linearLayoutManagerHORIZONTAL = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategory.setLayoutManager(linearLayoutManagerHORIZONTAL);

        LiveData<List<Category>> categoryLiveData = CategoryRepository.getInstance().getCategories();
        categoryLiveData.observe(this, categories -> {
            if(categories != null){
                CategoryAdapter categoryAdapter = new CategoryAdapter(categories, this);
                recyclerViewCategory.setAdapter(categoryAdapter);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewService.setLayoutManager(linearLayoutManager);

        LiveData<List<Service>> serviceLiveData = ServiceRepository.getInstance().getServices(1, 10, null, null);
        serviceLiveData.observe(this, services -> {
            if(services != null){
                ServiceAdapter serviceAdapter = new ServiceAdapter(services, this);
                recyclerViewService.setAdapter(serviceAdapter);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        bindingViews();
        initLinearLayout();
    }
}