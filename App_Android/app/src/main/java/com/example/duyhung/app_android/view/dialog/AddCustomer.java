package com.example.duyhung.app_android.view.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.duyhung.app_android.R;
import com.example.duyhung.app_android.callback.CallBackAction;
import com.example.duyhung.app_android.conconler.Controler;
import com.example.duyhung.app_android.module.Customer;

import static com.example.duyhung.app_android.Config.URL;


/**
 * Created by duyhung on 25/11/2017.
 */

public class AddCustomer extends DialogFragment {

    private EditText phoneNumber;
    private EditText name;
    private EditText address;
    private EditText cmt;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_customer, null);
        phoneNumber = view.findViewById(R.id.phone_number);
        name = view.findViewById(R.id.name);
        address = view.findViewById(R.id.address);
        cmt = view.findViewById(R.id.id_cmnd);

        builder.setView(view)

                .setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (!address.getText().toString().trim().equals("") && !cmt.getText().toString().trim().equals("")
                                && !name.getText().toString().trim().equals("") && !phoneNumber.getText().toString().trim().equals("")) {

                            Controler controler = new Controler(getActivity(), URL);
                            controler.addCustomer(new CallBackAction() {
                                @Override
                                public void result(Boolean result) {

                                }
                            }, create());
                            dismiss();
                        }
                    }
                })
                .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });
        return builder.create();
    }


    private Customer create() {
        Customer customer = new Customer();
        customer.setAddress(address.getText().toString().trim());
        customer.setCmt(cmt.getText().toString().trim());
        customer.setName(name.getText().toString().trim());
        customer.setPhone_number(phoneNumber.getText().toString().trim());
        return customer;
    }
}
