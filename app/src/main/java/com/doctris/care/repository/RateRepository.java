package com.doctris.care.repository;

import android.util.ArrayMap;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.doctris.care.client.RetrofitClient;
import com.doctris.care.domain.ListResponse;
import com.doctris.care.entities.Patient;
import com.doctris.care.entities.Rate;
import com.doctris.care.storage.SharedPrefManager;
import com.doctris.care.utils.LoggerUtil;
import com.doctris.care.utils.RequestBodyUtil;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RateRepository {
    private static RateRepository instance;
    private MutableLiveData<String> status;
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";

    public static RateRepository getInstance() {
        if (instance == null) {
            instance = new RateRepository();
        }
        return instance;
    }

    public void saveRate(Rate rate) {
        status = new MutableLiveData<>();
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("vote", rate.getVote());
        jsonParams.put("description", rate.getDescription());
        jsonParams.put("patient", SharedPrefManager.getInstance().get("patient", Patient.class).getId());
        if(rate.getExpand().getDoctor() != null) {
            jsonParams.put("doctor", rate.getExpand().getDoctor().getId());
        }
        if(rate.getExpand().getService() != null) {
            jsonParams.put("service", rate.getExpand().getService().getId());
        }
        Call<Void> call = RetrofitClient.getInstance().getApi().saveRate("User " + SharedPrefManager.getInstance().get("token", String.class), RequestBodyUtil.createRequestBody(jsonParams));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    status.setValue(SUCCESS);
                } else {
                    status.setValue(ERROR);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                LoggerUtil.e(t.getMessage());
                status.setValue(ERROR);
            }
        });
    }

    public LiveData<ListResponse<Rate>> getRates(int page, int perPage, String sort, String filter) {
        MutableLiveData<ListResponse<Rate>> rates = new MutableLiveData<>();
        status = new MutableLiveData<>();
        Call<ListResponse<Rate>> call = RetrofitClient.getInstance().getApi().getRate("User " + SharedPrefManager.getInstance().get("token", String.class), page, perPage, sort, filter, "patient,doctor,service");
        call.enqueue(new Callback<ListResponse<Rate>>() {
            @Override
            public void onResponse(@NonNull Call<ListResponse<Rate>> call, @NonNull Response<ListResponse<Rate>> response) {
                if (response.isSuccessful()) {
                    ListResponse<Rate> rateResponse = response.body();
                    assert rateResponse != null;
                    rates.setValue(rateResponse);
                    status.setValue(SUCCESS);
                } else {
                    status.setValue(ERROR);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ListResponse<Rate>> call, @NonNull Throwable t) {
                LoggerUtil.e(t.getMessage());
                status.setValue(ERROR);
            }
        });
        return rates;
    }
}
