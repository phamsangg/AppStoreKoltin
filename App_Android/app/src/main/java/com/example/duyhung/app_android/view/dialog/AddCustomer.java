package com.example.duyhung.app_android.view.dialog;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duyhung.app_android.R;
import com.example.duyhung.app_android.callback.CallBackAction;
import com.example.duyhung.app_android.conconler.Controler;
import com.example.duyhung.app_android.module.Customer;
import com.example.duyhung.app_android.module.Result;

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

    public AddCustomer newInstance(Activity activity) {
        AddCustomer addCustomer = new AddCustomer();
        addCustomer.activity = activity;
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

        builder.setView(view)

                .setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                        if (!address.getText().toString().trim().equals("") && !cmt.getText().toString().trim().equals("")
                                && !name.getText().toString().trim().equals("") && !phoneNumber.getText().toString().trim().equals("")) {
                            showDialog();
                            Controler controler = new Controler(getActivity(), URL);
                            controler.addCustomer(new CallBackAction() {
                                @Override
                                public void excute(Result result) {
                                    if(result!=null){
                                        if(result.getStatus()==200){
                                            Toast.makeText(activity,"create successfully",Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(activity,"create fail",Toast.LENGTH_SHORT).show();
                                        }

                                    }else{
                                        Toast.makeText(activity,"create fail",Toast.LENGTH_SHORT).show();
                                    }
                                    hideDialog();
                                }
                            }, create());

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
