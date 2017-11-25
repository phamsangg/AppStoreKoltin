package com.example.duyhung.app_android.module;

/**
 * Created by thetainguyen on 26/11/17.
 */

public class Customer {

    private int phoneNumber;
    private String name;
    private String address;
    private int cmt;

    public Customer() {
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public int getCmt() {
        return cmt;
    }

    public void setCmt(int cmt) {
        this.cmt = cmt;
    }
}
