package com.example.duyhung.app_android.module;

import java.util.Date;

/**
 * Created by thetainguyen on 26/11/17.
 */

public class Transfer {

    private Date date;
    private int money;
    private String nameProduction;
    private int phoneNumber;

    public Transfer() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getNameProduction() {
        return nameProduction;
    }

    public void setNameProduction(String nameProductino) {
        this.nameProduction = nameProductino;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
