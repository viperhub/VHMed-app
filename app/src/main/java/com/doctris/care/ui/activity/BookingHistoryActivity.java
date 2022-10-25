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
import com.doctris.care.entities.Booking;
import com.doctris.care.repository.BookingRepository;
import com.doctris.care.ui.adapter.BookingHistoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class BookingHistoryActivity extends AppCompatActivity {
    private Button backHome;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private NestedScrollView nestedScrollView;
    private int page = 1;
    private static final int LIMIT = 10;
    private int totalPage = 0;
    private List<Booking> listBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);
        listBooking = new ArrayList<>();
        bindingViews();
        bindingActions();
        initLinearLayout();
    }

    private void getBookingHistory(List<Booking> listBooking) {
        progressBar.setVisibility(View.VISIBLE);
        LiveData<ListResponse<Booking>> bookingLiveData = BookingRepository.getInstance().getBookingHistory(page, LIMIT, "-date_time", null);
        bookingLiveData.observe(this, bookings -> {
            if (bookings != null) {
                listBooking.addAll(bookings.getItems());
                totalPage = bookings.getTotalPages();
                BookingHistoryAdapter adapter = new BookingHistoryAdapter(listBooking, this);
                recyclerView.setAdapter(adapter);
            }
            progressBar.setVisibility(View.GONE);
        });
    }

    private void bindingActions() {
        backHome.setOnClickListener(this::onClickBackHome);
    }

    private void initLinearLayout() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        getBookingHistory(listBooking);
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight() && page < totalPage) {
                page++;
                getBookingHistory(listBooking);
            }
        });
    }

    private void onClickBackHome(View view) {
        finish();
    }

    private void bindingViews() {
        backHome = findViewById(R.id.btn_back);
        recyclerView = findViewById(R.id.recyclerview_booking);
        progressBar = findViewById(R.id.progressBar);
        nestedScrollView = findViewById(R.id.idNestedSV);
    }
}
