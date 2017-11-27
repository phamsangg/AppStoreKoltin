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

    public AddTransfer newInstance(String phoneNumber) {
        AddTransfer frg = new AddTransfer();
        frg.phoneNumber = phoneNumber;
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
                        if(!item.getText().toString().trim().equals("")&& !sumMoney.getText().toString().trim().equals("")){
                            Controler controler = new Controler(getActivity(),URL);
                            controler.addTrasfer(new CallBackAction() {
                                @Override
                                public void result(Boolean result) {

                                }
                            },getData());
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

    private Transfer getData(){
        Transfer transfer = new Transfer();

        Date date = new Date();
        transfer.setItem(item.getText().toString().trim());
        transfer.setMoney(Integer.parseInt(sumMoney.getText().toString().trim()));
        transfer.setDate_transfer(date);
        transfer.setCustomer_phone_number(phoneNumber);
        return transfer;
    }
}
