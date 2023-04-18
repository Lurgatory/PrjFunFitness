package com.example.prjweightrecords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TableRow;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import model.Body;
import model.Lesson;
import model.LessonAdapter;
import model.Meal;
import model.Record;
import model.RecordAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

//    Variables that we need to use
    private ImageButton ibNewRecordPlus,ibNewLessonPlus, ibNewMomentPlus, ibNewContactPlus,
            ibRecords, ibLesson, ibMoment, ibContact;

    private EditText edNewContact_Firstname, edNewContact_Lastname, edNewContactPhoneNumber;
    private Button btnNewContactAdd, btnNewContactCancel;
    private ListView lvWorkout, lvLesson;

    String username, action, author;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    ArrayList<Lesson> lessonArrayList;
    LessonAdapter lessonAdapter;

    ArrayList<Record> recordArrayList;
    RecordAdapter recordAdapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference recordReference, lessonReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        initialize();
    }

    private void initialize() {

        ibRecords = findViewById(R.id.ibRecords);
        ibLesson = findViewById(R.id.ibLesson);
        ibMoment = findViewById(R.id.ibMoment);
        ibContact = findViewById(R.id.ibContact);

        username = getIntent().getStringExtra("username");


        firebaseDatabase = FirebaseDatabase.getInstance();
        recordReference = firebaseDatabase.getReference("users").child(username).child("record");

        // Default page is the records page
        //Inflate built-in function which can put the module I created into the view.
        LayoutInflater l = getLayoutInflater();
        TableRow tableRow = findViewById(R.id.trBody);
        View v = l.inflate(R.layout.activity_records_page,null);
        lvWorkout = v.findViewById(R.id.lvRecordPageWorkout);
        ibNewRecordPlus = v.findViewById(R.id.ibNewBodyRecordPlus);
        displayRecords();
        tableRow.addView(v);

        ibRecords.setOnClickListener(this);
        ibLesson.setOnClickListener(this);
        ibMoment.setOnClickListener(this);
        ibContact.setOnClickListener(this);
        ibNewRecordPlus.setOnClickListener(this);
        lvWorkout.setOnItemClickListener(this);

    }

    // Add new contact popup window
    public void createNewContactDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopupView = getLayoutInflater().inflate(R.layout.popup_login,null);
        edNewContact_Firstname = (EditText) contactPopupView.findViewById(R.id.edNewContactFirstName);
        edNewContact_Lastname = (EditText) contactPopupView.findViewById(R.id.edNewContactLastName);
        edNewContactPhoneNumber = (EditText) contactPopupView.findViewById(R.id.edNewContactPhoneNumber);
        btnNewContactAdd = (Button) contactPopupView.findViewById(R.id.btnAddNewContact);
        btnNewContactCancel = (Button) contactPopupView.findViewById(R.id.btnNewContactCancel);

        dialogBuilder.setView(contactPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        btnNewContactAdd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

            }
        });

        btnNewContactCancel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


    // Calling views and modules by clicking different image buttons
    // To Roberto: To connect the listview with the date in the firebase, you have to write down the code in this part.
    @Override
    public void onClick(View view) {
        LayoutInflater l = getLayoutInflater();
        TableRow tableRow = findViewById(R.id.trBody);
        View v;
        Intent i;
        int id = view.getId();
        switch (id){
            case R.id.ibNewBodyRecordPlus:
                i = new Intent(this, Add_New_Record.class);
                //Pass value to next page
                username = getIntent().getStringExtra("username");
                i.putExtra("username",username);
                startActivity(i);
                break;
            case R.id.ibNewLessonPlus:
                action = "add";
                i = new Intent(this, Add_New_Lesson.class);
                i.putExtra("username",username);
                i.putExtra("action",action);
                startActivity(i);
                break;
            case R.id.ibNewMomentPlus:
                i = new Intent(this,Add_New_Moment.class);
                startActivity(i);
                break;
            case R.id.ibNewContactPlus:
                createNewContactDialog();
                break;
            case R.id.ibRecords:
                tableRow.removeAllViews();
                v = l.inflate(R.layout.activity_records_page, null);
                tableRow.addView(v);
                ibNewRecordPlus = v.findViewById(R.id.ibNewBodyRecordPlus);
                ibNewRecordPlus.setOnClickListener(this);
                lvWorkout = v.findViewById(R.id.lvRecordPageWorkout);
                lvWorkout.setOnItemClickListener(this);
                displayRecords();
                break;
            case R.id.ibLesson:
                tableRow.removeAllViews();
                v = l.inflate(R.layout.activity_lesson_page,null);
                tableRow.addView(v);
                ibNewLessonPlus = v.findViewById(R.id.ibNewLessonPlus);
                lvLesson = v.findViewById(R.id.lvLesson);
                lvLesson.setOnItemClickListener(this);
                ibNewLessonPlus.setOnClickListener(this);
                displayLesson();
                break;
            case R.id.ibMoment:
                tableRow.removeAllViews();
                v = l.inflate(R.layout.activity_moment_page,null);
                tableRow.addView(v);
                ibNewMomentPlus = v.findViewById(R.id.ibNewMomentPlus);
                ibNewMomentPlus.setOnClickListener(this);
                break;
            case R.id.ibContact:
                tableRow.removeAllViews();
                v = l.inflate(R.layout.activity_contact_page,null);
                tableRow.addView(v);
                ibNewContactPlus = v.findViewById(R.id.ibNewContactPlus);
                ibNewContactPlus.setOnClickListener(this);
                break;


        }
    }

    private void displayRecords(){

        recordArrayList = new ArrayList<>();
        recordAdapter = new RecordAdapter(this, recordArrayList);

        //
        recordReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                recordArrayList.clear();
                for (DataSnapshot ds:snapshot.getChildren()) {

                    String date = ds.getKey();
                    String breakfast = ds.child("meal").child("breakfast").getValue(String.class);
                    String lunch = ds.child("meal").child("lunch").getValue(String.class);
                    String dinner = ds.child("meal").child("dinner").getValue(String.class);
                    String weight = ds.child("body").child("weight").getValue(String.class);

                    Meal oneMeal = new Meal(breakfast, lunch, dinner);
                    Body oneBody = new Body(weight);
//                    Workout oneWorkout = new Workout(workoutName,repetition,set);
                    recordArrayList.add(new Record(date, oneBody, oneMeal));
                    recordAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        lvWorkout.setAdapter(recordAdapter);
    }

    private void displayLesson() {
        lessonArrayList = new ArrayList<>();
        lessonAdapter = new LessonAdapter(this, lessonArrayList);

        lessonReference = FirebaseDatabase.getInstance().getReference("users");

        lessonReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lessonArrayList.clear();
                for (DataSnapshot ds:snapshot.getChildren()){
                    for (DataSnapshot lessonds : ds.child("lesson").getChildren()){
                        String title = lessonds.child("title").getValue(String.class);
                        String authorName = lessonds.child("authorName").getValue(String.class);
                        String content = lessonds.child("content").getValue(String.class);
                        String date = lessonds.child("date").getValue(String.class);
                        Lesson oneLesson = new Lesson(""+title,""+content,""+authorName,""+date);
                        lessonArrayList.add(oneLesson);
                        lessonAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        lvLesson.setAdapter(lessonAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        if (adapterView.getId() == R.id.lvRecordPageWorkout){
            Intent intent = new Intent(this,Add_New_Record.class);
            intent.putExtra("username", username);
            intent.putExtra("date",recordArrayList.get(i).getDate());
            intent.putExtra("weight",recordArrayList.get(i).getBody().getWeight());
            intent.putExtra("breakfast",recordArrayList.get(i).getMeal().getBreakfast());
            intent.putExtra("lunch",recordArrayList.get(i).getMeal().getLunch());
            intent.putExtra("dinner",recordArrayList.get(i).getMeal().getDinner());
            startActivity(intent);
        } else if (adapterView.getId() == R.id.lvLesson) {
            action = "modify";
            Intent intent = new Intent(this, Add_New_Lesson.class);
            intent.putExtra("username", username);
            intent.putExtra("action",action);
            intent.putExtra("author",lessonArrayList.get(i).getAuthorName());
            intent.putExtra("title", lessonArrayList.get(i).getTitle());
            intent.putExtra("content", lessonArrayList.get(i).getContent());
            startActivity(intent);
        }
    }
}