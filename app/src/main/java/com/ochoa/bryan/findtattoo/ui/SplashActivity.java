package com.ochoa.bryan.findtattoo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.google.android.gms.auth.api.Auth;
import com.ochoa.bryan.findtattoo.AuthActivity;
import com.ochoa.bryan.findtattoo.ChooseLoginActivity;
import com.ochoa.bryan.findtattoo.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_splash);
        Thread thread = new Thread() {
            public void run () {
                try {
                    sleep(2000);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    Intent intent = new Intent(SplashActivity.this, ChooseLoginActivity.class);
                    startActivity(intent);
                }
            }
        };thread.start();
    }
}