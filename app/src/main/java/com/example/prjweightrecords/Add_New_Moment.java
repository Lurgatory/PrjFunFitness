package com.example.prjweightrecords;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Add_New_Moment extends AppCompatActivity implements View.OnClickListener {

    ImageButton ibClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_moment);
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
                break;
        }
    }
}