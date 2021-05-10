package com.ochoa.bryan.findtattoo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ochoa.bryan.findtattoo.databinding.ActivityProfileBinding;
import com.ochoa.bryan.findtattoo.ui.MainActivity;

public class ProfileActivity extends AppCompatActivity {

    //view binding
    private ActivityProfileBinding binding;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // init firebase auth
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();
    } // END OF ONCREATE

    private void checkUser() {
        //get current user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            startActivity(new Intent(this, AuthActivity.class));
        }
        else{
            //user logged in
            //get user info
            String email = firebaseUser.getEmail();
            //set email
            //binding.emailTv.setText(email);
        }
    }

    public void cerrarSesion(View view) {
        //firebaseAuth.signOut();
        firebaseAuth.getInstance().signOut();
        Toast.makeText(ProfileActivity.this, R.string.alert_closeSession, Toast.LENGTH_SHORT).show();
        checkUser();
    }
    public void cerrarSesion2(){
        //firebaseAuth.signOut();
        firebaseAuth.getInstance().signOut();
        Toast.makeText(ProfileActivity.this, R.string.alert_closeSession, Toast.LENGTH_SHORT).show();
        checkUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_CloseSession:
                cerrarSesion2();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}