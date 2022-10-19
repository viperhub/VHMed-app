package com.doctris.care.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.doctris.care.R;

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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewService.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        bindingViews();
        initLinearLayout();
    }
}