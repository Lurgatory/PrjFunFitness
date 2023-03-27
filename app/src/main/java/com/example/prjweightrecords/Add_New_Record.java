package com.example.prjweightrecords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


//To Roberto: Now you have to write code for connecting with the firebase at here.
//
public class Add_New_Record extends AppCompatActivity implements View.OnClickListener {

    private ImageButton ibClose;
    private TextView tvAddNewWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_record);
        initialize();
    }

    private void initialize() {
        ibClose = findViewById(R.id.ibClose);
        tvAddNewWorkout = findViewById(R.id.tvAddWorkoutPlan);
        ibClose.setOnClickListener(this);
        tvAddNewWorkout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.ibClose:
                finish();
                return;
            case R.id.tvAddWorkoutPlan:
                Intent i = new Intent(this, Add_New_Workout.class);
                startActivity(i);
        }
    }
}