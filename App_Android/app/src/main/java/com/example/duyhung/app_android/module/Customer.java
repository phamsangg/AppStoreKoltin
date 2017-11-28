package com.example.duyhung.app_android.module;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by thetainguyen on 26/11/17.
 */

public class Customer implements Serializable {

    private String phone_number;
    private String name;
    private String address;
    private String cmt;
    private Date date;

    public Customer() {
    }

    public Customer(String phoneNumber, String name, String address, String cmt,Date date) {
        this.phone_number = phoneNumber;
        this.name = name;
        this.address = address;
        this.cmt = cmt;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
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

    @Override
    public String toString() {
        return "customer{" +
                "phone_number='" + phone_number + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", cmt='" + cmt + '\'' +
                '}';
    }
}
