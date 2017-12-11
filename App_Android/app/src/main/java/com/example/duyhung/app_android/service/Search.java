package com.example.duyhung.app_android.service;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.duyhung.app_android.module.CustomerAutocomplete;
import com.example.duyhung.app_android.service.modules.Result;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.duyhung.app_android.Config.SUGGEST_CUSTOMER;
import static com.example.duyhung.app_android.Config.TIMEOUT_MILLISEC;

/**
 * Created by thetainguyen on 11/12/17.
 */

public class Search {

    private String url;

    public Search(String url) {
        this.url = url;
    }

    @SuppressLint("LongLogTag")
    public List<CustomerAutocomplete> getDataCustomer(int limit, String like) {


        url += SUGGEST_CUSTOMER + "?limit=" + limit;
        if (like != null && !like.equals("")) {
            try {
                like = URLEncoder.encode(like, "utf-8");
                url += "&like=" + like;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        try {

            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
            HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);

            HttpClient httpClient = new DefaultHttpClient(httpParams);

            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);

            HttpEntity httpEntity = httpResponse.getEntity();

            String result = EntityUtils.toString(httpEntity);

            Gson gson = new Gson();
            CustomerAutocomplete[] object = gson.fromJson(result, CustomerAutocomplete[].class);
            List<CustomerAutocomplete> myObjects = new ArrayList<>(Arrays.asList(object));
            return myObjects;
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
        return null;
        // Convert response to string using String Builder
    }
}
