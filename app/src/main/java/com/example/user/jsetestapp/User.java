package com.example.user.jsetestapp;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    // class members
    public static enum Gender {MALE , FEMALE}

    String firstName, lastName, email, password, ssn, defaultLocation, jseStudentId;
    LocalDate dob;
    Gender gender;
    int locationId, id;

    // constructor
    public User() {
    }

    /**
     * Retrieve firstName
     * @return String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set firstName
     * @param firstName - variable of type String
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retrieve lastName
     * @return String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set lastName
     * @param lastName - variable of type String
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retrieve email
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set email
     * @param email - variable of type String.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieve password
     * @return String data type
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set password
     * @param password - variable of type String.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrieve ssn.
     * @return String
     */
    public String getSsn() {
        return ssn;
    }

    /**
     * Set ssn.
     * @param ssn - variable of type String.
     */
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    /**
     * Retrieve defaultLocation
     * @return String
     */
    public String getDefaultLocation() {
        return defaultLocation;
    }

    /**
     * Set defaultLocation
     * @param defaultLocation - variable of type String
     */
    public void setDefaultLocation(String defaultLocation) {
        this.defaultLocation = defaultLocation;
    }

    /**
     * Retrieve dob
     * @return LocalDate
     */
    public LocalDate getDob() {
        return dob;
    }

    /**
     * Set dob
     * @param dob - variable of type LocalDate
     */
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    /**
     * Set dob as LocalDate
     * @param dob - variable of type String
     */
    public void setDob(String dob) {
        // initialize and set dtf pattern
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
        // parse dob to LocalDate (in dtf format) and assign to dob
        this.dob = dtf.parseLocalDate(dob);
    }

    /**
     * Set dob
     * @param year, months, day - variables of type String
     */
    public void setDob(String year, String month, String day) {
        // initialize and set dtf pattern
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
        // parse year plus "-", month plus "-", day to LocalDate (in dtf format) and assign to dob
        this.dob = dtf.parseLocalDate(year + "-" + month + "-" + day);
    }

    /**
     * Retrieve gender
     * @return Gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Retrieve gender
     * @return int
     */
    public int getGender(User user) {
        return Gender.valueOf(user.getGender().name()).ordinal();
    }

    /**
     * Set gender.
     * @param gender - variable of type Gender.
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Set gender
     * @param gender - variable of type String
     */
    public void setGender(String gender) {
        try {
            // assign value of gender (in uppercase) to gender
            this.gender = Gender.valueOf(gender.toUpperCase());
        } catch (Exception ex) {
            // assign value of FEMALE to gender
            this.gender = Gender.FEMALE;
        }
    }

    /**
     * Set gender
     * @param gender - variable of type int
     */
    public void setGender(int gender) {
        try {
            // assign value of int - 1 to gender
            this.gender = Gender.values()[gender-1];
        } catch (Exception ex) {
            // assign value of FEMALE as gender
            this.gender = Gender.FEMALE;
        }
    }

//    /**
//     * Retrieve isJseMember
//     * @return boolean
//     */
//    public boolean isJseMember() {
//        return isJseMember;
//    }

//    /**
//     * Set isJseMember
//     * @param isJseMember - variable of type boolean
//     */
//    public void setIsJseMember(boolean isJseMember) {
//        this.isJseMember = isJseMember;
//    }
//
//    /**
//     * Set the value of isJseMember
//     * @param isJseMember - variable of type String
//     */
//    public void setIsJseMember(String isJseMember) {
//        // if isJseMember is not null
//        if (isJseMember != null) {
//            // set isJseMember to true
//            this.isJseMember = true;
//        }
//    }

    /**
     * Retrieve locationId
     * @return int
     */
    public int getLocationId() {
        return locationId;
    }

    /**
     * Set locationId
     * @param locationId - variable of type int
     */
    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    /**
     * Set locationId
     * @param locationId - variable of type String
     */
    public void setLocationId(String locationId) {
        // if locationId is not null
        if (locationId != null)
            // assign locationId to locationId
            this.locationId = Integer.parseInt(locationId);
    }

    /**
     * Set locationId.
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
     * Retrieve id
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * Set id
     * @param id - variable of type int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieve jseStudentId
     * @return String
     */
    public String getJseStudentId() {
        return jseStudentId;
    }

    /**
     * Set jseStudentId
     * @param jseStudentId - variable of type String
     */
    public void setJseStudentId(String jseStudentId) {
        // if jseStudentId is not equal to null
        if (!jseStudentId.equals("null"))
            // assign jseStudentId to jseStudentId
            this.jseStudentId = jseStudentId;
    }

}