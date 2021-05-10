package com.ochoa.bryan.findtattoo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.ochoa.bryan.findtattoo.databinding.ActivityAuthBinding;
import com.ochoa.bryan.findtattoo.databinding.ActivityMainBinding;
import com.ochoa.bryan.findtattoo.databinding.ActivityProfileBinding;
import com.ochoa.bryan.findtattoo.ui.MainActivity;

public class AuthActivity extends AppCompatActivity {
    //init firebase auth
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    //view binding
    private ActivityAuthBinding binding;
    private static final int RC_SIGN_IN = 100;
    private GoogleSignInClient googleSignInClient;
    private static final String TAG = "GOOGLE_SIGN_IN_TAG";
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //Configure the google SignIn
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        //init firebase auth
        checkUser();
        //Google SignInButton: Click to begin Google SignIn
        binding.Googlebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //begin google sign in
                Log.d(TAG, "onClick: begin Google SignIn");
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent, RC_SIGN_IN);
            }
        });

        //
        binding.gmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AuthActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
        binding.Loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.editTextTextEmailAddress.getText().toString();
                String password = binding.editTextTextPassword.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(AuthActivity.this, R.string.alert_Login, Toast.LENGTH_SHORT).show();
                } else {
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, String.valueOf(R.string.alert_Sucess));
                                Toast.makeText(AuthActivity.this, R.string.alert_Sucess, Toast.LENGTH_SHORT).show();
                                FirebaseUser user = auth.getCurrentUser();
                                Intent i = new Intent(AuthActivity.this, ProfileActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                Log.w(TAG, String.valueOf(R.string.alert_loginFailed), task.getException());
                                Toast.makeText(AuthActivity.this, R.string.alert_loginFailed, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });

    }  // END OF ONCREATE

    private void checkUser() {
        // if user is already signed in then go to profile activity
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null) {
            Log.d(TAG, "checkUser: Already logged in");
            startActivity(new Intent(this, ProfileActivity.class));
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Result returned from launching the Intent from GoogleSignInApi.GetSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Log.d(TAG, "onActivityResult: Google Signin intent result");
            Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //google sign in success, now auth with firebase
                GoogleSignInAccount account = accountTask.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                //firebaseAuthWithGoogleAccount(account);
                firebaseAuthWithGoogleAccount(account.getIdToken());
            } catch (ApiException e) {
                //Login failed
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    //private void firebaseAuthWithGoogleAccount(GoogleSignInAccount account) {
    private void firebaseAuthWithGoogleAccount(String account) {
        Log.d(TAG, "firebaseAuthGoogleAccount: begin firebase auth with google account");
        AuthCredential credential = GoogleAuthProvider.getCredential(account, null);
        auth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //login success
                        Log.d(TAG, "onSuccess: Logged In");

                        // get logged in user
                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        //get user info
                        String uid = firebaseUser.getUid();
                        String email = firebaseUser.getEmail();

                        Log.d(TAG, "onSuccess: Email: " + email);
                        Log.d(TAG, "onSuccess: UID: " + uid);
                        //check if user is new or existing
                        if (authResult.getAdditionalUserInfo().isNewUser()) {
                            Log.d(TAG, "onSucess: Account Created...\n" + email);
                            Toast.makeText(AuthActivity.this, "Account Created...\n" + email, Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(TAG, "onSucess: Existing user...\n" + email);
                            Toast.makeText(AuthActivity.this, "Inicio de sesión con: " + email, Toast.LENGTH_SHORT).show();
                        }
                        //start profile activity
                        startActivity(new Intent(AuthActivity.this, ProfileActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //login failed
                Log.d(TAG, "onFailure: Loggin failed" + e.getMessage());
            }
        });
    }

    public void loginFacebook(View view) {
        Toast.makeText(this, R.string.alert_workInProgress, Toast.LENGTH_SHORT).show();
    }
}

