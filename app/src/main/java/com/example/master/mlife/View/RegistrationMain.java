package com.example.master.mlife.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.master.mlife.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationMain extends AppCompatActivity implements View.OnClickListener  {

    private FirebaseAuth mAuth;
    private EditText mETEmail;
    private EditText mETPassword;
    private static final String TAG = "EmailPassword";
    FirebaseUser us1;

    public static final String APP_PREFERENCES = "myParameters";

    public static final String APP_PREFERENCES_EMAIL = "Email";
    public static final String APP_PREFERENCES_UID = "UID";

    SharedPreferences mParams;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_layout);
        mETEmail = findViewById(R.id.et_email);
        mETPassword = findViewById(R.id.et_password);
        findViewById(R.id.bt_sign_in).setOnClickListener(this);
        findViewById(R.id.bt_registration).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();

        mParams = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        if(mParams.contains(APP_PREFERENCES_EMAIL)) {
            mETEmail.setText(mParams.getString(APP_PREFERENCES_EMAIL, ""));
        }


    }
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    }

    public void createAccount(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistrationMain.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {



            findViewById(R.id.bt_registration).setVisibility(View.VISIBLE);
        } else {
          //  findViewById(R.id.bt_sign_in.setVisibility(View.GONE);
        }
    }



    public void stopedActivity(){

        SharedPreferences.Editor editor = mParams.edit();
        editor.putString(APP_PREFERENCES_EMAIL, mETEmail.getText().toString());
        editor.putString(APP_PREFERENCES_UID, us1.getUid());
        editor.apply();


        Intent intent = new Intent();
        intent.putExtra("email",mETEmail.getText().toString());
        intent.putExtra("userUId", us1.getUid());
        setResult(RESULT_OK,intent);
        finish();
    }


    public void signIn (String email, String password){
        Log.d(TAG, "signIn:" + email);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener <AuthResult>() {
            @Override
            public void onComplete(@NonNull Task <AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    us1=user;
                    updateUI(user);
                    Toast.makeText(RegistrationMain.this, "Авторизация успешна", Toast.LENGTH_SHORT).show();
                    stopedActivity();
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(RegistrationMain.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    updateUI(null);
                    Toast.makeText(RegistrationMain.this, "Авторизация провалена", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    public void getCurrentUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            boolean emailVerified = user.isEmailVerified();
            String uid = user.getUid();
        }
    }


    public void regestration (String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener <AuthResult>() {
            @Override
            public void onComplete(@NonNull Task <AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    us1=user;
                    updateUI(user);
                    Toast.makeText(RegistrationMain.this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
                    stopedActivity();
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    updateUI(null);
                    Toast.makeText(RegistrationMain.this, "Регистрация провалена", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.bt_sign_in){
            signIn(mETEmail.getText().toString(),mETPassword.getText().toString());
        }else if(view.getId()==R.id.bt_registration){
            regestration(mETEmail.getText().toString(),mETPassword.getText().toString());
        }
    }
}
