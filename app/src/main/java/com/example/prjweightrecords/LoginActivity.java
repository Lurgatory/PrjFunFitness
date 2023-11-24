package com.example.prjweightrecords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import intent and UI 
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
//import the databse
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;
import java.util.Objects;
//declare the onclick
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    // Add btnlogin and tvSignup variables
    EditText loginUsername, loginPassword;
    Button loginButton;
    TextView tvSignupRedirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
		//declare
        initialize();
    }

    //18/04/2023 -- create the initialize() here
    // make login and signup button
    private void initialize() {
		//get the UI id
        //18/04/2023 -- set the listener for login button and signup redirects
        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        tvSignupRedirect = findViewById(R.id.signupRedirectText);
        loginButton.setOnClickListener(this);
        tvSignupRedirect.setOnClickListener(this);

    }
//custom method //18/04/2023  -- validate if empty user input
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
//custom method //18/04/2023 -- validate if empty user input
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
//custom method //18/04/2023 -- validate check user input --
    public void checkUser(){
        //18/04/2023 -- trim the user entry --
        String userUsername = loginUsername.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();

        //18/04/2023  -- connect to the DB and to the collection needs -- users
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        //18/04/2023  -- user the buil in class query to make object for child referral
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);

        //18/04/2023 -- invoke listener for 1 event
        //18/04/2023 -- this also checks if user exists in the DB and if entry is valid
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //18/04/2023  -- this is a error block of code and redirect to anther activity --
                if (snapshot.exists()){
                    loginUsername.setError(null);
                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);
                    if (passwordFromDB.equals(userPassword)){
                        loginUsername.setError(null);
                        Intent i = new Intent( LoginActivity.this, MainActivity.class);
                        i.putExtra("username",userUsername);
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

    //18/04/2023 -- create the onclick ()
    // jump to next page without validation
    @Override
    public void onClick(View view) {
        //18/04/2023 -- this switch block that logs in the user or goes to signup page
        //18/04/2023 -- it checks if empty with methods from above
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