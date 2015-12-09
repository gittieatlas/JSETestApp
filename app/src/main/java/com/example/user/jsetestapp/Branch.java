package com.example.user.jsetestapp;


import java.io.Serializable;

public class Branch implements Serializable{
    // class members
    int id;
    String name;

    // constructor
    public Branch(){

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

}

