package com.doctris.care.entities;

import com.doctris.care.domain.ExpandResponse;
import com.google.gson.annotations.SerializedName;

public class Rate {
    @SerializedName("id")
    private String id;
    @SerializedName("vote")
    private int vote;
    @SerializedName("@expand")
    private ExpandResponse expand;
    @SerializedName("created")
    private String created;
    @SerializedName("description")
    private String description;

    public Rate() {
    }

    public Rate(String id, int vote, ExpandResponse expand, String created, String description) {
        this.id = id;
        this.vote = vote;
        this.expand = expand;
        this.created = created;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public ExpandResponse getExpand() {
        return expand;
    }

    public void setExpand(ExpandResponse expand) {
        this.expand = expand;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
