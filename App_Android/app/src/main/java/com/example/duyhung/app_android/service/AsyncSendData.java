package com.example.duyhung.app_android.service;

import android.app.Activity;
import android.os.AsyncTask;

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
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import static com.example.duyhung.app_android.Config.TIMEOUT_MILLISEC;

/**
 * Created by thetainguyen on 27/11/17.
 */

public class AsyncSendData extends AsyncTask<Void, Void, Void> {

    private Activity activity;
    private String url;
    private CallBackAction callBackAction;
    private Result result;

    public AsyncSendData(Activity activity, String url, CallBackAction callBackAction) {
        this.activity = activity;
        this.url = url;
        this.callBackAction = callBackAction;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        int key = 0;
        String value = "";

        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
        HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = httpclient.execute(httpGet);
            result = new Result();
            HttpEntity httpEntity = response.getEntity();
            key = response.getStatusLine().getStatusCode();
            value = EntityUtils.toString(httpEntity);
            result.setResult(value);
            result.setStatus(key);

        } catch (ClientProtocolException e) {

        } catch (IOException e) {

        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        callBackAction.excute(result);
    }
}
