package com.example.prjweightrecords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TableRow;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import model.Body;
import model.Contact;
import model.ContactAdapter;
import model.Lesson;
import model.LessonAdapter;
import model.Meal;
import model.Record;
import model.RecordAdapter;
import model.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

//    Variables that we need to use
//18/04/2023 --  declare the attributes, vars, db
    private ImageButton ibNewRecordPlus,ibNewLessonPlus, ibNewContactPlus,
            ibRecords, ibLesson, ibContact;
    private EditText edNewContact_Firstname, edNewContact_Lastname, edNewContactPhoneNumber;
    private Button btnNewContactAdd, btnNewContactCancel, btnNewContactDelete;
    private ListView lvWorkout, lvLesson, lvContact;
    private String username, action, firstName, lastName, phoneNumber;

    private int contactIndex = 0;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    ArrayList<Contact> contactArrayList;
    ContactAdapter contactAdapter;

    ArrayList<Lesson> lessonArrayList;
    LessonAdapter lessonAdapter;

    ArrayList<Record> recordArrayList;
    RecordAdapter recordAdapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference recordReference, lessonReference, contactReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        initialize();
    }

    //18/04/2023 -- create this method
    private void initialize() {

        //18/04/2023 -- get the UI id --
        ibRecords = findViewById(R.id.ibRecords);
        ibLesson = findViewById(R.id.ibLesson);
        ibContact = findViewById(R.id.ibContact);

        //18/04/2023 -- not sure but it seems connect to child/document
        //**************   13/04/2023 (Shengxiong):  it grabs the username value from the login because we gonna use it for creating data structure in firebase *********************
        username = getIntent().getStringExtra("username");

        //18/04/2023 -- connect to the DB
        firebaseDatabase = FirebaseDatabase.getInstance();
        recordReference = firebaseDatabase.getReference("users").child(username).child("record");

        // Default page is the records page
        //Inflate built-in function which can put the module I created into the view.
        LayoutInflater l = getLayoutInflater();
        TableRow tableRow = findViewById(R.id.trBody);
        View v = l.inflate(R.layout.activity_records_page, null);
        lvWorkout = v.findViewById(R.id.lvRecordPageWorkout);
        ibNewRecordPlus = v.findViewById(R.id.ibNewBodyRecordPlus);
        displayRecords();
        tableRow.addView(v);

        ////18/04/2023 -- set the onclick methods listeners --
        ibRecords.setOnClickListener(this);
        ibLesson.setOnClickListener(this);
        ibContact.setOnClickListener(this);
        ibNewRecordPlus.setOnClickListener(this);
        lvWorkout.setOnItemClickListener(this);

    }


    //18/04/2023 -- not sure if firebase code is here
    ////19/04/2023 *********** Shengxiong: there are not firebase code at here. the following part is about the onClick button function.

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
//            case R.id.ibNewMomentPlus:
//                i = new Intent(this,Add_New_Moment.class);
//                startActivity(i);
//                break;
            case R.id.ibNewContactPlus:
                action = "add";
                firstName = "";
                lastName = "";
                phoneNumber = "";
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
            case R.id.ibContact:
                tableRow.removeAllViews();
                v = l.inflate(R.layout.activity_contact_page,null);
                tableRow.addView(v);
                ibNewContactPlus = v.findViewById(R.id.ibNewContactPlus);
                lvContact = v.findViewById(R.id.lvContact);
                lvContact.setOnItemClickListener(this);
                ibNewContactPlus.setOnClickListener(this);
                displayContact();
                break;


        }
    }

    //18/04/2023 -- code to display records and its from firebase ----
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
    //18/04/2023 -- code to display lesson and tis from firebase --
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

    private void displayContact(){
        contactArrayList = new ArrayList<>();
        contactAdapter = new ContactAdapter(this, contactArrayList);

        contactReference = FirebaseDatabase.getInstance().getReference("users").child(username).child("contact");
        contactReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                contactArrayList.clear();
                for (DataSnapshot ds:snapshot.getChildren()){
                    contactIndex = Integer.valueOf(ds.getKey());
                    String firstName = ds.child("firstName").getValue(String.class);
                    String lastName = ds.child("lastName").getValue(String.class);
                    String phoneNumber = ds.child("phoneNumber").getValue(String.class);
                    Contact oneContact = new Contact(""+firstName,""+lastName,""+phoneNumber);
                    contactArrayList.add(oneContact);
                    contactAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        lvContact.setAdapter(contactAdapter);
    }

    // Add new contact popup window
    //18/04/2023 -- this code is what we learn in lesson 12 --
    public void createNewContactDialog(){
        dialogBuilder = new AlertDialog.Builder(this);

        final View contactPopupView = getLayoutInflater().inflate(R.layout.popup_new_contact,null);
        edNewContact_Firstname = (EditText) contactPopupView.findViewById(R.id.edNewContactFirstName);
        edNewContact_Lastname = (EditText) contactPopupView.findViewById(R.id.edNewContactLastName);
        edNewContactPhoneNumber = (EditText) contactPopupView.findViewById(R.id.edNewContactPhoneNumber);
        btnNewContactAdd = (Button) contactPopupView.findViewById(R.id.btnAddNewContact);
        btnNewContactCancel = (Button) contactPopupView.findViewById(R.id.btnNewContactCancel);
        btnNewContactDelete = contactPopupView.findViewById(R.id.btnNewContactDelete);

        edNewContact_Firstname.setText(firstName);
        edNewContact_Lastname.setText(lastName);
        edNewContactPhoneNumber.setText(phoneNumber);

        dialogBuilder.setView(contactPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        if (action.equals("add")) {
            btnNewContactAdd.setText("ADD");
            btnNewContactDelete.setVisibility(View.INVISIBLE);

        }else{
            btnNewContactAdd.setText("Modify");
        }

        btnNewContactAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                firstName = edNewContact_Firstname.getText().toString();
                lastName = edNewContact_Lastname.getText().toString();
                phoneNumber = edNewContactPhoneNumber.getText().toString();
                contactIndex++;
                Contact contact = new Contact(firstName,lastName,phoneNumber);
                contactReference = firebaseDatabase.getReference("users").child(username).child("contact").child(String.valueOf(contactIndex));
                contactReference.setValue(contact);

                dialog.dismiss();
            }
        });

        btnNewContactCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnNewContactDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactReference = firebaseDatabase.getReference("users").
                        child(username).child("contact").child(String.valueOf(contactIndex+1));
                contactReference.removeValue();
                dialog.dismiss();
            }
        });
    }

    //18/04/2023 -- onclock create code
    //19/04/2023 ****************** Shengxiong : Nope, this is onItemClick which means when you click the cell on the list view.......*************
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
        }else if (adapterView.getId() == R.id.lvContact){
            action = "modify";
            firstName = contactArrayList.get(i).getFirstName();
            lastName = contactArrayList.get(i).getLastName();
            phoneNumber = contactArrayList.get(i).getPhoneNumber();
            contactIndex = i;
            createNewContactDialog();
        }
    }
}