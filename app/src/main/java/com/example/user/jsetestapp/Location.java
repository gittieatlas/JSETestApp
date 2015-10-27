package com.example.user.jsetestapp;

import java.io.Serializable;
import java.util.ArrayList;

public class Location implements Serializable {

    //long id;
    String name, address, phone;
    ArrayList<Hours> hoursArrayList;
    ArrayList<Alerts> alertsArrayList;

    public Location() {

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

    public ArrayList<Hours> getHoursArrayList() {
        return hoursArrayList;
    }

    public void setHoursArrayList(ArrayList<Hours> hoursArrayList) {
        this.hoursArrayList = hoursArrayList;
    }

    public ArrayList<Alerts> getAlertsArrayList() {
        return alertsArrayList;
    }

    public void setAlertsArrayList(ArrayList<Alerts> alertsArrayList) {
        this.alertsArrayList = alertsArrayList;
    }
}
