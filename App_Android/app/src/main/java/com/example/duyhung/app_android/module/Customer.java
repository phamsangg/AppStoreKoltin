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
    private Long date;
    private Long lateDateItem;


    public Customer() {
    }

    public Customer(String phoneNumber, String name, String address, String cmt, Long date, Long lateDateItem) {
        this.phone_number = phoneNumber;
        this.name = name;
        this.address = address;
        this.cmt = cmt;
        this.date = date;
        this.lateDateItem = lateDateItem;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
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

    public Long getLateDateItem() {
        return lateDateItem;
    }

    public void setLateDateItem(Long lateDateItem) {
        this.lateDateItem = lateDateItem;
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
