package com.example.duyhung.app_android.service;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.example.duyhung.app_android.callback.CallBackAction;
import com.example.duyhung.app_android.module.Result;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import static com.example.duyhung.app_android.Config.TIMEOUT_MILLISEC;

/**
 * Created by thetainguyen on 26/11/17.
 */

public class AsyncGetData extends AsyncTask<String, String, Void> {


    Activity activity;
    Result status;
    String url;
    InputStream inputStream = null;
    CallBackAction callBackAction;

    public AsyncGetData(Activity activity, CallBackAction callBackAction, String url) {
        this.activity = activity;
        this.url = url;
        this.callBackAction = callBackAction;
    }

    @SuppressLint("LongLogTag")
    @Override
    protected Void doInBackground(String... strings) {
        status = new Result();
        String result = "";
        int key = 0;
        try {

            String postMessage = "{}"; //HERE_YOUR_POST_STRING.
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
            HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);

            HttpClient httpClient = new DefaultHttpClient(httpParams);

            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            key = httpResponse.getStatusLine().getStatusCode();
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
            status.setStatus(key);
            status.setResult(result);

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
        callBackAction.excute(status);
    }
}
