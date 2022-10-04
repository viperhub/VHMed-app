package com.doctris.care.utils;

import android.content.Context;

import es.dmoral.toasty.Toasty;

public class ToastUtil {
    public static void success(Context c, String message) {
        Toasty.success(c, message, Toasty.LENGTH_SHORT, true).show();
    }

    public static void error(Context c, String message) {
        Toasty.error(c, message, Toasty.LENGTH_SHORT, true).show();
    }

    public static void info(Context c, String message) {
        Toasty.info(c, message, Toasty.LENGTH_SHORT, true).show();
    }

    public static void warning(Context c, String message) {
        Toasty.warning(c, message, Toasty.LENGTH_SHORT, true).show();
    }

    public static void normal(Context c, String message) {
        Toasty.normal(c, message, Toasty.LENGTH_SHORT).show();
    }
}
