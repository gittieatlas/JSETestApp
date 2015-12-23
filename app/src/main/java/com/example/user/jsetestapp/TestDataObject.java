package com.example.user.jsetestapp;

/*
 * TestDataObject class holds data for displaying in RecyclerView
 * */
public class TestDataObject {
    private String location, testDay, testTime, testDate, testDeadlineTitle, testDeadlineDay,
    testDeadlineDate, testDeadlineTime;

    TestDataObject(String text1, String text2, String text3, String text4,
                   String text5, String text6, String text7, String text8) {
        location = text1;
        testDay = text2;
        testTime = text3;
        testDate = text4;
        testDeadlineTitle = text5;
        testDeadlineDay = text6;
        testDeadlineTime = text7;
        testDeadlineDate = text8;


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
     * Retrieve testDeadlineDay
     * @return String
     */
    public String getTestDeadlineDay() {

        return testDeadlineDay;
    }

    public String getTestDeadlineDate() {
        return testDeadlineDate;
    }

    public void setTestDeadlineDate(String testDeadlineDate) {
        this.testDeadlineDate = testDeadlineDate;
    }

    public String getTestDeadlineTime() {
        return testDeadlineTime;
    }

    public void setTestDeadlineTime(String testDeadlineTime) {
        this.testDeadlineTime = testDeadlineTime;
    }
}
