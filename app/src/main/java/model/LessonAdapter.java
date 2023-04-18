package model;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.prjweightrecords.R;

import java.util.ArrayList;

public class LessonAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Lesson> lessonArrayList;

    Lesson lesson;

    public LessonAdapter(Context context, ArrayList<Lesson> lessonArrayList) {
        this.context = context;
        this.lessonArrayList = lessonArrayList;
    }

    @Override
    public int getCount() {
        return lessonArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return lessonArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View oneLesson;

        TextView tvTitle, tvContent,tvAuthor,tvDate;

        LayoutInflater inflater = LayoutInflater.from(context);

        oneLesson = inflater.inflate(R.layout.one_lesson,viewGroup,false);

        tvTitle = oneLesson.findViewById(R.id.tvOneLessonTitle);
        tvContent = oneLesson.findViewById(R.id.tvOneLessonContent);
        tvAuthor = oneLesson.findViewById(R.id.tvOneLessonAuthor);
        tvDate = oneLesson.findViewById(R.id.tvOneLessonDate);

        lesson = (Lesson) getItem(i);

        tvTitle.setText(lesson.getTitle());
        tvContent.setText(lesson.getContent());
        tvAuthor.setText(lesson.getAuthorName());
        tvDate.setText(lesson.getDate());

        return oneLesson;
    }
}


