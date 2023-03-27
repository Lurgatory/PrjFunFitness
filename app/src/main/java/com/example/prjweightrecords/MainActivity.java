package com.example.prjweightrecords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

//    Variables that we need to use
    private ImageButton ibNewRecordPlus,ibNewLessonPlus, ibNewMomentPlus, ibNewContactPlus,
            ibRecords, ibLesson, ibMoment, ibContact;



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

        // Default page is the records page
        //Inflate built-in function which can put the module I created into the view.
        LayoutInflater l = getLayoutInflater();
        TableRow tableRow = findViewById(R.id.trBody);
        View v = l.inflate(R.layout.activity_records_page,null);
        ibNewRecordPlus = v.findViewById(R.id.ibNewBodyRecordPlus);
        tableRow.addView(v);


        ibRecords.setOnClickListener(this);
        ibLesson.setOnClickListener(this);
        ibMoment.setOnClickListener(this);
        ibContact.setOnClickListener(this);
        ibNewRecordPlus.setOnClickListener(this);

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
                startActivity(i);
                break;
            case R.id.ibNewLessonPlus:
                i = new Intent(this, Add_New_Lesson.class);
                startActivity(i);
                break;
            case R.id.ibNewMomentPlus:
                i = new Intent(this,Add_New_Moment.class);
                startActivity(i);
                break;
            case R.id.ibRecords:
                tableRow.removeAllViews();
                v = l.inflate(R.layout.activity_records_page, null);
                tableRow.addView(v);
                ibNewRecordPlus = v.findViewById(R.id.ibNewBodyRecordPlus);
                ibNewRecordPlus.setOnClickListener(this);
                break;
            case R.id.ibLesson:
                tableRow.removeAllViews();
                v = l.inflate(R.layout.activity_lesson_page,null);
                tableRow.addView(v);
                ibNewLessonPlus = v.findViewById(R.id.ibNewLessonPlus);
                ibNewLessonPlus.setOnClickListener(this);
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
                break;


        }
    }
}