package com.example.duyhung.app_android.conconler;

import android.app.Activity;

import com.example.duyhung.app_android.callback.CallBackAction;
import com.example.duyhung.app_android.module.Customer;
import com.example.duyhung.app_android.module.Transfer;
import com.example.duyhung.app_android.service.AsyncGetData;
import com.example.duyhung.app_android.service.AsyncGetListName;
import com.example.duyhung.app_android.service.AsyncSendData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import static com.example.duyhung.app_android.Config.GET_CUSTOMER;
import static com.example.duyhung.app_android.Config.GET_CUSTOMER_LIST_NAME;
import static com.example.duyhung.app_android.Config.GET_CUSTOMER_PHONE;
import static com.example.duyhung.app_android.Config.GET_SUM;
import static com.example.duyhung.app_android.Config.GET_TRANSFER;
import static com.example.duyhung.app_android.Config.INSERT_CUSTOMER;
import static com.example.duyhung.app_android.Config.INSERT_TRANSFER;
import static com.example.duyhung.app_android.Config.UPDATE_CUSTOMER;


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

    public void getListCustomer(int limit, int offset, String like, CallBackAction callBackAction) {


        url += GET_CUSTOMER + "?limit=" + limit + "&offset=" + offset;
        if (like != null && !like.equals("")) {
            try {
                like = URLEncoder.encode(like, "utf-8");
                url += "&like=" + like;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }


        new AsyncGetData(activity, callBackAction, url).execute();
        return;
    }

    public void getListTransfer(int limit, int offset, String phoneNumber, CallBackAction callBackAction) {
        url += GET_TRANSFER + "?phone_number=" + phoneNumber + "&limit=" + limit + "&offset=" + offset;
        new AsyncGetData(activity, callBackAction, url).execute();
        return;
    }

    public void getSumMoney(String phoneNumber, CallBackAction callBackAction) {
        url += GET_SUM + "?phone_number=" + phoneNumber;
        new AsyncGetData(activity, callBackAction, url).execute();
        return;
    }

    public void addCustomer(CallBackAction callBackAction, Customer customer) {

        try {
            String phone = URLEncoder.encode(customer.getPhone_number(), "utf-8");
            url += INSERT_CUSTOMER + "?phone_number=" + phone;
            if (customer.getName() != null) {
                String name = URLEncoder.encode(customer.getName(), "utf-8");
                url += "&name=" + name;
            }

            if (customer.getAddress() != null) {
                String address = URLEncoder.encode(customer.getAddress(), "utf-8");
                url += "&address=" + address;
            }

            if (customer.getCmt() != null) {
                String cmt = URLEncoder.encode(customer.getCmt().toString(), "utf-8");
                url += "&cmt=" + cmt;
            }

            if (customer.getDate() != null) {
                String date = URLEncoder.encode(customer.getDate().toString(), "utf-8");
                url += "&date=" + date;
            }

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

    public void getCutomer(CallBackAction callBackAction, String phoneID) {
        try {

            String phone = URLEncoder.encode(phoneID.toString(), "utf-8");
            url += GET_CUSTOMER_PHONE + "?phone_number=" + phone;
            new AsyncSendData(activity, url, callBackAction).execute();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void getListName(CallBackAction callBackAction, List<String> phoneID) {


        try {
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            for (String s : phoneID) {
                JSONObject data = new JSONObject();
                data.put("phone", s);
                jsonArray.put(data);
            }
            jsonObject.put("contact", jsonArray);

            String listPhone = URLEncoder.encode(jsonObject.toString(), "utf-8");
            url += GET_CUSTOMER_LIST_NAME + "?listphone=" + listPhone;
            new AsyncGetListName(url, callBackAction).execute();

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return;
    }

    public void updateCustomer(CallBackAction callBackAction, Customer customer) {
        try {
            String phone = URLEncoder.encode(customer.getPhone_number(), "utf-8");
            url += UPDATE_CUSTOMER + "?phone_number=" + phone;
            if (customer.getName() != null) {
                String name = URLEncoder.encode(customer.getName(), "utf-8");
                url += "&name=" + name;
            }

            if (customer.getAddress() != null) {
                String address = URLEncoder.encode(customer.getAddress(), "utf-8");
                url += "&address=" + address;
            }

            if (customer.getCmt() != null) {
                String cmt = URLEncoder.encode(customer.getCmt().toString(), "utf-8");
                url += "&cmt=" + cmt;
            }

            new AsyncSendData(activity, url, callBackAction).execute();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
