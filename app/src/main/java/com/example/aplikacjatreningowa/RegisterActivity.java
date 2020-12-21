package com.example.aplikacjatreningowa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {
    EditText inputName,inputSurname,inputEmail,inputPassword; //declaring variables
    Button btnRegister;
    TextView gotAccount;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    ProgressBar progressRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputName = findViewById(R.id.inputName);   //connecting to the XML resources
        inputSurname = findViewById(R.id.inputSurname);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        btnRegister = findViewById(R.id.buttonLogin);
        gotAccount = findViewById(R.id.gotAccount);
        progressRegister = findViewById(R.id.progressRegister);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

       if(fAuth.getCurrentUser() != null) {
           startActivity(new Intent(getApplicationContext(), MainActivity.class));
           finish();
       }

        gotAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() { //what happens after button is clicked
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString();   //getting Text from input EditText to local variables
                String surname = inputSurname.getText().toString();
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                if(TextUtils.isEmpty(email)){
                    inputEmail.setError("E-mail jest wymagany!");
                    return;
                }
                if(TextUtils.isEmpty(name)){
                    inputName.setError("Imie jest wymagane!");
                    return;
                }
                if(TextUtils.isEmpty(surname)){
                    inputSurname.setError("Nazwisko jest wymagane!");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    inputPassword.setError("Hasło jest wymagane!");
                    return;
                }
                if(password.length() < 5){
                    inputPassword.setError("Hasło musi mieć więcej niz 5 znaków!");
                    return;
                }

                progressRegister.setVisibility(View.VISIBLE);

                //register the user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            StyleableToast.makeText(RegisterActivity.this,"Pomyślnie zarejestrowano!",R.style.toastBlue).show();

                            userID = fAuth.getCurrentUser().getUid();   // getting the userUID from current registering user
                            DocumentReference dR = fStore.collection("Users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("Name",name);
                            user.put("Username",surname);
                            user.put("Email",email);
                            dR.set(user);
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                            progressRegister.setVisibility(View.INVISIBLE);
                        }
                        else{
                            StyleableToast.makeText(RegisterActivity.this,"Błąd " + task.getException().getMessage(),R.style.toastBlue).show();
                            progressRegister.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });

    }
}