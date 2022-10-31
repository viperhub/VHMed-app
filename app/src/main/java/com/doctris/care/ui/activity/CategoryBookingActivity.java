package com.doctris.care.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doctris.care.R;
import com.doctris.care.entities.Category;
import com.doctris.care.listener.RecyclerTouchListener;
import com.doctris.care.repository.CategoryRepository;
import com.doctris.care.ui.adapter.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class CategoryBookingActivity extends AppCompatActivity {
    private Button btnBack;
    private RecyclerView rvCategory;
    private List<Category> listCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_grid_layout);
        listCategory = new ArrayList<>();
        bindingView();
        initRecyclerView();
        bindingAction();
        onclickCategoryItem();
    }

    private void bindingView() {
        btnBack = findViewById(R.id.btn_back);
        rvCategory = findViewById(R.id.recyclerview_category);
    }

    private void bindingAction() {
        btnBack.setOnClickListener(this::onClickBack);
    }

    private void onClickBack(View view) {
        finish();
        overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
    }

    private void onclickCategoryItem() {
        rvCategory.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvCategory, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String categoryId = listCategory.get(position).getId();
                Intent intent = new Intent(CategoryBookingActivity.this, ServiceByCategoryActivity.class);
                intent.putExtra("id", categoryId);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                // do nothing
            }
        }));
    }

    private void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvCategory.setLayoutManager(gridLayoutManager);
        LiveData<List<Category>> categoryList = CategoryRepository.getInstance().getCategories();
        categoryList.observe(this, categories -> {
            listCategory.addAll(categories);
            CategoryAdapter categoryAdapter = new CategoryAdapter(listCategory, this, R.layout.category_grid_item);
            rvCategory.setAdapter(categoryAdapter);
        });
    }
}
