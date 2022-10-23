package com.doctris.care.entities;

import com.doctris.care.domain.ExpandResponse;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class Booking {
    @SerializedName("id")
    private String id;
    @SerializedName("@expand")
    private ExpandResponse expand;
    @SerializedName("date_time")
    private String dateTime;
    @SerializedName("description")
    private String description;
    @SerializedName("payment_method")
    private String paymentMethod;
    @SerializedName("payment_status")
    private String paymentStatus;
    @SerializedName("booking_status")
    private String bookingStatus;
    @SerializedName("price")
    private String price;
    @SerializedName("created")
    private String created;

    public Booking() {
    }

    public Booking(String id, ExpandResponse expand, String dateTime, String description, String paymentMethod, String paymentStatus, String bookingStatus, String price, String created) {
        this.id = id;
        this.expand = expand;
        this.dateTime = dateTime;
        this.description = description;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.bookingStatus = bookingStatus;
        this.price = price;
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ExpandResponse getExpand() {
        return expand;
    }

    public void setExpand(ExpandResponse expand) {
        this.expand = expand;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
