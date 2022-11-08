package com.doctris.care.entities;

import com.doctris.care.domain.ExpandResponse;
import com.google.gson.annotations.SerializedName;

public class Comment {
    @SerializedName("id")
    private String id;
    @SerializedName("@expand")
    private ExpandResponse expand;
    @SerializedName("comment")
    private String comment;
    @SerializedName("created")
    private String created;

    public Comment() {
    }

    public Comment(String id, ExpandResponse expand, String comment, String created) {
        this.id = id;
        this.expand = expand;
        this.comment = comment;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
