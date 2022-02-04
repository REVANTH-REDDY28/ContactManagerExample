package com.sunny.contactmanagerexample.model;

import androidx.annotation.NonNull;

public class Contact{
    private int id;
    private String name;
    private String phoneNumber;

    public Contact(){

    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%o name:%s  phone number:%s ",id,name,phoneNumber);
    }

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Contact(int id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


}
