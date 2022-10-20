package com.doctris.care.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class GlideUtil {
    private GlideUtil() {
    }

    public static void load(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }

    public static void load(ImageView imageView, int resId) {
        Glide.with(imageView.getContext())
                .load(resId)
                .into(imageView);
    }

    public static void load(ImageView imageView, String url, int placeholder) {
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(placeholder)
                .into(imageView);
    }

    public static void load(ImageView imageView, int resId, int placeholder) {
        Glide.with(imageView.getContext())
                .load(resId)
                .placeholder(placeholder)
                .into(imageView);
    }

    public static void load(CircleImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }


}
