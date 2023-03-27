package com.example.prjweightrecords;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

// To Roberto: to connect the workout plan information with firebase, you have to write down the code at here.
public class Add_New_Workout extends AppCompatActivity implements View.OnClickListener {

    private ImageButton ibClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_workout);
        initialize();
    }

    private void initialize() {
        ibClose = findViewById(R.id.ibClose);
        ibClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.ibClose:
                finish();
                return;
        }
    }
}