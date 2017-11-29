package com.example.duyhung.app_android.view.dialog;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duyhung.app_android.R;
import com.example.duyhung.app_android.callback.CallBackAction;
import com.example.duyhung.app_android.callback.CallBackObject;
import com.example.duyhung.app_android.conconler.Controler;
import com.example.duyhung.app_android.module.Customer;
import com.example.duyhung.app_android.module.Result;

import java.util.Date;

import static com.example.duyhung.app_android.Config.URL;


/**
 * Created by duyhung on 25/11/2017.
 */

public class AddCustomer extends DialogFragment {

    private EditText phoneNumber;
    private EditText name;
    private EditText address;
    private EditText cmt;
    private ProgressDialog progressDialog;
    private Activity activity;
    private Button ok;
    private Button cancel;
    private CallBackObject callBackObject;
    private String phone;

    public AddCustomer newInstance(Activity activity, CallBackObject callBackObject) {
        AddCustomer addCustomer = new AddCustomer();
        addCustomer.activity = activity;
        addCustomer.callBackObject = callBackObject;
        return addCustomer;
    }

    public AddCustomer newInstance(Activity activity, CallBackObject callBackObject, String phone) {
        AddCustomer addCustomer = new AddCustomer();
        addCustomer.activity = activity;
        addCustomer.callBackObject = callBackObject;
        addCustomer.phone = phone;
        return addCustomer;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_customer, null);
        phoneNumber = view.findViewById(R.id.phone_number);
        name = view.findViewById(R.id.name);
        address = view.findViewById(R.id.address);
        cmt = view.findViewById(R.id.id_cmnd);
        ok = view.findViewById(R.id.btn_ok);
        cancel = view.findViewById(R.id.btn_cancel);

        if (phone != null) {
            phoneNumber.setText(phone);
            phoneNumber.setEnabled(false);
        }

        builder.setView(view);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!address.getText().toString().trim().equals("") && !cmt.getText().toString().trim().equals("")
                        && !name.getText().toString().trim().equals("") && !phoneNumber.getText().toString().trim().equals("")) {
                    dismiss();
                    showDialog();
                    Controler controler = new Controler(getActivity(), URL);
                    controler.addCustomer(new CallBackAction() {
                        @Override
                        public void excute(Result result) {
                            if (result != null) {
                                if (result.getStatus() == 200) {
                                    callBackObject.returnObject(create());
                                    Toast.makeText(activity, "create successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(activity, "create fail", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(activity, "create fail", Toast.LENGTH_SHORT).show();
                            }
                            hideDialog();
                        }
                    }, create());

                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return builder.create();
    }


    private Customer create() {
        Customer customer = new Customer();
        Date date = new Date();
        customer.setAddress(address.getText().toString().trim());
        customer.setCmt(cmt.getText().toString().trim());
        customer.setName(name.getText().toString().trim());
        String phone = phoneNumber.getText().toString().trim();
        phone = phone.replaceAll("\\s+", "");
        phone = phone.replaceAll("\\s?", "");
        customer.setPhone_number(phone);
        customer.setDate(date);
        return customer;
    }

    private void showDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(activity);
        }
        if (!progressDialog.isShowing()) {
            progressDialog.setMessage("creating...");
            progressDialog.show();
        }

    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }

}
