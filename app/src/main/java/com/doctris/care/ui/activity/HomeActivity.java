package com.doctris.care.ui.activity;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.doctris.care.R;
import com.doctris.care.ui.fragment.AccountFragment;
import com.doctris.care.ui.fragment.HomeFragment;
import com.doctris.care.ui.fragment.NewFragment;
import com.doctris.care.ui.fragment.BlogFragment;
import com.doctris.care.ui.fragment.UpComingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.messaging.FirebaseMessaging;

import pub.devrel.easypermissions.EasyPermissions;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton fab;
    private final HomeFragment homeFragment = new HomeFragment();
    private final AccountFragment accountFragment = new AccountFragment();
    private final NewFragment newFragment = new NewFragment();
    private final BlogFragment blogFragment = new BlogFragment();
    private final UpComingFragment upComingFragment = new UpComingFragment();

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bindingView();
        setSupportActionBar(toolbar);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
                    break;
                case R.id.upcoming:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, upComingFragment).commit();
                    break;
                case R.id.search:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, blogFragment).commit();
                    break;
                case R.id.account:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, accountFragment).commit();
                    break;
                default:
                    break;
            }
            return true;
        });
        openFabNewIcon();
        notificationRequest();
        firebaseToken();
    }

    private void notificationRequest() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!EasyPermissions.hasPermissions(this, android.Manifest.permission.POST_NOTIFICATIONS)) {
                EasyPermissions.requestPermissions(this, "Chúng tôi cần quyền thông báo để gửi đến bạn những tin tức mới", 1, android.Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }

    private void firebaseToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                return;
            }
            String token = task.getResult();
            String msg = getString(R.string.msg_token_fmt, token);
            Log.d(TAG, msg);
        });
    }

    private void bindingView() {
        toolbar = findViewById(R.id.action_bar);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fab = findViewById(R.id.fab);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void openFabNewIcon() {
        fab.setOnClickListener(v -> getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_up, R.anim.slide_down).replace(R.id.fragment_container, newFragment).commit());
    }

}
