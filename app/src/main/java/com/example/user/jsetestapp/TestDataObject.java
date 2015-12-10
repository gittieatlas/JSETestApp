package com.example.user.jsetestapp;

/*
 * TestDataObject class holds data for displaying in RecyclerView
 * */
public class TestDataObject {
    private String location, testDay, testTime, testDate, testDeadlineTitle, testDeadlineDetails;

    TestDataObject(String text1, String text2, String text3, String text4,
                   String text5, String text6) {
        location = text1;
        testDay = text2;
        testTime = text3;
        testDate = text4;
        testDeadlineTitle = text5;
        testDeadlineDetails = text6;
    }

    /**
     * Retrieve location
     * @return String
     */
    public String getLocation() {

        return location;
    }

    /**
     * Retrieve testDay
     * @return String
     */
    public String getTestDay() {

        return testDay;
    }

    /**
     * Retrieve testTime
     * @return String
     */
    public String getTestTime() {

        return testTime;
    }

    /**
     * Retrieve testDate
     * @return String
     */
   public String getTestDate() {

        return testDate;
    }

    /**
     * Retrieve testDeadlineTitle
     * @return String
     */
    public String getTestDeadlineTitle() {

        return testDeadlineTitle;
    }

    /**
     * Retrieve testDeadlineDetails
     * @return String
     */
    public String getTestDeadlineDetails() {

        return testDeadlineDetails;
    }

}
