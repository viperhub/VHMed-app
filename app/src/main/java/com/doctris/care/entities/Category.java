package com.doctris.care.entities;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("id")
    private String id;
    @SerializedName("category_name")
    private String categoryName;
    @SerializedName("description")
    private String description;
    @SerializedName("image")
    private String image;

    public Category(String id, String categoryName, String description, String image) {
        this.id = id;
        this.categoryName = categoryName;
        this.description = description;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        if (id == null) {
            return image;
        } else {
            return "https://doctriscare.ml/api/files/category/" + id + "/" + image;
        }
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
