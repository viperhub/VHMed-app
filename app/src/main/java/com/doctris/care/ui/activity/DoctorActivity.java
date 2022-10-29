package com.doctris.care.ui.activity;

import android.os.Bundle;
import android.os.Handler;
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
import com.doctris.care.entities.Category;
import com.doctris.care.entities.Doctor;
import com.doctris.care.listener.RecyclerTouchListener;
import com.doctris.care.repository.CategoryRepository;
import com.doctris.care.repository.DoctorRepository;
import com.doctris.care.ui.adapter.CategoryAdapter;
import com.doctris.care.ui.adapter.DoctorAdapter;

import java.util.ArrayList;
import java.util.List;

public class DoctorActivity extends AppCompatActivity {
    private RecyclerView recyclerViewCategory;
    private RecyclerView recyclerViewDoctor;
    private ProgressBar progressBar;
    private NestedScrollView nestedScrollView;
    private Button backHome;
    private int page = 1;
    private static final int LIMIT = 10;
    private int totalPage = 0;
    private List<Doctor> listDoctor;
    private String filter = null;
    private List<Category> listCategory;
    private SearchView searchView;

    private void bindingViews() {
        recyclerViewCategory = findViewById(R.id.recyclerview_category);
        recyclerViewDoctor = findViewById(R.id.recyclerview_doctor);
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
                    listDoctor.clear();
                    getDoctorData(listDoctor);
                } else {
                    filter = null;
                    getDoctorData(listDoctor);
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
        listCategory.add(new Category(null, "Tất cả", "", "https://icons.veryicon.com/png/o/miscellaneous/cloud-computing-red-and-blue/select-all-invert.png"));
        LiveData<List<Category>> categoryLiveData = CategoryRepository.getInstance().getCategories();
        categoryLiveData.observe(this, categories -> {
            listCategory.addAll(categories);
            CategoryAdapter categoryAdapter = new CategoryAdapter(listCategory, this, R.layout.category_view_item);
            recyclerViewCategory.setAdapter(categoryAdapter);
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewDoctor.setLayoutManager(linearLayoutManager);
        getDoctorData(listDoctor);
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY > (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) * 0.8 && page < totalPage) {
                page++;
                getDoctorData(listDoctor);
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
                listDoctor.clear();
                getDoctorData(listDoctor);
            }

            @Override
            public void onLongClick(View view, int position) {
                // do nothing
            }
        }));
    }

    private void getDoctorData(List<Doctor> list) {
        progressBar.setVisibility(View.VISIBLE);
        LiveData<ListResponse<Doctor>> doctorLiveData = DoctorRepository.getInstance().getDoctors(page, LIMIT, null, filter);
        doctorLiveData.observe(this, doctors -> {
            if (doctors != null) {
                totalPage = doctors.getTotalPages();
                list.addAll(doctors.getItems());
                DoctorAdapter doctorAdapter = new DoctorAdapter(list, this);
                recyclerViewDoctor.setAdapter(doctorAdapter);
            }
            progressBar.setVisibility(View.GONE);
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        listDoctor = new ArrayList<>();
        bindingViews();
        initLinearLayout();
        bindingActions();
        onclickCategoryItem();
    }
}
