package com.doctris.care.repository;

import android.util.ArrayMap;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.doctris.care.client.RetrofitClient;
import com.doctris.care.domain.ListResponse;
import com.doctris.care.entities.Comment;
import com.doctris.care.entities.Patient;
import com.doctris.care.entities.Rate;
import com.doctris.care.storage.SharedPrefManager;
import com.doctris.care.utils.LoggerUtil;
import com.doctris.care.utils.RequestBodyUtil;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentRepository {
    private static CommentRepository instance;
    private MutableLiveData<String> status;
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";

    public static CommentRepository getInstance() {
        if (instance == null) {
            instance = new CommentRepository();
        }
        return instance;
    }

    public LiveData<ListResponse<Comment>> getComments(int page, int perPage, String sort, String filter) {
        MutableLiveData<ListResponse<Comment>> comments = new MutableLiveData<>();
        status = new MutableLiveData<>();
        Call<ListResponse<Comment>> call = RetrofitClient.getInstance().getApi().getCommentBlog("User " + SharedPrefManager.getInstance().get("token", String.class), page, perPage, sort, filter, "patient,blog");
        call.enqueue(new Callback<ListResponse<Comment>>() {
            @Override
            public void onResponse(@NonNull Call<ListResponse<Comment>> call, @NonNull Response<ListResponse<Comment>> response) {
                if (response.isSuccessful()) {
                    ListResponse<Comment> commentResponse = response.body();
                    assert commentResponse != null;
                    comments.setValue(commentResponse);
                    status.setValue(SUCCESS);
                } else {
                    status.setValue(ERROR);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ListResponse<Comment>> call, @NonNull Throwable t) {
                LoggerUtil.e(t.getMessage());
                status.setValue(ERROR);
            }
        });
        return comments;
    }

    public void saveComment(String comment, String idBlog) {
        status = new MutableLiveData<>();
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("comment", comment);
        jsonParams.put("patient", SharedPrefManager.getInstance().get("patient", Patient.class).getId());
        jsonParams.put("blog", idBlog);
        Call<Void> call = RetrofitClient.getInstance().getApi().saveComment("User " + SharedPrefManager.getInstance().get("token", String.class), RequestBodyUtil.createRequestBody(jsonParams));
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

}
