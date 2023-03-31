package com.example.prjweightrecords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    // Add btnlogin and tvSignup variables
    EditText loginUsername, loginPassword;
    Button loginButton;
    TextView tvSignupRedirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();
    }

    // make login and signup button
    private void initialize() {
        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        tvSignupRedirect = findViewById(R.id.signupRedirectText);
        loginButton.setOnClickListener(this);
        tvSignupRedirect.setOnClickListener(this);

    }

    public Boolean validateUsername(){
        String loginUsernameText = loginUsername.getText().toString();
        if (loginUsernameText.isEmpty()){
            loginUsername.setError("Username cannot be empty");
            return false;
        }else{
            loginUsername.setError(null);
            return true;
        }
    }

    public Boolean validatePassword(){
        String loginPasswordText = loginPassword.getText().toString();
        if (loginPasswordText.isEmpty()){
            loginPassword.setError("Username cannot be empty");
            return false;
        }else{
            loginPassword.setError(null);
            return true;
        }
    }

    public void checkUser(){
        String userUsername = loginUsername.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    loginUsername.setError(null);
                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);
                    if (passwordFromDB.equals(userPassword)){
                        loginUsername.setError(null);
                        Intent i = new Intent( LoginActivity.this, MainActivity.class);
                        startActivity(i);
                    }else{
                        loginPassword.setError("Invalid Credentials");
                        loginPassword.requestFocus();
                    }
                }else{
                    loginUsername.setError("User does not exist");
                    loginUsername.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // jump to next page without validation
    @Override
    public void onClick(View view) {
        Intent i;
        int id = view.getId();
        switch (id){
            case R.id.login_button:
                if (!validatePassword() | !validateUsername()){
                }else{
                    checkUser();
                }
                break;
            case R.id.signupRedirectText:
                i = new Intent(this, SignupActivity.class);
                startActivity(i);
        }
    }
}