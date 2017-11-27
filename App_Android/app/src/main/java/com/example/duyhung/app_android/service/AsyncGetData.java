package com.example.duyhung.app_android.service;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.example.duyhung.app_android.callback.CallBackGetListCustomer;
import com.example.duyhung.app_android.module.Customer;
import com.example.duyhung.app_android.view.MainActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static com.example.duyhung.app_android.Config.GET_CUSTOMER;

/**
 * Created by thetainguyen on 26/11/17.
 */

public class AsyncGetData extends AsyncTask<String, String, Void> {


    Activity activity;

    String url;
    InputStream inputStream = null;
    String result = "";
    CallBackGetListCustomer callBackGetListCustomer;

    public AsyncGetData(Activity activity, CallBackGetListCustomer callBackGetListCustomer, String url) {
        this.activity = activity;
        this.url = url;
        this.callBackGetListCustomer = callBackGetListCustomer;
    }

    @SuppressLint("LongLogTag")
    @Override
    protected Void doInBackground(String... strings) {

        try {

            HttpClient httpClient = new DefaultHttpClient();

            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();

            // Read content & Log
            inputStream = httpEntity.getContent();
        } catch (UnsupportedEncodingException e1) {
            Log.e("UnsupportedEncodingException", e1.toString());
            e1.printStackTrace();
        } catch (ClientProtocolException e2) {
            Log.e("ClientProtocolException", e2.toString());
            e2.printStackTrace();
        } catch (IllegalStateException e3) {
            Log.e("IllegalStateException", e3.toString());
            e3.printStackTrace();
        } catch (IOException e4) {
            Log.e("IOException", e4.toString());
            e4.printStackTrace();
        }
        // Convert response to string using String Builder
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
            StringBuilder sBuilder = new StringBuilder();

            String line = null;
            while ((line = bReader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }

            inputStream.close();
            result = sBuilder.toString();

        } catch (Exception e) {
            Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
        }
        return null;
    }

    @SuppressLint("LongLogTag")

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @SuppressLint("LongLogTag")

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        callBackGetListCustomer.excute(result);
    }
}
