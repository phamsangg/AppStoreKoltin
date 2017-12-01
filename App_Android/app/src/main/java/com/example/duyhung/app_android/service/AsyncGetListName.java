package com.example.duyhung.app_android.service;

import android.os.AsyncTask;
import android.util.Log;

import com.example.duyhung.app_android.callback.CallBackAction;
import com.example.duyhung.app_android.service.modules.Result;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import static com.example.duyhung.app_android.Config.TIMEOUT_MILLISEC;

/**
 * Created by thetainguyen on 01/12/17.
 */

public class AsyncGetListName extends AsyncTask<Void, Void, Void> {

    private String URL;
    String responseString = null;
    CallBackAction callBackAction;
    Result result = null;

    public AsyncGetListName(String URL, CallBackAction callBackAction) {
        this.URL = URL;

        this.callBackAction = callBackAction;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        HttpPost postRequest = new HttpPost(URL);

        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
        HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);

        try {

            HttpClient httpClient = new DefaultHttpClient(httpParams);

            HttpGet httpGet = new HttpGet(URL);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            result = new Result();
            result.setStatus( httpResponse.getStatusLine().getStatusCode());
            HttpEntity httpEntity = httpResponse.getEntity();

            result.setResult(EntityUtils.toString(httpEntity));


        } catch (Exception e) {
            // TODO: handle exception
            postRequest.abort();
            Log.w("HttpPostRetreiver", "Error for URL " + URL, e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        callBackAction.excute(result);
    }
}
