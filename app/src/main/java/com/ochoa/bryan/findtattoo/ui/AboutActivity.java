package com.ochoa.bryan.findtattoo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.ochoa.bryan.findtattoo.ProfileActivity;
import com.ochoa.bryan.findtattoo.databinding.ActivityAboutBinding;

public class AboutActivity extends AppCompatActivity {

    private ActivityAboutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutBinding.inflate(getLayoutInflater());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(binding.getRoot());
    }

    public void btnBack(View view) {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }
}