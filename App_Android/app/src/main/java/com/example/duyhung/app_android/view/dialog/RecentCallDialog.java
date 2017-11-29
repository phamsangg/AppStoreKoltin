package com.example.duyhung.app_android.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.duyhung.app_android.R;
import com.example.duyhung.app_android.callback.CallBackEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by thetainguyen on 29/11/17.
 */

public class RecentCallDialog extends DialogFragment {

    private Set<String> listPhone;
    private Activity activity;
    private ListView listView;
    private CallBackEvent callBackEvent;

    public RecentCallDialog newInstance(Activity activity, Set<String> listPhone, CallBackEvent callBackEvent) {
        RecentCallDialog recentCallDialog = new RecentCallDialog();
        recentCallDialog.activity = activity;
        recentCallDialog.listPhone = listPhone;
        recentCallDialog.callBackEvent = callBackEvent;
        return recentCallDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.list_recent_call, null);
        final List<String> listData = new ArrayList<>();
        listData.addAll(listPhone);

        listView = view.findViewById(R.id.list_contact);

        if (listPhone.size() == 0) {
            listData.add(getResources().getString(R.string.err_recent_call));
        }
        listView.setAdapter(new ArrayAdapter<String>(activity,
                android.R.layout.simple_list_item_1, listData));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String string = String.valueOf(adapterView.getItemAtPosition(i));
                callBackEvent.getPhone(string.trim());
            }
        });
        builder.setView(view);
        return builder.create();
    }
}
