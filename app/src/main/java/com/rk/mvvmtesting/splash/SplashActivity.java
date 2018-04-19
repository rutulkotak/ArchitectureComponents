package com.rk.mvvmtesting.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rk.mvvmtesting.user.UserActivity;
import com.rk.mvvmtesting.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        showHomeScreen();
    }

    private void showHomeScreen() {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
        finish();
    }
}