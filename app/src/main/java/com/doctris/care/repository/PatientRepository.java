package com.doctris.care.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.doctris.care.client.RetrofitClient;
import com.doctris.care.domain.ListResponse;
import com.doctris.care.entities.Account;
import com.doctris.care.entities.Patient;
import com.doctris.care.storage.SharedPrefManager;
import com.doctris.care.utils.LoggerUtil;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientRepository {
    private static PatientRepository instance;
    private MutableLiveData<String> status;
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";
    private static final String MULTI_PART_FORM_DATA = "multipart/form-data";

    public static PatientRepository getInstance() {
        if (instance == null) {
            instance = new PatientRepository();
        }
        return instance;
    }

    public LiveData<String> getPatientInfo() {
        status = new MutableLiveData<>();
        Call<ListResponse<Patient>> call = RetrofitClient.getInstance().getApi().getPatientInfo("User " + SharedPrefManager.getInstance().get("token", String.class));
        call.enqueue(new Callback<ListResponse<Patient>>() {
            @Override
            public void onResponse(@NonNull Call<ListResponse<Patient>> call, @NonNull Response<ListResponse<Patient>> response) {
                if (response.isSuccessful()) {
                    ListResponse<Patient> patientResponse = response.body();
                    assert patientResponse != null;
                    List<Patient> patients = patientResponse.getItems();
                    if (patients.size() > 0) {
                        SharedPrefManager.getInstance().put("patient", patients.get(0));
                        status.setValue(SUCCESS);
                    } else {
                        status.setValue("not found");
                    }
                } else {
                    status.setValue(ERROR);
                    LoggerUtil.e("getPatientInfo", response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ListResponse<Patient>> call, @NonNull Throwable t) {
                status.setValue(ERROR);
            }
        });
        return status;
    }

    public LiveData<String> savePatientInfo(Patient patient, File avatarFile) {
        status = new MutableLiveData<>();
        RequestBody name = RequestBody.create(MediaType.parse(MULTI_PART_FORM_DATA), patient.getName());
        RequestBody account = RequestBody.create(MediaType.parse(MULTI_PART_FORM_DATA), SharedPrefManager.getInstance().get("account", Account.class).getUserId());
        RequestBody dateOfBirth = RequestBody.create(MediaType.parse(MULTI_PART_FORM_DATA), patient.getDateOfBirth());
        RequestBody phone = RequestBody.create(MediaType.parse(MULTI_PART_FORM_DATA), String.valueOf(patient.getPhone()));
        RequestBody address = RequestBody.create(MediaType.parse(MULTI_PART_FORM_DATA), patient.getAddress());
        RequestBody gender = RequestBody.create(MediaType.parse(MULTI_PART_FORM_DATA), String.valueOf(patient.isGender()));
        RequestBody avatar = RequestBody.create(MediaType.parse(MULTI_PART_FORM_DATA), avatarFile);
        MultipartBody.Part avatarPart = MultipartBody.Part.createFormData("avatar", avatarFile.getName(), avatar);
        Call<Patient> call = RetrofitClient.getInstance().getApi().savePatientInfo("User " + SharedPrefManager.getInstance().get("token", String.class), name, account, dateOfBirth, gender, phone, address, avatarPart);
        call.enqueue(new retrofit2.Callback<Patient>() {
            @Override
            public void onResponse(@NonNull Call<Patient> call, @NonNull Response<Patient> response) {
                if (response.isSuccessful()) {
                    SharedPrefManager.getInstance().put("patient", response.body());
                    status.setValue(SUCCESS);
                } else {
                    if(response.code() == 400) {
                        status.setValue("duplicate");
                    } else {
                        status.setValue(ERROR);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Patient> call, @NonNull Throwable t) {
            }
        });
        return status;
    }
}
