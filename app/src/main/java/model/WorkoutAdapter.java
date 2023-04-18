package model;
//imports for features?
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
//not sure what this imports
import com.example.prjweightrecords.R;

import java.util.ArrayList;
//parent class is Workout
public class WorkoutAdapter extends ArrayAdapter<Workout> {
   //fields
    private Context mContext;
    private int mResource;
    //constructor w/ complex params
    public WorkoutAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Workout> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }
  // this is a method from the parent?
    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        //inflator code
        view = LayoutInflater.from(mContext).inflate(mResource,parent,false);
        //get infor from the UI
        TextView tvWorkoutName, tvRepetition, tvSet;
        tvWorkoutName = view.findViewById(R.id.tvWorkoutNameCellDatabase);
        tvRepetition = view.findViewById(R.id.tvRepetitionCellDatabase);
        tvSet = view.findViewById(R.id.tvSetCellDatabase);
        //use the setters
        tvWorkoutName.setText(getItem(position).getWorkoutName());
        tvRepetition.setText(getItem(position).getRepetition());
        tvSet.setText(getItem(position).getSet());
        //return this whent he method is called
        return view;
    }
}
