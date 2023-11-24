package com.example.prjweightrecords;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import model.Lesson;

public class Add_New_Lesson extends AppCompatActivity implements View.OnClickListener {

    //18/04/2023 - make UI vars fields
    private ImageButton ibClose;
    private EditText edLessonTitle, edLessonContent;
    private Button btnLessonSubmit,btnLessonDelete;

    private String lessonTitle, lessonContent, intentUsername,action,author;

    //18/04/2023 - declare the DB
    FirebaseDatabase firebase;
    DatabaseReference lessonReference;

    //get Value of Data today
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    // Get today's date as a Date object
    Date today = new Date();
    // Convert the Date object to a string using the SimpleDateFormat object
    String date = dateFormat.format(today);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_lesson);
        initialize();
    }

    //18/04/2023 -  create this method
    private void initialize() {
        //18/04/2023 -- get the UI id
        ibClose = findViewById(R.id.ibClose);
        edLessonTitle = findViewById(R.id.edLessonTitle);
        edLessonContent = findViewById(R.id.edLessonContent);
        btnLessonSubmit = findViewById(R.id.btnLessonSubmit);
        btnLessonDelete = findViewById(R.id.btnLessonDelete);


        action = getIntent().getStringExtra("action");
        lessonTitle = getIntent().getStringExtra("title");
        lessonContent = getIntent().getStringExtra("content");
        author = getIntent().getStringExtra("author");
        intentUsername = getIntent().getStringExtra("username");

        Toast.makeText(this,author + " // " + intentUsername,Toast.LENGTH_LONG).show();

        edLessonTitle.setText(lessonTitle);
        edLessonContent.setText(lessonContent);

        if (action.equals("add")){
            btnLessonDelete.setVisibility(View.INVISIBLE);
        }else if (action.equals("modify") && !author.equals(intentUsername)){
            btnLessonSubmit.setText("Modify");
            btnLessonDelete.setVisibility(View.INVISIBLE);
            btnLessonSubmit.setVisibility(View.INVISIBLE);
        }else{
            btnLessonSubmit.setText("Modify");
        }

        //18/04/2023 -- set the listners
        btnLessonSubmit.setOnClickListener(this);
        btnLessonDelete.setOnClickListener(this);
        ibClose.setOnClickListener(this);

        //18/04/2023 -- connect to the DB
        firebase = FirebaseDatabase.getInstance();
        lessonReference = firebase.getReference("users");
    }

    //18/04/2023 -- create the onclick ()
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.ibClose:
                finish();
                break;
            case R.id.btnLessonSubmit:
                submitLesson(view);
                break;
            case R.id.btnLessonDelete:
                deleteLesson();
                finish();
                break;
        }
    }

    //18/04/2023 -- method to submit lesson --
    private void deleteLesson() {
        lessonTitle = edLessonTitle.getText().toString();

        lessonReference.child(intentUsername).child("lesson").child(lessonTitle).removeValue();
    }

    //18/04/2023 -- method to submit lesson --
    private void submitLesson(View view) {
        //18/04/2023 -- get the user input
        lessonTitle = edLessonTitle.getText().toString();
        lessonContent = edLessonContent.getText().toString();

        //18/04/2023 -- create object from class
        Lesson lesson = new Lesson(lessonTitle,lessonContent, intentUsername,date);

        //18/04/2023 -- refer to child docs in the db
        lessonReference.child(intentUsername).child("lesson").child(lessonTitle).setValue(lesson);

        //18/04/2023 alert output
        Toast.makeText(this,"Lesson Add Successfully", Toast.LENGTH_LONG).show();

        this.finish();

    }
}


