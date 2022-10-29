package com.doctris.care.ui.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.developer.kalert.KAlertDialog;
import com.doctris.care.R;
import com.doctris.care.domain.ExpandResponse;
import com.doctris.care.entities.Booking;
import com.doctris.care.entities.Doctor;
import com.doctris.care.entities.Service;
import com.doctris.care.repository.AccountRepository;
import com.doctris.care.repository.BookingRepository;
import com.doctris.care.repository.DoctorRepository;
import com.doctris.care.repository.ServiceRepository;
import com.doctris.care.utils.AlertDialogUtil;
import com.doctris.care.utils.GlideUtil;
import com.doctris.care.utils.ToastUtil;

import de.hdodenhof.circleimageview.CircleImageView;

public class BookingActivity extends AppCompatActivity {
    private TextView etDay;
    private TextView etTime;
    private EditText etDescription;
    private Spinner spnPayment;
    private Button btnBooking;
    private Button btnBack;
    private CircleImageView serviceImage;
    private TextView etServiceName;
    private TextView etServicePrice;
    private Service service;
    private Doctor doctor;
    private String type;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        bindingView();
        getData();
        bindingAction();
    }

    private void bindingView() {
        etDay = findViewById(R.id.et_day);
        etTime = findViewById(R.id.et_time);
        etDescription = findViewById(R.id.et_description);
        spnPayment = findViewById(R.id.spn_payment);
        btnBooking = findViewById(R.id.btn_book);
        btnBack = findViewById(R.id.btn_back);
        serviceImage = findViewById(R.id.imgService);
        etServiceName = findViewById(R.id.tvServiceName);
        etServicePrice = findViewById(R.id.tvServicePrice);
    }

    private void bindingAction() {
        etDay.setOnClickListener(this::chooseDay);
        etTime.setOnClickListener(this::chooseTime);
        btnBooking.setOnClickListener(this::onBooking);
        btnBack.setOnClickListener(this::back);
    }

    private void onBooking(View view) {
        if (validate()) {
            new KAlertDialog(this, KAlertDialog.WARNING_TYPE)
                    .setTitleText("Xác nhận đặt lịch")
                    .setContentText("Bạn có chắc chắn muốn đặt lịch?")
                    .setContentTextSize(15)
                    .setTitleTextSize(20)
                    .setConfirmClickListener("Đặt lịch", kAlertDialog1 -> {
                        kAlertDialog1.dismissWithAnimation();
                        booking();
                    })
                    .setCancelClickListener("Hủy", KAlertDialog::dismissWithAnimation)
                    .show();
        }
    }

    private void back(View view) {
        finish();
    }

    private void chooseTime(View view) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, this::onTimeSet, 0, 0, true);
        timePickerDialog.show();
    }

    @SuppressLint("SetTextI18n")
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        if (hourOfDay >= 7 && hourOfDay <= 17) {
            etTime.setText(hourString + ":" + minuteString);
        } else {
            ToastUtil.warning(this, "Chỉ đặt lịch từ 7h đến 17h");
        }
    }

    private void chooseDay(View view) {
        @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view1, year, month, dayOfMonth) -> etDay.setText(dayOfMonth + "/" + (month + 1) + "/" + year), 2020, 0, 1);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + 604800000);
        datePickerDialog.show();
    }

    private String convertDate(String date, String time) {
        String[] arr = date.split("/");
        int day = Integer.parseInt(arr[0]);
        int month = Integer.parseInt(arr[1]);
        if (day < 10) {
            arr[0] = "0" + day;
        }
        if (month < 10) {
            arr[1] = "0" + month;
        }

        return arr[2] + "-" + arr[1] + "-" + arr[0] + " " + time + ":00";
    }

    @SuppressLint("SetTextI18n")
    private void getData() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        id = intent.getStringExtra("id");
        if (type.equals("doctor")) {
            LiveData<Doctor> liveData = DoctorRepository.getInstance().getDoctorById(id);
            liveData.observe(this, doctor -> {
                this.doctor = doctor;
                GlideUtil.load(serviceImage, doctor.getImage());
                etServiceName.setText("Bác sĩ: " + doctor.getName());
                etServicePrice.setText("Giá: " + doctor.getPrice() + " VND");
            });
        } else {
            LiveData<Service> liveData = ServiceRepository.getInstance().getServiceById(id);
            liveData.observe(this, service -> {
                this.service = service;
                GlideUtil.load(serviceImage, service.getImage());
                etServiceName.setText(service.getName());
                etServicePrice.setText(service.getPrice() + " VND");
            });
        }
    }

    private void booking() {
        AlertDialogUtil.loading(this);
        String date = convertDate(etDay.getText().toString(), etTime.getText().toString());
        String description = etDescription.getText().toString();
        String payment = spnPayment.getSelectedItem().toString();
        payment = payment.equals("Thanh toán qua ví điện tử") ? "vnpay" : "pay as you go";
        if (payment.equals("vnpay")) {
            ToastUtil.warning(this, "Chức năng đang được phát triển");
            return;
        }
        Booking booking = new Booking();
        ExpandResponse expandResponse = new ExpandResponse();
        expandResponse.setDoctor(doctor);
        expandResponse.setService(service);
        booking.setExpand(expandResponse);
        booking.setPaymentMethod(payment);
        booking.setDateTime(date);
        booking.setDescription(description);
        booking.setBookingStatus("pending");
        booking.setPaymentStatus("pending");
        LiveData<Booking> liveData = BookingRepository.getInstance().saveBooking(booking);
        liveData.observe(this, booking1 -> {
            AlertDialogUtil.stop();
            if (booking1 != null) {
                Intent intent = new Intent(this, SuccessActivity.class);
                intent.putExtra("id", booking1.getId());
                startActivity(intent);
                finish();
            } else {
                ToastUtil.error(this, "Đặt lịch thất bại");
            }
        });
    }

    private boolean validate() {
        String day = etDay.getText().toString();
        String time = etTime.getText().toString();
        String description = etDescription.getText().toString();
        String payment = spnPayment.getSelectedItem().toString();

        if (day.isEmpty() || time.isEmpty() || description.isEmpty() || payment.isEmpty() || payment.equals("Chọn phương thức thanh toán")) {
            ToastUtil.warning(this, "Vui lòng nhập đầy đủ thông tin");
            return false;
        }
        return true;
    }


}
