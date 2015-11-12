package com.example.user.jsetestapp;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    public static enum Gender {MALE, FEMALE}

    String firstName, lastName, email, password, ssn, defaultLocation;
    LocalDate dob;
    Gender gender;
    boolean isJseMember;
    int locationId;

    public User() {

    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getDefaultLocation() {
        return defaultLocation;
    }

    public void setDefaultLocation(String defaultLocation) {
        this.defaultLocation = defaultLocation;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }


    public void setDob(String year, String month, String day) {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
        this.dob = dtf.parseLocalDate(year + "-" + month + "-" + day);
    }

    public Gender getGender() {
        return gender;
    }
    public int getGender(User user) {
        return Gender.valueOf(user.getGender().name()).ordinal();
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setGender(String gender) {
        try {
            this.gender = Gender.valueOf(gender.toUpperCase());
        } catch (Exception ex) {
            this.gender = Gender.FEMALE;
        }
    }


//    public String getGender(Gender gender) {
//        String stringGender;
//        try {
//            stringGender = gender.toString();
//        } catch (Exception ex) {
//            stringGender = Gender.values()[1].toString();
//        }
//        return stringGender;
//    }

    public boolean isJseMember() {
        return isJseMember;
    }

    public void setIsJseMember(boolean isJseMember) {
        this.isJseMember = isJseMember;
    }

//    public int getLocationId(ArrayList<Location> locationArrayList, User user) {
//        int locationId = 0;
//        for (Location location : locationArrayList){
//            if (user.getDefaultLocation().equals(location.getName())){
//                locationId = location.getId() ;
//            }
//        }
//        return locationId;
//    }


    public int getLocationId() {
        return locationId;
    }
//    public String getLocationIdString() {
//        return Integer.toString(locationId);
//    }


    public void setLocationId(int locationId) {

        this.locationId = locationId;
    }


    public void setLocationId(ArrayList<Location> locationArrayList, User user) {
        for (Location location : locationArrayList){
            if (user.getDefaultLocation().equals(location.getName())){
                this.locationId = location.getId() ;
            }
        }
        //this.locationId = locationId;
    }


}
