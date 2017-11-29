package com.example.duyhung.app_android.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.duyhung.app_android.R;
import com.example.duyhung.app_android.callback.CallbackConfilm;

/**
 * Created by thetainguyen on 29/11/17.
 */

public class ConfilmDialog extends DialogFragment {

    String messager;
    CallbackConfilm callbackConfilm;

    public ConfilmDialog newInstance(String messager, CallbackConfilm callbackConfilm) {
        ConfilmDialog frg = new ConfilmDialog();
        frg.messager = messager;
        frg.callbackConfilm = callbackConfilm;
        return frg;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(messager)
                .setPositiveButton(R.string.btn_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                callbackConfilm.confilm();
                                dismiss();
                            }
                        }
                )
                .setNegativeButton(R.string.btn_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dismiss();
                            }
                        }
                );
        return builder.create();
    }
}
