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
import com.example.duyhung.app_android.callback.CallBackObject;
import com.example.duyhung.app_android.conconler.Controler;
import com.example.duyhung.app_android.module.Result;
import com.example.duyhung.app_android.module.Transfer;

import java.util.Date;

import static com.example.duyhung.app_android.Config.URL;

/**
 * Created by thetainguyen on 26/11/17.
 */

public class AddTransfer extends DialogFragment {

    String phoneNumber;
    private EditText item;
    private EditText sumMoney;
    private ProgressDialog progressDialog;
    Activity activity;
    CallBackObject callBackObject;


    public AddTransfer newInstance(String phoneNumber, Activity activity,CallBackObject callBackObject) {
        AddTransfer frg = new AddTransfer();
        frg.phoneNumber = phoneNumber;
        frg.activity = activity;
        frg.callBackObject = callBackObject;
        return frg;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_transfer, null);

        item = view.findViewById(R.id.name_product);
        sumMoney = view.findViewById(R.id.sum_money);
        builder.setView(view)

                .setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (!item.getText().toString().trim().equals("") && !sumMoney.getText().toString().trim().equals("")) {
                            dismiss();
                            showDialog();
                            Controler controler = new Controler(getActivity(), URL);
                            controler.addTrasfer(new CallBackAction() {

                                @Override
                                public void excute(Result result) {
                                    if (result != null) {
                                        if (result.getStatus() == 200) {
                                            callBackObject.returnObject(getData());
                                            Toast.makeText(activity, "create successfully", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(activity, "create fail", Toast.LENGTH_SHORT).show();
                                        }

                                    } else {
                                        Toast.makeText(activity, "create fail", Toast.LENGTH_SHORT).show();
                                    }
                                    hideDialog();
                                }
                            }, getData(), phoneNumber);
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

    private Transfer getData() {
        Transfer transfer = new Transfer();

        Date date = new Date();
        transfer.setItem(item.getText().toString().trim());
        String money =sumMoney.getText().toString().trim();
        money = money.replaceAll("\\s+","");
        money = money.replaceAll("\\s?","");
        transfer.setMoney(Integer.parseInt(money));
        transfer.setDate_transfer(date);
        return transfer;
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
