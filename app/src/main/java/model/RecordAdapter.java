package model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.prjweightrecords.R;

import java.util.ArrayList;

public class RecordAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Record> recordArrayList;

    Record record;

    public RecordAdapter(Context context, ArrayList<Record> recordArrayList) {
        this.context = context;
        this.recordArrayList = recordArrayList;
    }

    @Override
    public int getCount() {
        return recordArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return recordArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View oneWork;

        TextView tvBreakfast,tvLunch,tvDinner, tvWeight, tvDate;

        LayoutInflater inflater = LayoutInflater.from(context);

        oneWork = inflater.inflate(R.layout.one_workout,viewGroup,false);

        tvBreakfast = oneWork.findViewById(R.id.tvOneWorkoutBreakfastDatabase);
        tvLunch = oneWork.findViewById(R.id.tvOneWorkoutLunchDatabase);
        tvDinner = oneWork.findViewById(R.id.tvOneWorkoutDinnerDatabase);
        tvWeight = oneWork.findViewById(R.id.tvOneWorkoutWeightDatabase);
        tvDate = oneWork.findViewById(R.id.tvOneWorkoutDate);

        record = (Record) getItem(i);

        tvDate.setText(record.getDate());
        tvBreakfast.setText(record.meal.getBreakfast());
        tvLunch.setText(record.meal.getLunch());
        tvDinner.setText(record.meal.getDinner());
        tvWeight.setText(record.body.getWeight());

        return oneWork;
    }
}


