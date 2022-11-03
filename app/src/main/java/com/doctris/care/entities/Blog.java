package com.doctris.care.entities;

import com.doctris.care.domain.ExpandResponse;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Blog {

    @SerializedName("id")
    private String id;
    @SerializedName("tittle")
    private String tittle;
    @SerializedName("date_time")
    private String dateTime;
    @SerializedName("image")
    private String image;
    @SerializedName("description")
    private String description;
    @SerializedName("@expand")
    private ExpandResponse expand;
    @SerializedName("viewer")
    private int viewer;
    @SerializedName("created")
    private String dateCreated;
    @SerializedName("updated")
    private String dateUpdated;

    public int getViewer() {
        return viewer;
    }

    public void setViewer(int viewer) {
        this.viewer = viewer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getImage() {
        return "https://doctriscare.ml/api/files/blog/" + id + "/" + image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id='" + id + '\'' +
                ", tittle='" + tittle + '\'' +
                ", dateTime=" + dateTime +
                ", image='" + getImage() + '\'' +
                ", description='" + description + '\'' +
                ", category=" + expand.getCategory().toString() +
                ", dateCreated=" + dateCreated +
                ", dateUpdated=" + dateUpdated +
                ", viewer=" + viewer +
                '}';
    }
}
