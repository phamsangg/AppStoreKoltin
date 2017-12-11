package com.example.duyhung.app_android.view.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.duyhung.app_android.R;
import com.example.duyhung.app_android.callback.CallBackObject;
import com.example.duyhung.app_android.module.Customer;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by thetainguyen on 06/12/17.
 */

public class ScreenSlidePageFragmentAddCustomer extends Fragment {

    private EditText phoneNumber;
    private EditText address;
    private EditText name;
    private EditText cmt;
    private Button ok;
    private Button clean;
    private String phone;
    CallBackObject callBackObject;

    public ScreenSlidePageFragmentAddCustomer newInstance(CallBackObject callBackObject) {
        ScreenSlidePageFragmentAddCustomer frg = new ScreenSlidePageFragmentAddCustomer();
        frg.callBackObject = callBackObject;
        return frg;
    }

    public ScreenSlidePageFragmentAddCustomer newInstance(String phone, CallBackObject callBackObject) {
        ScreenSlidePageFragmentAddCustomer frg = new ScreenSlidePageFragmentAddCustomer();
        frg.phone = phone;
        frg.callBackObject = callBackObject;
        return frg;
    }

    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_add_customer, container, false);
        init(view);
        registerEvent();

        return view;
    }

    private void init(View view) {
        phoneNumber = view.findViewById(R.id.phone_number);
        if (phone != null) {
            phoneNumber.setText(phone);
            phoneNumber.setEnabled(false);
        }

        name = view.findViewById(R.id.name);
        address = view.findViewById(R.id.address);
        cmt = view.findViewById(R.id.id_cmnd);
        ok = view.findViewById(R.id.btn_ok);
        clean = view.findViewById(R.id.btn_clean);
    }

    private void registerEvent() {
        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phoneNumber.isEnabled() == true)
                    phoneNumber.setText("");
                name.setText("");
                address.setText("");
                cmt.setText("");
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(phoneNumber.getText())) {
                    callBackObject.returnObject(create());
                }
            }
        });
    }

    private Customer create() {
        Customer customer = new Customer();
        Calendar now = Calendar.getInstance();
        customer.setAddress(address.getText().toString().trim());
        customer.setCmt(cmt.getText().toString().trim());
        customer.setName(name.getText().toString().trim());
        String phone = phoneNumber.getText().toString().trim();
        phone = phone.replaceAll("\\s+", "");
        phone = phone.replaceAll("\\s?", "");
        customer.setPhone_number(phone);
        customer.setDate(now.getTimeInMillis());
        return customer;
    }


}
