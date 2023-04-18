package com.example.prjweightrecords;
//basic imports
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

//OnClickListener declare implements w/ the actual method below
public class Add_New_Moment extends AppCompatActivity implements View.OnClickListener {
//fields
    ImageButton ibClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_moment);
		//declare this method here
        initialize();
    }
//create this method here
    private void initialize() {
		//find the ID and set the listner
        ibClose = findViewById(R.id.ibClose);
        ibClose.setOnClickListener(this);
    }
//OnClickListener method created here--on the click run the method--
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