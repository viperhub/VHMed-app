package com.doctris.care.domain;

import com.doctris.care.entities.Patient;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PatientResponse {
    @SerializedName("page")
    private int page;
    @SerializedName("perPage")
    private int perPage;
    @SerializedName("totalItems")
    private int totalItems;
    @SerializedName("totalPages")
    private int totalPages;
    @SerializedName("items")
    List<Patient> patients;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
}
