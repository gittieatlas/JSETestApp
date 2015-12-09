package com.example.user.jsetestapp;

public class HoursDataObject {
    // Declare variables
    private String libraryDay,libraryOpeningTime, libraryClosingTime;


    // Constructor
    HoursDataObject(String text1, String text2, String text3) {
        libraryDay = text1;
        libraryOpeningTime = text2;
        libraryClosingTime = text3;

    }

    /**
     * Retrieve libraryDay defined in the constructor
     * @return String
     */
    public String getLibraryDay() {

        return libraryDay;
    }

    /**
     * Retrieve libraryOpeningTime defined in the constructor
     * @return String
     */
    public String getLibraryOpeningTime() {

        return libraryOpeningTime;
    }

    /**
     * Retrieve libraryClosingTime defined in the constructor
     * @return String
     */
    public String getLibraryClosingTime() {

        return libraryClosingTime;
    }

}
