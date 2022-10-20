package com.doctris.care.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doctris.care.R;
import com.doctris.care.domain.ListResponse;
import com.doctris.care.entities.Category;
import com.doctris.care.entities.Doctor;
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

    private void bindingViews() {
        recyclerViewCategory = findViewById(R.id.recyclerview_category);
        recyclerViewDoctor = findViewById(R.id.recyclerview_doctor);
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

    private void initLinearLayout() {
        LinearLayoutManager linearLayoutManagerHORIZONTAL = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategory.setLayoutManager(linearLayoutManagerHORIZONTAL);

        LiveData<List<Category>> categoryLiveData = CategoryRepository.getInstance().getCategories();
        categoryLiveData.observe(this, categories -> {
            if (categories != null) {
                CategoryAdapter categoryAdapter = new CategoryAdapter(categories, this);
                recyclerViewCategory.setAdapter(categoryAdapter);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewDoctor.setLayoutManager(linearLayoutManager);

        List<Doctor> doctorsList = new ArrayList<>();

        LiveData<ListResponse<Doctor>> doctorLiveData = DoctorRepository.getInstance().getDoctors(page, LIMIT, null, null);
        doctorLiveData.observe(this, doctors -> {
            if (doctors != null) {
                totalPage = doctors.getTotalPages();
                doctorsList.addAll(doctors.getItems());
                DoctorAdapter doctorAdapter = new DoctorAdapter(doctorsList , this);
                recyclerViewDoctor.setAdapter(doctorAdapter);
            }
        });

        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight() && page < totalPage) {
                page++;
                progressBar.setVisibility(View.VISIBLE);
                LiveData<ListResponse<Doctor>> doctorLiveData1 = DoctorRepository.getInstance().getDoctors(page, LIMIT, null, null);
                doctorLiveData1.observe(this, doctors -> {
                    if (doctors != null) {
                        totalPage = doctors.getTotalPages();
                        doctorsList.addAll(doctors.getItems());
                        DoctorAdapter doctorAdapter = new DoctorAdapter(doctorsList , this);
                        recyclerViewDoctor.setAdapter(doctorAdapter);
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        bindingViews();
        bindingActions();
        initLinearLayout();
    }
}
