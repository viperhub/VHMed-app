package com.doctris.care.utils;

import android.content.Context;
import android.graphics.Color;

import com.developer.kalert.KAlertDialog;
import com.doctris.care.R;

public class AlertDialogUtil {
    private static KAlertDialog kAlertDialog;

    private AlertDialogUtil() {
    }

    public static void loading(Context context) {
        kAlertDialog = new KAlertDialog(context, KAlertDialog.PROGRESS_TYPE, 0)
                .setTitleTextSize(20);
        kAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#0BA2FF"));
        kAlertDialog.show();
    }

    public static void stop() {
        if (kAlertDialog != null) {
            kAlertDialog.dismissWithAnimation();
        }
    }

    public static void success(Context context, String title, String content, String btnContent, KAlertDialog.KAlertClickListener listener) {
        kAlertDialog = new KAlertDialog(context, KAlertDialog.SUCCESS_TYPE, 0)
                .setTitleText(title)
                .setContentText(content)
                .setConfirmText(btnContent)
                .setConfirmClickListener(listener)
                .setContentTextSize(15)
                .setTitleTextSize(20)
                .confirmButtonColor(R.drawable.button_background, context);
        kAlertDialog.show();
    }

    public static void error(Context context, String title, String content, String btnContent, KAlertDialog.KAlertClickListener listener) {
        kAlertDialog = new KAlertDialog(context, KAlertDialog.ERROR_TYPE, 0)
                .setTitleText(title)
                .setContentText(content)
                .setConfirmText(btnContent)
                .setConfirmClickListener(listener)
                .setContentTextSize(15)
                .setTitleTextSize(20)
                .confirmButtonColor(R.drawable.button_background, context);
        kAlertDialog.show();
    }

    public static void warning(Context context, String title, String content, String btnContent, KAlertDialog.KAlertClickListener listener) {
        kAlertDialog = new KAlertDialog(context, KAlertDialog.WARNING_TYPE, 0)
                .setTitleText(title)
                .setContentText(content)
                .setConfirmText(btnContent)
                .setConfirmClickListener(listener)
                .setContentTextSize(15)
                .setTitleTextSize(20)
                .confirmButtonColor(R.drawable.button_background, context);
        kAlertDialog.show();
    }
}
