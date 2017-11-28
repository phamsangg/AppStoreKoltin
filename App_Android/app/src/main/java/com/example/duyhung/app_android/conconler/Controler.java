package com.example.duyhung.app_android.conconler;

import android.app.Activity;

import com.example.duyhung.app_android.callback.CallBackAction;
import com.example.duyhung.app_android.module.Customer;
import com.example.duyhung.app_android.module.Transfer;
import com.example.duyhung.app_android.service.AsyncGetData;
import com.example.duyhung.app_android.service.AsyncSendData;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.example.duyhung.app_android.Config.GET_CUSTOMER;
import static com.example.duyhung.app_android.Config.GET_TRANSFER;
import static com.example.duyhung.app_android.Config.INSERT_CUSTOMER;
import static com.example.duyhung.app_android.Config.INSERT_TRANSFER;


/**
 * Created by thetainguyen on 26/11/17.
 */

public class Controler {

    Activity activity;
    String url;

    public Controler(Activity activity, String url) {
        this.activity = activity;
        this.url = url;
    }

    public void getListCustomer(int limit, int offset, CallBackAction callBackAction) {

        url += GET_CUSTOMER + "?limit=" + limit + "&offset=" + offset;
        new AsyncGetData(activity, callBackAction, url).execute();
        return;
    }

    public void getListTransfer(int limit, int offset, String phoneNumber, CallBackAction callBackAction) {
        url += GET_TRANSFER + "?phone_number=" + phoneNumber + "&limit=" + limit + "&offset=" + offset;
        new AsyncGetData(activity, callBackAction, url).execute();
        return;
    }

    public void addCustomer(CallBackAction callBackAction, Customer customer) {

        try {
            String name = URLEncoder.encode(customer.getName(), "utf-8");
            String address = URLEncoder.encode(customer.getAddress(), "utf-8");
            String phone = URLEncoder.encode(customer.getPhone_number(), "utf-8");
            String cmt = URLEncoder.encode(customer.getCmt().toString(), "utf-8");
            String date = URLEncoder.encode(customer.getDate().toString(), "utf-8");
            url += INSERT_CUSTOMER + "?phone_number=" + phone + "&name=" + name + "&address=" + address + "&cmt=" + cmt + "&date=" + date;
            new AsyncSendData(activity, url, callBackAction).execute();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public void addTrasfer(CallBackAction callBackAction, Transfer transfer, String phoneId) {

        try {
            String item = URLEncoder.encode(transfer.getItem(), "utf-8");
            String date_tranfer = URLEncoder.encode(transfer.getDate_transfer().toString(), "utf-8");
            String phone = URLEncoder.encode(phoneId.toString(), "utf-8");
            String money = URLEncoder.encode(String.valueOf(transfer.getMoney()), "utf-8");
            url += INSERT_TRANSFER + "?customer_phone_number=" + phone + "&item=" + item + "&money=" + money + "&date_tranfer=" + date_tranfer;
            new AsyncSendData(activity, url, callBackAction).execute();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

}
