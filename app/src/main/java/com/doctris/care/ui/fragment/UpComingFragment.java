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
import com.doctris.care.entities.Booking;
import com.doctris.care.repository.BookingRepository;
import com.doctris.care.ui.adapter.BookingHistoryAdapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UpComingFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private NestedScrollView nestedScrollView;
    private int page = 1;
    private static final int LIMIT = 10;
    private int totalPage = 0;
    private List<Booking> listBooking;
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_up_coming, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = getActivity();
        if (isAdded() && activity != null) {
            listBooking = new ArrayList<>();
            bindingViews(view);
            initLinearLayout();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = context instanceof Activity ? (Activity) context : null;
    }

    private void getBookingHistory(List<Booking> listBooking) {
        LocalDate date = LocalDate.now();
        progressBar.setVisibility(View.VISIBLE);
        LiveData<ListResponse<Booking>> bookingLiveData = BookingRepository.getInstance().getBookingHistory(page, LIMIT, "+date_time", "date_time>='" + date + "' && booking_status='pending'");
        bookingLiveData.observe(getViewLifecycleOwner(), bookings -> {
            if (bookings != null) {
                listBooking.addAll(bookings.getItems());
                totalPage = bookings.getTotalPages();
                BookingHistoryAdapter adapter = new BookingHistoryAdapter(listBooking, getContext());
                recyclerView.setAdapter(adapter);
            }
            progressBar.setVisibility(View.GONE);
        });
    }

    private void initLinearLayout() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        getBookingHistory(listBooking);
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight() && page < totalPage) {
                page++;
                getBookingHistory(listBooking);
            }
        });
    }

    private void bindingViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerview_booking);
        progressBar = view.findViewById(R.id.progressBar);
        nestedScrollView = view.findViewById(R.id.idNestedSV);
    }
}