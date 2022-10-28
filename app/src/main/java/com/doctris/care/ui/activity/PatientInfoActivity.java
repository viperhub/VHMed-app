package com.doctris.care.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.developer.kalert.KAlertDialog;
import com.doctris.care.R;
import com.doctris.care.entities.Patient;
import com.doctris.care.repository.PatientRepository;
import com.doctris.care.storage.SharedPrefManager;
import com.doctris.care.utils.AlertDialogUtil;
import com.doctris.care.utils.RealPathUtil;
import com.doctris.care.utils.ValidateUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class PatientInfoActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private EditText etName;
    private EditText etDateOfBirth;
    private RadioGroup rgGender;
    private RadioButton rbGender;
    private EditText etAddress;
    private EditText etPhone;
    private CircleImageView ivAvatar;
    private Button btnChooseImage;
    private Button btnSave;
    private Button backHome;
    private ArrayList<Uri> path = new ArrayList<>();
    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_information);
        patient = new Patient();
        bindingView();
        bindingData();
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
        backHome = findViewById(R.id.btn_back);
    }

    private void bindingAction() {
        btnChooseImage.setOnClickListener(this::onClickChooseImage);
        btnSave.setOnClickListener(this::onClickSave);
        backHome.setOnClickListener(this::onClickBack);
        etDateOfBirth.setOnClickListener(this::onClickDateOfBirth);
    }

    @SuppressLint("SetTextI18n")
    private void onClickDateOfBirth(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view1, year, month, dayOfMonth) -> etDateOfBirth.setText(dayOfMonth + "/" + (month + 1) + "/" + year), 2000, 0, 1);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void onClickBack(View view) {
        finish();
    }

    private void onClickSave(View view) {
        getUriFromImageView(ivAvatar);
        if (bindingValidate()) {
            AlertDialogUtil.loading(this);
            String realPath = RealPathUtil.getRealPath(this, resizeImage(path.get(0)));
            File avatar = new File(realPath);
            Patient patientUpdate = new Patient();
            patientUpdate.setId(patient.getId());
            patientUpdate.setName(etName.getText().toString());
            patientUpdate.setDateOfBirth(convertDate(etDateOfBirth.getText().toString()));
            patientUpdate.setAddress(etAddress.getText().toString());
            patientUpdate.setPhone(Integer.parseInt(etPhone.getText().toString()));
            patientUpdate.setGender(getGenderChecked());
            PatientRepository.getInstance().updatePatientInfo(patientUpdate, avatar).observe(this, status -> {
                AlertDialogUtil.stop();
                if (status.equals("success")) {
                    AlertDialogUtil.success(this, "Cập nhật thông tin", "Cập nhật thành công", "OK", KAlertDialog::dismissWithAnimation);
                } else {
                    AlertDialogUtil.error(this, "Cập nhật thông tin", "Cập nhật thất bại", "OK", null);
                }
            });
        }
    }

    @SuppressLint("SetTextI18n")
    private void bindingData() {
        patient = SharedPrefManager.getInstance().get("patient", Patient.class);
        etName.setText(patient.getName());
        etDateOfBirth.setText(patient.getDateOfBirth().split(" ")[0].split("-")[2] + "/" + patient.getDateOfBirth().split(" ")[0].split("-")[1] + "/" + patient.getDateOfBirth().split(" ")[0].split("-")[0]);
        etAddress.setText(patient.getAddress());
        etPhone.setText("0" + patient.getPhone());
        Glide.with(this).load(patient.getAvatar()).into(ivAvatar);
        if (patient.isGender()) {
            rbGender = findViewById(R.id.rb_male);
        } else {
            rbGender = findViewById(R.id.rb_female);
        }
        rbGender.setChecked(true);
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

    private void onClickChooseImage(View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                imagePicker();
            } else {
                EasyPermissions.requestPermissions(this, "Hãy cấp quyền camera và bộ nhớ", 100, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                imagePickerTiramisu();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        }
    }

    // photo picker for android 13 and above
    private void imagePickerTiramisu() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 100);
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
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            if (requestCode == FilePickerConst.REQUEST_CODE_PHOTO && resultCode == RESULT_OK && data != null) {
                path = new ArrayList<>();
                path.addAll(data.getParcelableArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
                Glide.with(this).load(path.get(0)).into(ivAvatar);
            }
        } else {
            if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
                Uri uri = data.getData();
                ivAvatar.setImageURI(uri);
                path.add(uri);
            }
        }
    }


    private Uri resizeImage(Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, 500, 500, true);
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

    private boolean getGenderChecked() {
        int id = rgGender.getCheckedRadioButtonId();
        return id == R.id.rb_male;
    }

    private void getUriFromImageView(CircleImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        path.add(0, resizeImage(Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null))));
    }

    private boolean bindingValidate() {
        return ValidateUtil.isPictureValid(path, this)
                && ValidateUtil.isNameValid(etName)
                && ValidateUtil.isDateOfBirthValid(etDateOfBirth)
                && ValidateUtil.isPhoneValid(etPhone)
                && ValidateUtil.isGenderValid(rgGender, this)
                && ValidateUtil.isAddressValid(etAddress);

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
}
