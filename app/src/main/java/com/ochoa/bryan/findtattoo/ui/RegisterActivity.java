package com.ochoa.bryan.findtattoo.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.ochoa.bryan.findtattoo.R;
import com.ochoa.bryan.findtattoo.databinding.ActivityRegisterBinding;

/**
 * ACTIVIDAD DE REGISTRO DE LA APLICACIÓN
 */
public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private AwesomeValidation awesomeValidation;
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.etEmail, Patterns.EMAIL_ADDRESS, R.string.invalid_mail);
        awesomeValidation.addValidation(this, R.id.etPassword, ".{6,}", R.string.invalid_password);


        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = binding.etEmail.getText().toString();
                String pass = binding.etPassword.getText().toString();

                if(awesomeValidation.validate()){
                    firebaseAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, R.string.alert_registerSucess, Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(RegisterActivity.this, AuthActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                ToastErrorCode(errorCode);
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this, R.string.alert_Login, Toast.LENGTH_SHORT).show();
                }
            }
        });
    } // END OF ONCREATE

    /**
     *
     * @param error
     * Con este metodo tenemos un switch con el que controlamos los fallos que queremos que ocurran en caso de
     * introducir algun dato mal a la hora del registro, como pueda ser una contraseña demasiado pequeña o un correo invalido
     */
    private void ToastErrorCode(String error) {

        switch (error) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                Toast.makeText(RegisterActivity.this, R.string.ERROR_INVALID_CUSTOM_TOKEN, Toast.LENGTH_LONG).show();
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                Toast.makeText(RegisterActivity.this, R.string.ERROR_CUSTOM_TOKEN_MISMATCH, Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(RegisterActivity.this, R.string.ERROR_INVALID_CREDENTIAL, Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_EMAIL":
                Toast.makeText(RegisterActivity.this, R.string.ERROR_INVALID_EMAIL, Toast.LENGTH_LONG).show();
                binding.etEmail.setError("La dirección de correo electrónico está mal formateada.");
                binding.etEmail.requestFocus();
                break;

            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(RegisterActivity.this, R.string.ERROR_WRONG_PASSWORD, Toast.LENGTH_LONG).show();
                binding.etPassword.setError("La contraseña es incorrecta");
                binding.etPassword.requestFocus();
                binding.etPassword.setText("");
                break;

            case "ERROR_USER_MISMATCH":
                Toast.makeText(RegisterActivity.this, R.string.ERROR_USER_MISMATCH, Toast.LENGTH_LONG).show();
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                Toast.makeText(RegisterActivity.this,R.string.ERROR_REQUIRES_RECENT_LOGIN, Toast.LENGTH_LONG).show();
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                Toast.makeText(RegisterActivity.this, R.string.ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL, Toast.LENGTH_LONG).show();
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(RegisterActivity.this, R.string.ERROR_EMAIL_ALREADY_IN_USE, Toast.LENGTH_LONG).show();
                binding.etEmail.setError("La dirección de correo electrónico ya está siendo utilizada por otra cuenta.");
                binding.etEmail.requestFocus();
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                Toast.makeText(RegisterActivity.this, R.string.ERROR_CREDENTIAL_ALREADY_IN_USE, Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_DISABLED":
                Toast.makeText(RegisterActivity.this, R.string.ERROR_USER_DISABLED, Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                Toast.makeText(RegisterActivity.this, R.string.ERROR_USER_TOKEN_EXPIRED, Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(RegisterActivity.this, R.string.ERROR_USER_NOT_FOUND, Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_USER_TOKEN":
                Toast.makeText(RegisterActivity.this, R.string.ERROR_INVALID_USER_TOKEN, Toast.LENGTH_LONG).show();
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                Toast.makeText(RegisterActivity.this, R.string.ERROR_OPERATION_NOT_ALLOWED, Toast.LENGTH_LONG).show();
                break;

            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(RegisterActivity.this, R.string.ERROR_WEAK_PASSWORD, Toast.LENGTH_LONG).show();
                binding.etPassword.setError("La contraseña no es válida, debe tener al menos 6 caracteres");
                binding.etPassword.requestFocus();
                break;

        }

    }

    public void cancelRegister(View view) {
        Intent i = new Intent(RegisterActivity.this, AuthActivity.class);
        startActivity(i);
    }
}