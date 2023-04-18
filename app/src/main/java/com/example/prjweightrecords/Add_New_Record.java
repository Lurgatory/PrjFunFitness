package com.example.prjweightrecords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import for passing data btwn pages--all the UI--list and text--toast
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
//import snackbar--firebase--
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import arraylist--date-map
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
//import these classes
import model.Body;
import model.Meal;
import model.Workout;
import model.WorkoutAdapter;


//To Roberto: Now you have to write code for connecting with the firebase at here.
//declare the onClick and ValueEventListener
public class Add_New_Record extends AppCompatActivity implements View.OnClickListener, ValueEventListener, AdapterView.OnItemClickListener {
    //fields
    private ImageButton ibClose;
    private TextView tvAddNewWorkout;
    private Button btnSubmit;
    private EditText edDate,edWeight,edBreakfast,edLunch,edDinner;
    private ListView lvWorkoutExercise;

    ArrayList<Workout> workoutArrayList;
    WorkoutAdapter workoutAdapter;

    private String weight, breakfast, lunch, dinner, userName, action;
    private int workoutIndex =0;


    //Firebase connection
    FirebaseDatabase firebaseDatabase;
    DatabaseReference recordDatabase, recordWorkoutDatabase;

    //get Value of Data today
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    // Get today's date as a Date object
    Date today = new Date();
    // Convert the Date object to a string using the SimpleDateFormat object
    String date = dateFormat.format(today);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_record);
		//declare this method
        initialize();

    }
//create this method
    private void initialize() {

//        workoutArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,workoutArrayList);
        //find the UI id
        ibClose = findViewById(R.id.ibClose);
        tvAddNewWorkout = findViewById(R.id.tvAddWorkoutPlan);
        btnSubmit = findViewById(R.id.btnSubmit);
        edDate = findViewById(R.id.edDate);
        edWeight = findViewById(R.id.edWeight);
        edBreakfast = findViewById(R.id.edBreakfast);
        edLunch = findViewById(R.id.edLunch);
        edDinner = findViewById(R.id.edDinner);
        lvWorkoutExercise = findViewById(R.id.lvWorkoutExercises);
        lvWorkoutExercise.setOnItemClickListener(this);

        String intentDate = getIntent().getStringExtra("date");
        String intentBreakfast = getIntent().getStringExtra("breakfast");
        String intentLunch = getIntent().getStringExtra("lunch");
        String intentDinner = getIntent().getStringExtra("dinner");
        String intentWeight = getIntent().getStringExtra("weight");
        if (intentDate != null && intentBreakfast != null && intentLunch != null && intentDinner != null && intentWeight != null){
            date = intentDate;
            edBreakfast.setText(intentBreakfast);
            edLunch.setText(intentLunch);
            edDinner.setText(intentDinner);
            edWeight.setText(intentWeight);
        }

        edDate.setText(date);

        //Connect with Database
        firebaseDatabase = FirebaseDatabase.getInstance();
        recordDatabase = firebaseDatabase.getReference("users");

        userName = getIntent().getStringExtra("username");
        recordWorkoutDatabase = recordDatabase.child(userName).
                child("record").child(date);

        //Connect cell block to the workout list view
		//roberto comment--this is a complex block of code--start
        workoutArrayList = new ArrayList<Workout>();
        workoutAdapter = new WorkoutAdapter( this,R.layout.one_workout_exercise,workoutArrayList);

        recordWorkoutDatabase.child("workout").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                workoutArrayList.clear();
                for (DataSnapshot ds:snapshot.getChildren()){
                    workoutIndex = Integer.valueOf(ds.getKey());
                    String workoutName = ds.child("workoutName").getValue(String.class);
                    String repetition = ds.child("repetition").getValue(String.class);
                    String set = ds.child("set").getValue(String.class);
                    workoutArrayList.add(new Workout("" + workoutName,"" + repetition,"" + set));
                    workoutAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        lvWorkoutExercise.setAdapter(workoutAdapter);

        ibClose.setOnClickListener(this);
        tvAddNewWorkout.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }
//roberto comment--this is a complex block of code--end


//onClick creted method
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.ibClose:
                finish();
                break;
            case R.id.tvAddWorkoutPlan:
                Intent i = new Intent(this, Add_New_Workout.class);
//                userName = getIntent().getStringExtra("username");
                workoutIndex++;
                action = "add";
                i.putExtra("action",action);
                i.putExtra("date", edDate.getText().toString());
                i.putExtra("workoutIndex",workoutIndex);
                i.putExtra("username",userName);
                startActivity(i);
                break;
            case R.id.btnSubmit:
                submitRecord(view);
                finish();
                break;
        }
    }
//custom method --complex--
    private void submitRecord(View view) {
        //get value from the edittext
        weight = edWeight.getText().toString();
        breakfast = edBreakfast.getText().toString();
        lunch = edLunch.getText().toString();
        dinner = edDinner.getText().toString();
        //get username from login
        userName = getIntent().getStringExtra("username");
        //add data in the firebase
        Meal meal = new Meal(breakfast, lunch, dinner);
        Body body = new Body(weight);
        recordDatabase.child(userName).child("record").child(date).child("meal").setValue(meal);
        recordDatabase.child(userName).child("record").child(date).child("body").setValue(body);
        Toast.makeText(this," Add Successful",Toast.LENGTH_LONG).show();
    }
//complex code method
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(snapshot.exists()){
//            Map workout = (Map) snapshot.child("1").getValue();
//            Log.d("WORKOUT_FIREBASE",workout.toString());

            long count = snapshot.getChildrenCount();
            for (int i = 0; i < count; i++) {
                Map workout = (Map) snapshot.child(String.valueOf(i+1)).getValue();
                Log.d("WORKOUT_FIREBASE",workout.toString());
            }
//            Log.d("WORKOUT_FIREBASE", "Count: " + count);

        }else{
            Toast.makeText(this,"No found",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        action = "modify";
        Intent intent = new Intent(this, Add_New_Workout.class);
        intent.putExtra("date",edDate.getText().toString());
        intent.putExtra("action",action);
        intent.putExtra("workoutIndex",i+1);
        intent.putExtra("username",userName);
        intent.putExtra("workoutName",workoutArrayList.get(i).getWorkoutName());
        intent.putExtra("repetition",workoutArrayList.get(i).getRepetition());
        intent.putExtra("set",workoutArrayList.get(i).getSet());
        startActivity(intent);
    }
}