package model;

import androidx.annotation.NonNull;

public class User {
	//robertoComment--fields
    String name, email, username, password;

//    Record record;
  //robertoComment--default construtor
    public User() {
    }

//    public User(Record record) {
//        this.record = record;
//    }


//robertoComment--constrictoer w/ 4 params
    public User(String name, String email, String username, String password) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }
//robertoComment--geters/setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public Record getRecord() {
//        return record;
//    }
//
//    public void setRecord(Record record) {
//        this.record = record;
//    }
//robertoComment--tostring
    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
