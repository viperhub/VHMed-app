package com.doctris.care.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.developer.kalert.KAlertDialog;
import com.doctris.care.R;
import com.doctris.care.entities.Account;
import com.doctris.care.entities.Patient;
import com.doctris.care.repository.AccountRepository;
import com.doctris.care.storage.SharedPrefManager;
import com.doctris.care.ui.activity.BookingHistoryActivity;
import com.doctris.care.ui.activity.LoginActivity;
import com.doctris.care.ui.activity.PatientInfoActivity;
import com.doctris.care.utils.AlertDialogUtil;
import com.doctris.care.utils.GlideUtil;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {
    private TextView tvName;
    private TextView tvEmail;
    private CircleImageView ivProfile;
    private TextView tvLogout;
    private TextView tvBookingHistory;
    private TextView tvChangePassword;
    private TextView tvChangeProfile;
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = getActivity();
        if (isAdded() && activity != null) {
            bindingView(view);
            bindingData();
            bindingActions();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = context instanceof Activity ? (Activity) context : null;
    }

    private void bindingView(View view) {
        tvName = view.findViewById(R.id.tv_name);
        tvEmail = view.findViewById(R.id.tv_email);
        ivProfile = view.findViewById(R.id.iv_avatar);
        tvLogout = view.findViewById(R.id.logout);
        tvBookingHistory = view.findViewById(R.id.tv_history);
        tvChangePassword = view.findViewById(R.id.tv_change_password);
        tvChangeProfile = view.findViewById(R.id.tv_my_account);
    }

    private void bindingActions() {
        tvLogout.setOnClickListener(this::onClickLogout);
        tvBookingHistory.setOnClickListener(this::onClickBookingHistory);
        tvChangePassword.setOnClickListener(this::onClickChangePassword);
        tvChangeProfile.setOnClickListener(this::onClickChangeProfile);
    }

    private void onClickChangeProfile(View view) {
        Intent intent = new Intent(getContext(), PatientInfoActivity.class);
        startActivity(intent);
    }

    private void onClickChangePassword(View view) {
        new KAlertDialog(activity, KAlertDialog.WARNING_TYPE)
                .setTitleText("Đổi mật khẩu")
                .setContentText("Bạn có chắc chắn muốn đổi mật khẩu?")
                .setConfirmClickListener("Đồng ý", sDialog -> {
                    sDialog.dismissWithAnimation();
                    AccountRepository.getInstance().requestPasswordReset(SharedPrefManager.getInstance().get("account", Account.class).getEmail());
                    SharedPrefManager.getInstance().clear();
                    Intent intent = new Intent(requireContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    AlertDialogUtil.success(getContext(), "Thành công", "Gửi mã xác minh thành công.", "OK", KAlertDialog::dismissWithAnimation);
                })
                .setCancelClickListener("Hủy bỏ", KAlertDialog::dismissWithAnimation)
                .show();
    }

    private void onClickBookingHistory(View view) {
        startActivity(new Intent(getContext(), BookingHistoryActivity.class));
    }

    private void onClickLogout(View view) {
        new KAlertDialog(activity, KAlertDialog.WARNING_TYPE)
                .setTitleText("Đăng xuất")
                .setContentText("Bạn có chắc chắn muốn đăng xuất?")
                .setConfirmClickListener("Đăng xuất", sDialog -> {
                    sDialog.dismissWithAnimation();
                    SharedPrefManager.getInstance().clear();
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .setCancelClickListener("Hủy", KAlertDialog::dismissWithAnimation)
                .show();
    }


    private void bindingData() {
        tvName.setText(SharedPrefManager.getInstance().get("patient", Patient.class).getName());
        tvEmail.setText(SharedPrefManager.getInstance().get("account", Account.class).getEmail());
        GlideUtil.load(ivProfile, SharedPrefManager.getInstance().get("patient", Patient.class).getAvatar());
    }
}