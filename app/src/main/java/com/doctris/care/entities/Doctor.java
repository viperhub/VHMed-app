package com.doctris.care.entities;

import com.doctris.care.domain.ExpandResponse;
import com.google.gson.annotations.SerializedName;

public class Doctor {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("date_of_birth")
    private String dateOfBirth;
    @SerializedName("phone")
    private int phone;
    @SerializedName("gender")
    private boolean gender;
    @SerializedName("address")
    private String address;
    @SerializedName("description")
    private String description;
    @SerializedName("@expand")
    private ExpandResponse expand;
    @SerializedName("image")
    private String image;
    @SerializedName("price")
    private int price;
    private float rating;

    public double getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
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
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ExpandResponse getExpand() {
        return expand;
    }

    public void setExpand(ExpandResponse expand) {
        this.expand = expand;
    }

    public String getImage() {
        return "https://doctriscare.ml/api/files/doctor/" + id + "/" + image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", phone=" + phone +
                ", gender=" + gender +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", category=" + expand.getCategory().toString() +
                ", image='" + image + '\'' +
                ", price=" + price +
                '}';
    }
}
