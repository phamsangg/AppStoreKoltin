package com.example.duyhung.app_android.module;

import java.io.Serializable;

/**
 * Created by thetainguyen on 26/11/17.
 */

public class Customer implements Serializable {

    private String phoneNumber;
    private String name;
    private String address;
    private String cmt;

    public Customer() {
    }

    public Customer(String phoneNumber, String name, String address, String cmt) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.address = address;
        this.cmt = cmt;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
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

    public String getCmt() {
        return cmt;
    }

    public void setCmt(String cmt) {
        this.cmt = cmt;
    }
}
