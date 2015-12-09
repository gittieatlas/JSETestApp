package com.example.user.jsetestapp;

import java.io.Serializable;
import java.util.ArrayList;

public class Location implements Serializable {
    // class members
    int id, branchId;
    String name, address, phone;
    ArrayList<Hour> hourArrayList;
    ArrayList<Alert> alertArrayList;

    // constructor
    public Location() {

    }

    /**
     * Retrieve branchId
     * @return int
     */
    public int getBranchId() {
        return branchId;
    }

    /**
     * Set branchId
     * @param branchId - variable of type int
     */
    public void setBranchId(int branchId) {
        this.branchId = branchId;
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
     * Retrieve name
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     * @param name - variable of type String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieve address
     * @return String
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set address
     * @param address - variable of type String
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Retrieve phone
     * @return String
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Set phone
     * @param phone - variable of type String
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Retrieve hourArrayList
     * @return ArrayList<Hour>
     */
    public ArrayList<Hour> getHourArrayList() {
        return hourArrayList;
    }

    /**
     * Set hourArrayList
     * @param hourArrayList - ArrayList of type Hour
     */
    public void setHourArrayList(ArrayList<Hour> hourArrayList) {
        this.hourArrayList = hourArrayList;
    }

    /**
     * Retrieve alertsArrayList
     * @return ArrayList<Alert>
     */
    public ArrayList<Alert> getAlertArrayList() {
        return alertArrayList;
    }

    /**
     * Set alertArrayList
     * @param alertArrayList - ArrayList of type Alert
     */
    public void setAlertArrayList(ArrayList<Alert> alertArrayList) {
        this.alertArrayList = alertArrayList;
    }
}
