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

    private ImageButton ibClose;
    private EditText edLessonTitle, edLessonContent;
    private Button btnLessonSubmit,btnLessonDelete;

    private String lessonTitle, lessonContent, intentUsername,action,author;

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

    private void initialize() {
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

        btnLessonSubmit.setOnClickListener(this);
        btnLessonDelete.setOnClickListener(this);
        ibClose.setOnClickListener(this);

        firebase = FirebaseDatabase.getInstance();
        lessonReference = firebase.getReference("users");
    }

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

    private void deleteLesson() {
        lessonTitle = edLessonTitle.getText().toString();

        lessonReference.child(intentUsername).child("lesson").child(lessonTitle).removeValue();
    }

    private void submitLesson(View view) {
        lessonTitle = edLessonTitle.getText().toString();
        lessonContent = edLessonContent.getText().toString();

//        intentUsername = getIntent().getStringExtra("username");

        Lesson lesson = new Lesson(lessonTitle,lessonContent, intentUsername,date);

        lessonReference.child(intentUsername).child("lesson").child(lessonTitle).setValue(lesson);

        Toast.makeText(this,"Lesson Add Successfully", Toast.LENGTH_LONG).show();

        this.finish();

    }
}


