package com.example.recipeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText registerFullName, registerEmail, registerPassword,registerConfPass;
    Button registerUserBtn;
    FirebaseAuth fAuth;
    ImageView exitRegis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerFullName = findViewById(R.id.registerFullName);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        registerConfPass = findViewById(R.id.confPassword);
        registerUserBtn = findViewById(R.id.registerBtn);
        exitRegis = findViewById(R.id.exitRegis);

        fAuth = FirebaseAuth.getInstance();

        exitRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LogReg.class));
            }
        });


        registerUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // extract the data from the form

                String fullName = registerFullName.getText().toString();
                String email = registerEmail.getText().toString();
                String password = registerPassword.getText().toString();
                String confPass = registerConfPass.getText().toString();

                if(fullName.isEmpty()){
                    registerFullName.setError("Full Name is Required");
                    return;
                }

                if(email.isEmpty()){
                    registerEmail.setError("Email is Required");
                    return;
                }

                if(password.isEmpty()){
                    registerPassword.setError("Password is Required");
                    return;
                }

                if(password.length()<6){
                    registerPassword.setError("Password must be 6 or more length");
                }

                if(confPass.isEmpty()){
                    registerConfPass.setError("Confirm Password is Required");
                    return;
                }

                if(!password.equals(confPass)){
                    registerConfPass.setError("Password Do Not Match.");
                    return;
                }

                // data is validated
                // register the user.

                Toast.makeText(Register.this, "Data Validated", Toast.LENGTH_SHORT).show();

                fAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}