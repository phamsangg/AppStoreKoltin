package com.example.duyhung.app_android.service;

import android.app.Activity;
import android.os.AsyncTask;

import com.example.duyhung.app_android.callback.CallBackAction;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thetainguyen on 27/11/17.
 */

public class AsyncSendData extends AsyncTask<Void, Void, Void> {

    private Activity activity;
    private String url;
    private CallBackAction callBackAction;

    public AsyncSendData(Activity activity, String url, CallBackAction callBackAction) {
        this.activity = activity;
        this.url = url;
        this.callBackAction = callBackAction;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        HttpClient httpclient = new DefaultHttpClient();

        HttpGet httpGet = new HttpGet(url);

        try {
            HttpResponse response = httpclient.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            httpEntity.getContent();
        } catch (ClientProtocolException e) {

        } catch (IOException e) {

        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        callBackAction.result(true);
    }
}
