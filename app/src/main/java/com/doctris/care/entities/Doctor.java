package com.doctris.care.entities;

import com.google.gson.annotations.SerializedName;

public class Doctor {
    @SerializedName("id")
    private int id;
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
    @SerializedName("category")
    private Category category;
    @SerializedName("image")
    private String image;
    @SerializedName("price")
    private int price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImage() {
        return "http://doctris-cloud.koreacentral.cloudapp.azure.com/api/files/doctor/" + id + "/" + image;
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
                ", category=" + category +
                ", image='" + image + '\'' +
                ", price=" + price +
                '}';
    }
}
