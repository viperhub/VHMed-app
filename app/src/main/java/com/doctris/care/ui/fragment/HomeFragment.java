package com.doctris.care.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doctris.care.R;
import com.doctris.care.domain.ListResponse;
import com.doctris.care.entities.Blog;
import com.doctris.care.entities.Doctor;
import com.doctris.care.entities.Patient;
import com.doctris.care.entities.Service;
import com.doctris.care.repository.BlogRepository;
import com.doctris.care.repository.DoctorRepository;
import com.doctris.care.repository.ServiceRepository;
import com.doctris.care.storage.SharedPrefManager;
import com.doctris.care.ui.activity.BlogActivity;
import com.doctris.care.ui.activity.DoctorActivity;
import com.doctris.care.ui.activity.PatientInfoActivity;
import com.doctris.care.ui.activity.ServiceActivity;
import com.doctris.care.ui.adapter.BlogHorizontalAdapter;
import com.doctris.care.ui.adapter.CartHorizontalAdapter;
import com.doctris.care.utils.GlideUtil;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {
    private TextView tvName;
    private TextView tvContent;
    private CircleImageView ivAvatar;
    private TextView tvNameInfo;
    private TextView tvBirthDayInfo;
    private TextView tvGenderInfo;
    private TextView tvSeeAllService;
    private TextView tvSeeAllDoctor;
    private TextView tvSeeAllBlog;
    private RecyclerView rvService;
    private RecyclerView rvDoctor;
    private RecyclerView rvBlog;
    private CardView cvInfo;
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = getActivity();
        if (isAdded() && activity != null) {
            bindingView(view);
            bindingActions();
            setContentByTime();
            initService();
            initDoctor();
            initBlog();
            initData();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = context instanceof Activity ? (Activity) context : null;
    }

    private void bindingView(View view) {
        tvName = view.findViewById(R.id.tv_name);
        tvContent = view.findViewById(R.id.tv_content);
        ivAvatar = view.findViewById(R.id.iv_avatar);
        tvNameInfo = view.findViewById(R.id.tv_full_name);
        tvBirthDayInfo = view.findViewById(R.id.tv_birthday);
        tvGenderInfo = view.findViewById(R.id.tv_gender);
        tvSeeAllService = view.findViewById(R.id.tv_see_all_service);
        rvService = view.findViewById(R.id.rv_service);
        rvDoctor = view.findViewById(R.id.rv_doctor);
        rvBlog = view.findViewById(R.id.rv_blog);
        tvSeeAllDoctor = view.findViewById(R.id.tv_see_all_doctor);
        tvSeeAllBlog = view.findViewById(R.id.tv_see_all_blog);
        cvInfo = view.findViewById(R.id.patientInfo);
    }

    private void bindingActions() {
        tvSeeAllService.setOnClickListener(this::onClickSeeAllService);
        tvSeeAllDoctor.setOnClickListener(this::onClickSeeAllDoctor);
        tvSeeAllBlog.setOnClickListener(this::onClickSeeAllBlog);
        cvInfo.setOnClickListener(this::onClickInfo);
    }

    private void onClickSeeAllBlog(View view) {
        Intent intent = new Intent(activity, BlogActivity.class);
        startActivity(intent);
    }

    private void onClickInfo(View view) {
        Intent intent = new Intent(activity, PatientInfoActivity.class);
        startActivity(intent);
    }

    private void onClickSeeAllDoctor(View view) {
        Intent intent = new Intent(activity, DoctorActivity.class);
        startActivity(intent);
    }

    private void onClickSeeAllService(View view) {
        Intent intent = new Intent(getActivity(), ServiceActivity.class);
        startActivity(intent);
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        Patient patient = SharedPrefManager.getInstance().get("patient", Patient.class);
        if (patient != null) {
            tvName.setText("Xin chào " + getName(patient.getName()));
            tvNameInfo.setText(patient.getName());
            tvBirthDayInfo.setText(convertDate(patient.getDateOfBirth()));
            tvGenderInfo.setText(patient.isGender() ? "Nam" : "Nữ");
            GlideUtil.load(ivAvatar, patient.getAvatar());
        }
    }

    private void initBlog() {
        LinearLayoutManager linearLayoutManagerHORIZONTAL = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        rvBlog.setLayoutManager(linearLayoutManagerHORIZONTAL);
        LiveData<ListResponse<Blog>> blogLiveData = BlogRepository.getInstance().getBlog(1, 3, "-created", null);
        blogLiveData.observe(getViewLifecycleOwner(), blogs -> {
            if (blogs != null) {
                BlogHorizontalAdapter adapter = new BlogHorizontalAdapter(blogs.getItems(), activity);
                rvBlog.setAdapter(adapter);
            }
        });

    }

    private void initService() {
        rvService.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        LiveData<ListResponse<Service>> serviceLiveData = ServiceRepository.getInstance().getServices(1, 10, null, null);
        serviceLiveData.observe(getViewLifecycleOwner(), services -> {
            if (services != null) {
                CartHorizontalAdapter<Service> adapter = new CartHorizontalAdapter<>(services.getItems(), activity);
                rvService.setAdapter(adapter);
            }
        });
    }

    private void initDoctor() {
        rvDoctor.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        LiveData<ListResponse<Doctor>> doctorLiveData = DoctorRepository.getInstance().getDoctors(1, 10, null, null);
        doctorLiveData.observe(getViewLifecycleOwner(), doctors -> {
            if (doctors != null) {
                CartHorizontalAdapter<Doctor> adapter = new CartHorizontalAdapter<>(doctors.getItems(), activity);
                rvDoctor.setAdapter(adapter);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setContentByTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour < 12) {
            tvContent.setText("Chúc bạn một buổi sáng tốt lành!");
        } else if (hour < 18) {
            tvContent.setText("Chúc bạn một buổi chiều tốt lành!");
        } else {
            tvContent.setText("Chúc bạn một buổi tối tốt lành!");
        }
    }

    private String getName(String name) {
        String[] names = name.split(" ");
        return names[names.length - 1];
    }

    private String convertDate(String date) {
        String[] dates = date.split(" ");
        String[] day = dates[0].split("-");
        return day[2] + "/" + day[1] + "/" + day[0];
    }
}