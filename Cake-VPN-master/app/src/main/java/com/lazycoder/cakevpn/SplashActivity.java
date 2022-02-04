package com.lazycoder.cakevpn;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.google.android.material.snackbar.Snackbar;
import com.lazycoder.cakevpn.view.MainActivity;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        loginUser();


    }

    private void loginUser() {
//        startActivity(new Intent(SplashActivity.this, MainActivity.class));
//        finish();
    }


}