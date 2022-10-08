package com.doctris.care.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.doctris.care.R;
import com.doctris.care.ui.fragment.AccountFragment;
import com.doctris.care.ui.fragment.HomeFragment;
import com.doctris.care.ui.fragment.NewFragment;
import com.doctris.care.ui.fragment.SearchFragment;
import com.doctris.care.ui.fragment.UpComingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton fab;
    private final HomeFragment homeFragment = new HomeFragment();
    private final AccountFragment accountFragment = new AccountFragment();
    private final NewFragment newFragment = new NewFragment();
    private final SearchFragment searchFragment = new SearchFragment();
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
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, searchFragment).commit();
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
