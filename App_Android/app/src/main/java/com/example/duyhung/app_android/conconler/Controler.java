package com.example.duyhung.app_android.conconler;

import android.app.Activity;

import com.example.duyhung.app_android.callback.CallBackGetListCustomer;
import com.example.duyhung.app_android.module.Customer;
import com.example.duyhung.app_android.service.AsyncGetData;
import com.example.duyhung.app_android.service.AsyncSendData;

import java.util.ArrayList;
import java.util.List;

import static com.example.duyhung.app_android.Config.GET_CUSTOMER;
import static com.example.duyhung.app_android.Config.GET_TRANSFER;


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
    
    public void getListCustomer(int limit, int offset, CallBackGetListCustomer callBackGetListCustomer) {

        url += GET_CUSTOMER + "?limit=" + limit + "&offset=" + offset;
        new AsyncGetData(activity, callBackGetListCustomer, url).execute();
        return;
    }

    public void getListTransfer(int limit, int offset, String phoneNumber, CallBackGetListCustomer callBackGetListCustomer) {
        url += GET_TRANSFER + "?phone_number=" + phoneNumber + "&limit=" + limit + "&offset=" + offset;
        new AsyncGetData(activity, callBackGetListCustomer, url).execute();
        return;
    }

}
