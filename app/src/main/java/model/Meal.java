package model;
//why this annotation?
import androidx.annotation.NonNull;

public class Meal {
    //fields
    String breakfast, lunch, dinner;
    //default contructor
    public Meal() {
    }
   //constructor w/ 3 params
    public Meal(String breakfast, String lunch, String dinner) {
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }
    //getsers and setters
    public String getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public String getLunch() {
        return lunch;
    }

    public void setLunch(String lunch) {
        this.lunch = lunch;
    }

    public String getDinner() {
        return dinner;
    }

    public void setDinner(String dinner) {
        this.dinner = dinner;
    }
    //tostring method
    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
