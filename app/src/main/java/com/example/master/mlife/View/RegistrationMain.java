package com.example.master.mlife.View;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.master.mlife.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationMain extends AppCompatActivity implements View.OnClickListener  {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private EditText mETEmail;
    private EditText mETPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_layout);

        mAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){

                }else {

                }
            }
        };

        mETEmail = (EditText)findViewById(R.id.et_email);
        mETPassword = (EditText)findViewById(R.id.et_password);

        findViewById(R.id.bt_sign_in).setOnClickListener(this);
        findViewById(R.id.bt_registration).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.bt_sign_in){
            signin(mETEmail.getText().toString(),mETPassword.getText().toString());
        }else if(view.getId()==R.id.bt_registration){
            regestration(mETEmail.getText().toString(),mETPassword.getText().toString());
        }
    }

    public void signin(String email, String password){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(RegistrationMain.this, "Авторизация успешна", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(RegistrationMain.this, "Авторизация провалена", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void regestration(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(RegistrationMain.this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(RegistrationMain.this, "Регистрация провалена", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
