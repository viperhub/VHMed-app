package com.doctris.care.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.doctris.care.R;
import com.doctris.care.domain.ListResponse;
import com.doctris.care.entities.Comment;
import com.doctris.care.repository.CommentRepository;
import com.doctris.care.ui.adapter.CommentAdapter;
import com.doctris.care.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class BlogCommentActivity extends AppCompatActivity {

    private String filter = null;
    private List<Comment> listComment;
    private RecyclerView recyclerViewComment;
    private TextView tvTitle;
    private Button btnBack;
    private Button btnOutComment;
    private Button btnSendComment;
    private EditText etComment;

    public void bindingViews(){
        recyclerViewComment = findViewById(R.id.rv_comment);
        tvTitle = findViewById(R.id.tv_title);

        btnBack = findViewById(R.id.btn_back);
        btnOutComment = findViewById(R.id.btn_out_comment);
        btnSendComment = findViewById(R.id.btn_send_comment);
        etComment = findViewById(R.id.et_comment);
    }

    public void bindingActions(){
        btnBack.setOnClickListener(this::onClickBtnBack);
        btnOutComment.setOnClickListener(this::onClickBtnBack);
        btnSendComment.setOnClickListener(this::onClickSendCommment);
    }

    private void onClickSendCommment(View view) {
        Intent intent = getIntent();
        String idBlog = intent.getStringExtra("id");

        if (onValidate()) {
            CommentRepository.getInstance().saveComment(etComment.getText().toString(), idBlog);
            Intent intentComment = new Intent(this, BlogCommentActivity.class);
            intentComment.putExtra("id", idBlog);
            intentComment.putExtra("name", tvTitle.getText());

            finish();
            startActivity(intentComment);

        }
    }

    private boolean onValidate() {
        if (etComment.getText().toString().isEmpty()) {
            ToastUtil.warning(this, "Bạn chưa nhập bình luận");
            return false;
        }
        return true;
    }

    private void setTittle(){
        Intent intent = getIntent();
        String nameBlog = intent.getStringExtra("name");
        tvTitle.setText(nameBlog);
    }

    private void getCommentData(List<Comment> commentList) {

        Intent intent = getIntent();
        String idBlog = intent.getStringExtra("id");

        filter = "(blog~'"+ idBlog +"')";

        LiveData<ListResponse<Comment>> commentLiveData = CommentRepository.getInstance().getComments(1, 10, "-created", filter);
        commentLiveData.observe(this, blogs -> {
            if (blogs != null) {
                commentList.addAll(blogs.getItems());
                CommentAdapter commentAdapter = new CommentAdapter(commentList, this);
                recyclerViewComment.setAdapter(commentAdapter);
            }
        });
    }

    private void initLinearLayout() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewComment.setLayoutManager(linearLayoutManager);
        setTittle();
        getCommentData(listComment);
    }

    private void onClickBtnBack(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_comment);
        listComment = new ArrayList<>();
        bindingViews();
        bindingActions();
        initLinearLayout();
    }


}