package com.doctris.care.domain;

import com.doctris.care.entities.Category;
import com.google.gson.annotations.SerializedName;

public class ExpandServiceResponse {
    @SerializedName("category")
    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
