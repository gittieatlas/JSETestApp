package com.example.user.jsetestapp;


import java.io.Serializable;

public class Branch implements Serializable{

    int id;
    String name;

    public Branch(){


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


}

