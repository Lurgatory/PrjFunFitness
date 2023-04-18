package model;
//why this annotation?
import androidx.annotation.NonNull;

public class Body {
	
    //fields
    String weight;
     //default constructor
    public Body() {
    }
   //contructor with a param
    public Body(String weight) {
        this.weight = weight;
    }
    //getters/setters
    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
    //tostring method
    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
