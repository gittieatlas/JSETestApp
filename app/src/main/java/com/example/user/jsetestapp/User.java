package com.example.user.jsetestapp;

import org.joda.time.LocalDate;

public class User {

    public static enum Gender {MALE, FEMALE}

    String firstName, lastName, email, password, ssn, defaultLocation;
    LocalDate dob;
    Gender gender;
    boolean isJseMember;


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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setGender(String gender) {
        try {
            this.gender = Gender.valueOf(gender);
       } catch (Exception ex) {
           this.gender = Gender.FEMALE;
        }
    }

    public boolean isJseMember() {
        return isJseMember;
    }

    public void setIsJseMember(boolean isJseMember) {
        this.isJseMember = isJseMember;
    }


}
