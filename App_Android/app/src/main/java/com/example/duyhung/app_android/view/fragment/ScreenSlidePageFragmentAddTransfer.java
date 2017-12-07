package com.example.duyhung.app_android.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.duyhung.app_android.R;
import com.example.duyhung.app_android.callback.CallBackNewTransferNonCustomer;
import com.example.duyhung.app_android.module.Transfer;

import java.util.Date;

/**
 * Created by thetainguyen on 06/12/17.
 */

public class ScreenSlidePageFragmentAddTransfer extends Fragment {

    private EditText phoneNumber;
    private EditText summoney;
    private EditText nameProduct;
    private Button ok;
    private Button clean;
    private String phone;

    CallBackNewTransferNonCustomer callBackNewTransferNonCustomer;

    public ScreenSlidePageFragmentAddTransfer newInstance(String phone, CallBackNewTransferNonCustomer callBackNewTransferNonCustomer) {
        ScreenSlidePageFragmentAddTransfer frg = new ScreenSlidePageFragmentAddTransfer();
        frg.callBackNewTransferNonCustomer = callBackNewTransferNonCustomer;
        frg.phone = phone;
        return frg;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_add_tranfer, container, false);

        init(view);
        registerEvent();
        return view;
    }

    private void init(View view) {
        phoneNumber = view.findViewById(R.id.phone_number);
        if (phone != null)
            phoneNumber.setText(phone);
        summoney = view.findViewById(R.id.sum_money);
        nameProduct = view.findViewById(R.id.name_product);
        ok = view.findViewById(R.id.btn_ok);
        clean = view.findViewById(R.id.btn_clean);
    }

    private void registerEvent() {
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!nameProduct.getText().toString().trim().equals("") && !summoney.getText().toString().trim().equals("")) {
                }
                callBackNewTransferNonCustomer.excute(getData(), phoneNumber.getText().toString().trim());
            }
        });

        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumber.setText("");
                nameProduct.setText("");
                summoney.setText("");
            }
        });
    }

    private Transfer getData() {
        Transfer transfer = new Transfer();

        Date date = new Date();
        transfer.setItem(nameProduct.getText().toString().trim());
        String money = summoney.getText().toString().trim();
        money = money.replaceAll("\\s+", "");
        money = money.replaceAll("\\s?", "");
        transfer.setMoney(Integer.parseInt(money));
        transfer.setDate_transfer(date);
        return transfer;
    }

}
