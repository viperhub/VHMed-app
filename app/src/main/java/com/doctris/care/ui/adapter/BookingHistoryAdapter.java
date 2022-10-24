package com.doctris.care.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doctris.care.R;
import com.doctris.care.entities.Booking;
import com.doctris.care.ui.adapter.holder.BookingViewHolder;

import java.util.List;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingViewHolder> {
    private List<Booking> bookings;
    private final Context context;
    private final LayoutInflater layoutInflater;

    public BookingHistoryAdapter(List<Booking> bookings, Context context) {
        this.bookings = bookings;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookingViewHolder(layoutInflater.inflate(R.layout.booking_view_item, parent, false), context);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        holder.bindData(bookings.get(position));
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }
}
