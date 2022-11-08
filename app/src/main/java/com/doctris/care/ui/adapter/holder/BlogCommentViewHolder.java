package com.doctris.care.ui.adapter.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doctris.care.R;
import com.doctris.care.entities.Comment;
import com.doctris.care.utils.GlideUtil;

import de.hdodenhof.circleimageview.CircleImageView;

public class BlogCommentViewHolder extends RecyclerView.ViewHolder {

    private CircleImageView imgUser;
    private TextView tvUserName;
    private TextView tvDate;
    private TextView tvComment;
    private final Context context;

    public BlogCommentViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        bindingViews(itemView);
    }

    private void bindingViews(View itemView) {
        imgUser = itemView.findViewById(R.id.user_image);
        tvUserName = itemView.findViewById(R.id.user_name);
        tvDate = itemView.findViewById(R.id.date_comment);
        tvComment = itemView.findViewById(R.id.comment);
    }

    public void setCommentItem(Comment commentItem) {
        tvUserName.setText(commentItem.getExpand().getPatient().getName());
        GlideUtil.load(imgUser, commentItem.getExpand().getPatient().getAvatar());
        tvDate.setText(commentItem.getCreated());
        tvComment.setText(commentItem.getComment());
    }
}
