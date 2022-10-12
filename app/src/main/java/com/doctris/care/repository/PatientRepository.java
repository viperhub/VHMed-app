package com.doctris.care.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.doctris.care.client.RetrofitClient;
import com.doctris.care.domain.PatientResponse;
import com.doctris.care.entities.Patient;
import com.doctris.care.storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;

public class PatientRepository {
    private static PatientRepository instance;
    private MutableLiveData<String> status;

    public static PatientRepository getInstance() {
        if (instance == null) {
            instance = new PatientRepository();
        }
        return instance;
    }

    public LiveData<String> getPatientInfo() {
        status = new MutableLiveData<>();
        Call<PatientResponse> call = RetrofitClient.getInstance().getApi().getPatientInfo("User " + SharedPrefManager.getInstance().get("token", String.class));
        call.enqueue(new retrofit2.Callback<PatientResponse>() {
            @Override
            public void onResponse(@NonNull Call<PatientResponse> call, @NonNull retrofit2.Response<PatientResponse> response) {
                if (response.isSuccessful()) {
                    PatientResponse patientResponse = response.body();
                    assert patientResponse != null;
                    List<Patient> patients = patientResponse.getPatients();
                    if (patients.size() > 0) {
                        SharedPrefManager.getInstance().put("patient", patients.get(0));
                        status.setValue("success");
                    } else {
                        status.setValue("not found");
                    }
                } else {
                    status.setValue("error");
                }
            }

            @Override
            public void onFailure(@NonNull Call<PatientResponse> call, @NonNull Throwable t) {
                status.setValue("error");
            }
        });
        return status;
    }
}
