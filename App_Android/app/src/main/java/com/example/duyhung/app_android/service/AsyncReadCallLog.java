package com.example.duyhung.app_android.service;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.CallLog;

import com.example.duyhung.app_android.R;
import com.example.duyhung.app_android.callback.CallBackListObject;
import com.example.duyhung.app_android.module.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thetainguyen on 01/12/17.
 */

public class AsyncReadCallLog extends AsyncTask<Void, Void, List<Contact>> {

    private Cursor cursor;
    private Activity activity;
    private CallBackListObject callBackListObject;

    public AsyncReadCallLog(Cursor cursor, Activity activity, CallBackListObject callBackListObject) {
        this.cursor = cursor;
        this.activity = activity;
        this.callBackListObject = callBackListObject;
    }

    @Override
    protected List<Contact> doInBackground(Void... voids) {

        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
        List<Contact> contactList = new ArrayList<>();

        while (cursor.moveToNext()) {

            Contact contact = new Contact();

            contact.setPhNum(formatPhoneNumber(cursor.getString(number)));
            contact.setCallDate(Long.valueOf(cursor.getString(date)));
            contact.setCallDuration(cursor.getString(duration));

            int callType = Integer.parseInt(cursor.getString(type));

            switch (callType) {
                case CallLog.Calls.OUTGOING_TYPE:
                    contact.setCallType(R.drawable.ic_call_made_black_24dp);
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    contact.setCallType(R.drawable.ic_call_received_black_24dp);
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    contact.setCallType(R.drawable.ic_call_missed_black_24dp);
                    break;
            }

            contactList.add(contact);
        }

        return contactList;
    }

    @Override
    protected void onPostExecute(List<Contact> contacts) {
        super.onPostExecute(contacts);
        callBackListObject.getListObject(contacts);
        return;
    }

    private static String formatPhoneNumber(String txt){
        txt = txt.replace("+84","0");
        txt = txt.replace(" ","");
        return txt;
    }
}
