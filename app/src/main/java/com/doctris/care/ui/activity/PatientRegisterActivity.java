package com.doctris.care.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.developer.kalert.KAlertDialog;
import com.doctris.care.R;
import com.doctris.care.entities.Patient;
import com.doctris.care.repository.PatientRepository;
import com.doctris.care.utils.AlertDialogUtil;
import com.doctris.care.utils.RealPathUtil;
import com.doctris.care.utils.ValidateUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class PatientRegisterActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private EditText etName;
    private EditText etDateOfBirth;
    private RadioGroup rgGender;
    private RadioButton rbGender;
    private EditText etAddress;
    private EditText etPhone;
    private ImageView ivAvatar;
    private Button btnChooseImage;
    private Button btnSave;
    private ArrayList<Uri> path = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register);
        bindingView();
        bindingAction();
    }

    private void bindingView() {
        etName = findViewById(R.id.et_name);
        etDateOfBirth = findViewById(R.id.et_date_of_birth);
        rgGender = findViewById(R.id.rgGender);
        etAddress = findViewById(R.id.et_address);
        etPhone = findViewById(R.id.et_phone);
        ivAvatar = findViewById(R.id.iv_avatar);
        btnChooseImage = findViewById(R.id.btn_change_avatar);
        btnSave = findViewById(R.id.btn_save);
    }

    private void bindingAction() {
        btnChooseImage.setOnClickListener(this::onClickChooseImage);
        btnSave.setOnClickListener(this::onClickSave);
        etDateOfBirth.setOnClickListener(this::onClickDateOfBirth);
    }

    @SuppressLint("SetTextI18n")
    private void onClickDateOfBirth(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view1, year, month, dayOfMonth) -> etDateOfBirth.setText(dayOfMonth + "/" + (month + 1) + "/" + year), 2000, 0, 1);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void onClickSave(View view) {
        if (bindingValidate()) {
            AlertDialogUtil.loading(this);
            String realPath = RealPathUtil.getRealPath(this, resizeImage(path.get(0)));
            File avatar = new File(realPath);
            Patient patient = new Patient();
            patient.setName(etName.getText().toString());
            patient.setDateOfBirth(convertDate(etDateOfBirth.getText().toString()));
            patient.setAddress(etAddress.getText().toString());
            patient.setPhone(Integer.parseInt(etPhone.getText().toString()));
            patient.setGender(getGender());
            PatientRepository.getInstance().savePatientInfo(patient, avatar).observe(this, status -> {
                AlertDialogUtil.stop();
                if (status.equals("success")) {
                    KAlertDialog.KAlertClickListener listener = sDialog -> {
                        sDialog.dismissWithAnimation();
                        Intent intent = new Intent(this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    };
                   AlertDialogUtil.success(this, "Đăng ký thành công", "Đăng ký thành công", "OK", listener);
                } else if(status.equals("duplicate")) {
                    AlertDialogUtil.error(this, "Đăng ký thất bại", "Số điện thoại đã tồn lại", "OK", null);
                } else {
                    AlertDialogUtil.error(this, "Đăng ký thất bại", "Đăng ký thất bại", "OK", null);
                }
            });
        }
    }

    private void onClickChooseImage(View view) {
        String[] permissions = {android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, permissions)) {
            imagePicker();
        } else {
            EasyPermissions.requestPermissions(this, "Hãy cấp quyền camera và bộ nhớ", 100, permissions);
        }
    }

    private void imagePicker() {
        FilePickerBuilder.getInstance()
                .setActivityTitle("Lựa chọn ảnh")
                .setSpan(FilePickerConst.SPAN_TYPE.FOLDER_SPAN, 3)
                .setSpan(FilePickerConst.SPAN_TYPE.DETAIL_SPAN, 4)
                .setMaxCount(1)
                .setSelectedFiles(path)
                .setActivityTheme(R.style.ImagePicker)
                .pickPhoto(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FilePickerConst.REQUEST_CODE_PHOTO && resultCode == RESULT_OK && data != null) {
            path = new ArrayList<>();
            path.addAll(data.getParcelableArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
            Glide.with(this).load(path.get(0)).into(ivAvatar);
        }
    }

    private boolean getGender() {
        int selectedId = rgGender.getCheckedRadioButtonId();
        rbGender = findViewById(selectedId);
        return rbGender.getText().toString().equals("Nam");
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == 100 && perms.size() == 2) {
            imagePicker();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        } else {
            EasyPermissions.requestPermissions(this, "Hãy cấp quyền camera và bộ nhớ", 100, perms.toArray(new String[0]));
        }
    }

    private String convertDate(String date) {
        String[] arr = date.split("/");
        int day = Integer.parseInt(arr[0]);
        int month = Integer.parseInt(arr[1]);
        if (day < 10) {
            arr[0] = "0" + day;
        }
        if (month < 10) {
            arr[1] = "0" + month;
        }
        return arr[2] + "-" + arr[1] + "-" + arr[0];
    }

    private Uri resizeImage(Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            File file = new File(getCacheDir(), "avatar.jpg");
            FileOutputStream out = new FileOutputStream(file);
            resized.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            return Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean bindingValidate() {
        return ValidateUtil.isPictureValid(path, this)
                && ValidateUtil.isNameValid(etName)
                && ValidateUtil.isDateOfBirthValid(etDateOfBirth)
                && ValidateUtil.isPhoneValid(etPhone)
                && ValidateUtil.isGenderValid(rgGender, this)
                && ValidateUtil.isAddressValid(etAddress);

    }
}
