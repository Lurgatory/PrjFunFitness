package com.example.prjweightrecords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//declare the onclick
public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    //fields
    EditText signupName, signupEmail, signupUsername, signupPassword;
    TextView loginRedirectText;
    Button signupButton;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //declare
        initialize();
    }
   //create
    private void initialize() {
		//find the UI id
        signupName = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        signupUsername = findViewById(R.id.signup_username);
        signupPassword = findViewById(R.id.signup_password);
        signupButton = findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);

        signupButton.setOnClickListener(this);
    }
   //create the onclick--its a standard db connect then get the input id then refrnce child then toast and go to new page
    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent i;
        switch (id){
            case R.id.signup_button:
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");

                String name = signupName.getText().toString();
                String email = signupEmail.getText().toString();
                String username = signupUsername.getText().toString();
                String password = signupPassword.getText().toString();

                User user = new User(name,email,username,password);
                reference.child(username).setValue(user);

                Toast.makeText(this,"You have signup successfully!",Toast.LENGTH_LONG).show();
                i = new Intent(this, LoginActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.loginRedirectText:
                i = new Intent(this, LoginActivity.class);
                startActivity(i);
                finish();
                break;

        }
    }
}