package com.doctris.care.entities;

import com.doctris.care.domain.ExpandServiceResponse;
import com.google.gson.annotations.SerializedName;

public class Service {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("price")
    private int price;
    @SerializedName("@expand")
    private ExpandServiceResponse expand;
    @SerializedName("image")
    private String image;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ExpandServiceResponse getExpand() {
        return expand;
    }

    public void setExpand(ExpandServiceResponse expand) {
        this.expand = expand;
    }

    public String getImage() {
        return "http://doctris-cloud.koreacentral.cloudapp.azure.com/api/files/service/" + id + "/" + image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category=" + expand.getCategory().toString() +
                ", image='" + getImage() + '\'' +
                '}';
    }
}
