package com.example.user.jsetestapp;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    public static enum Gender {MALE , FEMALE}

    String firstName, lastName, email, password, ssn, defaultLocation, jseStudentId;
    LocalDate dob;
    Gender gender;
    boolean isJseMember;
    int locationId, id;

    public User() {
    }

    /**
     * Retrieve the value of firstName
     * @return String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the value of firstName
     * @param firstName - variable of type String
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retrieve the value of lastName
     * @return String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the value of lastName
     * @param lastName - variable of type String
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retrieve the value of email
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the value of email
     * @param email - variable of type String.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieve the value of password
     * @return String data type
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the value of password
     * @param password - variable of type String.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrieve the value of ssn.
     * @return String
     */
    public String getSsn() {
        return ssn;
    }

    /**
     * Set the value of ssn.
     * @param ssn - variable of type String.
     */
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    /**
     * Retrieve the value of defaultLocation.
     * @return String data type.
     */
    public String getDefaultLocation() {
        return defaultLocation;
    }

    /**
     * Set the value of defaultLocation.
     * @param defaultLocation - variable of type String.
     */
    public void setDefaultLocation(String defaultLocation) {
        this.defaultLocation = defaultLocation;
    }

    /**
     * Retrieve the value of dob
     * @return LocalDate
     */
    public LocalDate getDob() {
        return dob;
    }

    /**
     * Set the value of dob
     * @param dob - variable of type LocalDate
     */
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    /**
     * Set the value of dob
     * @param dob - variable of type String
     */
    public void setDob(String dob) {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
        this.dob = dtf.parseLocalDate(dob);
    }

    /**
     * Set the value of dob
     * @param year, months, day - variables of type String
     */
    public void setDob(String year, String month, String day) {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
        this.dob = dtf.parseLocalDate(year + "-" + month + "-" + day);
    }

    /**
     * Retrieve the value of gender
     * @return Gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Retrieve the value of gender
     * @return int
     */
    public int getGender(User user) {
        return Gender.valueOf(user.getGender().name()).ordinal();
    }

    /**
     * Set the value of gender.
     * @param gender - variable of type Gender.
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Set the value of gender
     * @param gender - variable of type String
     */
    public void setGender(String gender) {
        try {
            this.gender = Gender.valueOf(gender.toUpperCase());
        } catch (Exception ex) {
            this.gender = Gender.FEMALE;
        }
    }

    /**
     * Set the value of gender
     * @param gender - variable of type int
     */
    public void setGender(int gender) {
        try {
            this.gender = Gender.values()[gender-1];
        } catch (Exception ex) {
            this.gender = Gender.FEMALE;
        }
    }

    /**
     * Retrieve if isJseMember
     * @return boolean
     */
    public boolean isJseMember() {
        return isJseMember;
    }

    /**
     * Set the value of isJseMember
     * @param isJseMember - variable of type boolean
     */
    public void setIsJseMember(boolean isJseMember) {
        this.isJseMember = isJseMember;
    }

    /**
     * Set the value of isJseMember
     * @param isJseMember - variable of type String
     */
    public void setIsJseMember(String isJseMember) {
        if (isJseMember != null) {
            this.isJseMember = true;
        }
    }

    /**
     * Retrieve the value of locationId
     * @return int
     */
    public int getLocationId() {
        return locationId;
    }

    /**
     * Set the value of locationId
     * @param locationId - variable of type int
     */
    public void setLocationId(int locationId) {

        this.locationId = locationId;
    }

    /**
     * Set the value of locationId
     * @param locationId - variable of type String
     */
    public void setLocationId(String locationId) {
        if (locationId != null)
            this.locationId = Integer.parseInt(locationId);
    }

    /**
     * Set the value of locationId.
     * @param locationArrayList - variable of type Location.
     * @param user - variable of type User.
     */
    public void setLocationId(ArrayList<Location> locationArrayList, User user) {
        // loop through each location in locationArrayList
        for (Location location : locationArrayList) {
            // if user's defaultLocation is equal to current location
            if (user.getDefaultLocation().equals(location.getName())) {
                // assign id of location to locationId
                this.locationId = location.getId();
            }
        }
    }

    /**
     * Retrieve the value of id
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * Set the value of id
     * @param id - variable of type int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieve the value of jseStudentId
     * @return String
     */
    public String getJseStudentId() {
        return jseStudentId;
    }

    /**
     * Set the value of jseStudentId
     * @param jseStudentId - variable of type String
     */
    public void setJseStudentId(String jseStudentId) {
        if (!jseStudentId.equals("null"))
            this.jseStudentId = jseStudentId;
    }

}