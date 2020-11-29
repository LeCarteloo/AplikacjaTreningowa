package com.example.aplikacjatreningowa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    TextView newUser;
    EditText inputEmail,inputPassword;
    FirebaseAuth fAuth;
    ProgressBar progressLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.buttonLogin);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        progressLogin = findViewById(R.id.progressLogin);
        fAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                if(email.isEmpty()){
                    inputEmail.setError("E-mail jest wymagany!");
                    return;
                }
                if(password.isEmpty()){
                    inputPassword.setError("Hasło jest wymagane!");
                    return;
                }
                if(password.length() < 5){
                    inputPassword.setError("Hasło musi mieć więcej niz 5 znaków!");
                    return;
                }

                progressLogin.setVisibility(View.VISIBLE);
                // authenticate the user
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            StyleableToast.makeText(LoginActivity.this, "Pomyślnie zalogowano!", R.style.toastBlue).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            progressLogin.setVisibility(View.INVISIBLE);
                        }
                        else{
                            StyleableToast.makeText(LoginActivity.this,"Błąd " + task.getException().getMessage(),R.style.toastBlue).show();
                            progressLogin.setVisibility(View.INVISIBLE);
                        }

                    }
                });

            }
        });

        newUser = (TextView) findViewById(R.id.newUser);
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });
    }

    public void openRegisterActivity(){
        Intent registerActivity = new Intent(this, RegisterActivity.class);
        startActivity(registerActivity);
    }


}