package com.doctris.care.domain;

import com.doctris.care.entities.Category;
import com.doctris.care.entities.Doctor;
import com.doctris.care.entities.Patient;
import com.doctris.care.entities.Service;
import com.google.gson.annotations.SerializedName;

public class ExpandResponse {
    @SerializedName("category")
    private Category category;
    @SerializedName("doctor")
    private Doctor doctor;
    @SerializedName("patient")
    private Patient patient;
    @SerializedName("service")
    private Service service;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
