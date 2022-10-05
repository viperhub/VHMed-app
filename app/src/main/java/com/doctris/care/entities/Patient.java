package com.doctris.care.entities;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class Patient {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("date_of_birth")
    private String dateOfBirth;
    @SerializedName("phone")
    private int Phone;
    @SerializedName("gender")
    private boolean gender;
    @SerializedName("address")
    private String address;
    @SerializedName("avatar")
    private String avatar;

    public Patient() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getPhone() {
        return Phone;
    }

    public void setPhone(int phone) {
        Phone = phone;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", Phone=" + Phone +
                ", gender=" + gender +
                ", address='" + address + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
