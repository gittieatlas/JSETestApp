package com.example.user.jsetestapp;

import java.io.Serializable;
import java.util.ArrayList;

public class Location implements Serializable {

    int id, branchId;
    String name, address, phone;
    ArrayList<Hour> hourArrayList;
    ArrayList<Alert> alertArrayList;

    public Location() {

    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<Hour> getHourArrayList() {
        return hourArrayList;
    }

    public void setHourArrayList(ArrayList<Hour> hourArrayList) {
        this.hourArrayList = hourArrayList;
    }

    public ArrayList<Alert> getAlertArrayList() {
        return alertArrayList;
    }

    public void setAlertArrayList(ArrayList<Alert> alertArrayList) {
        this.alertArrayList = alertArrayList;
    }
}
