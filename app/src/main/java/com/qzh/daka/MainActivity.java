package com.qzh.daka;


import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.qzh.daka.fragments.ClockInFragment;
import com.qzh.daka.fragments.ClockInHistoryFragment;
import com.qzh.daka.fragments.HealthDetailFragment;
import com.qzh.daka.fragments.LogUserFragment;
import com.qzh.daka.service.LogUserService;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);
        DrawerLayout drawerLayout = findViewById(R.id.dl_activity_main);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        navigationView.setCheckedItem(R.id.nav_clock_in);
        boolean isEmpty = LogUserService.isEmpty(this);
        if (isEmpty) {
            showUserEmptyDialog(MainActivity.this);
        }
        initFragment(new ClockInFragment());
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.nav_log_user:
                    commitFragment(new LogUserFragment());
                    invalidateOptionsMenu();
                    break;
                case R.id.nav_health_detail:
                    commitFragment(new HealthDetailFragment());
                    invalidateOptionsMenu();
                    break;
                case R.id.nav_history:
                    commitFragment(new ClockInHistoryFragment());
                    invalidateOptionsMenu();
                    break;
                default:
                    commitFragment(new ClockInFragment());
                    invalidateOptionsMenu();
                    break;

            }
            drawerLayout.closeDrawers();
            return true;
        });
    }


    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction transaction = fragmentManager.beginTransaction();

    public void initFragment(Fragment fragment) {
        transaction.add(R.id.container, fragment);
        transaction.commit();
    }

    public void commitFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            DrawerLayout drawerLayout = findViewById(R.id.dl_activity_main);
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void showUserEmptyDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage("未检测到已验证用户,请先完成个人信息补全~");
        builder.setCancelable(false);
        builder.setNegativeButton("好的", (dialog, which) -> {
            commitFragment(new LogUserFragment());
            invalidateOptionsMenu();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



}
