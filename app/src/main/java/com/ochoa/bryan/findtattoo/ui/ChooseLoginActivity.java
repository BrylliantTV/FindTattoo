package com.ochoa.bryan.findtattoo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ochoa.bryan.findtattoo.R;

public class ChooseLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_login);
    }

    public void loginClient(View view) {
        Intent i = new Intent(ChooseLoginActivity.this, AuthActivity.class);
        startActivity(i);
    }

    public void loginPartner(View view) {
        Toast.makeText(this, R.string.alert_workInProgress, Toast.LENGTH_SHORT).show();
    }
}