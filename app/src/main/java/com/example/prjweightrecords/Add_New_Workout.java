package com.example.prjweightrecords;

import androidx.appcompat.app.AppCompatActivity;
//imprt the UI
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
//import databse
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
//import this class
import model.Workout;

// To Roberto: to connect the workout plan information with firebase, you have to write down the code at here.
public class Add_New_Workout extends AppCompatActivity implements View.OnClickListener {
    //fields
    private ImageButton ibClose;
    private Button btnAdd,btnDelete;
    private EditText edWorkoutName,edRepetition,edSet;
    private String strWorkoutName, strRepetition, strSet, userName, date, action;
    private int workoutIndex;

    DatabaseReference workoutDatabase;

//    //get Value of Data today
//    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//    // Get today's date as a Date object
//    Date today = new Date();
//    // Convert the Date object to a string using the SimpleDateFormat object
//    String date = dateFormat.format(today);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_workout);
		//declare
        initialize();
    }
   //create
    private void initialize() {
		//get the UI id
        ibClose = findViewById(R.id.ibClose);
        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.btnDelete);
        edWorkoutName = findViewById(R.id.edWorkoutName);
        edRepetition = findViewById(R.id.edRep);
        edSet = findViewById(R.id.edSets);
        ibClose.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        strWorkoutName = getIntent().getStringExtra("workoutName");
        strRepetition = getIntent().getStringExtra("repetition");
        strSet = getIntent().getStringExtra("set");
        date = getIntent().getStringExtra("date");
        action = getIntent().getStringExtra("action");

        if (action.equals("add")){
            btnDelete.setVisibility(View.INVISIBLE);

        }else{
            btnAdd.setText("Modify");
        }

        edWorkoutName.setText(strWorkoutName);
        edRepetition.setText(strRepetition);
        edSet.setText(strSet);

        //connect the db
        workoutDatabase = FirebaseDatabase.getInstance().getReference("users");
    }
//create the onclick
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.ibClose:
                finish();
                break;
            case R.id.btnAdd:
                addWorkout(view);
                finish();
                break;
            case R.id.btnDelete:
                deleteWorkout();
                finish();
                break;
        }
    }

    private void deleteWorkout() {
        userName = getIntent().getStringExtra("username");
        workoutIndex = getIntent().getIntExtra("workoutIndex",0);
        workoutDatabase.child(userName).child("record").child(date).child("workout").child(String.valueOf(workoutIndex)).removeValue();
    }

    //custom method
    private void addWorkout(View view) {
        //
        strWorkoutName = edWorkoutName.getText().toString();
        strRepetition = edRepetition.getText().toString();
        strSet = edSet.getText().toString();

        userName = getIntent().getStringExtra("username");
        workoutIndex = getIntent().getIntExtra("workoutIndex",0);
        Workout workout = new Workout(strWorkoutName,strRepetition,strSet);
        workoutDatabase.child(userName).child("record").child(date).child("workout").child(String.valueOf(workoutIndex)).setValue(workout);


    }
}