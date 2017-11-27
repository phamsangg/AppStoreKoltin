package com.example.duyhung.app_android.module;

import java.util.Date;

/**
 * Created by thetainguyen on 26/11/17.
 */

public class Transfer {

    private Date date_transfer;
    private int money;
    private String item;
    private int customer_phone_number;

    public Transfer() {
    }

    public Date getDate_transfer() {
        return date_transfer;
    }

    public void setDate_transfer(Date date_transfer) {
        this.date_transfer = date_transfer;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getCustomer_phone_number() {
        return customer_phone_number;
    }

    public void setCustomer_phone_number(int customer_phone_number) {
        this.customer_phone_number = customer_phone_number;
    }
}
