package com.example.duyhung.app_android.event;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * Created by thetainguyen on 29/11/17.
 */

public class ServiceReceiver extends BroadcastReceiver {
    public static final String NOTIFICATION = "receiver_call";

    private static final String TAG = "PhoneStateBroadcastReceiver";
    Context mContext;
    String incoming_nr;
    private int prev_state;

    @Override
    public void onReceive(final Context context, Intent intent) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);;
        telephony.listen(new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);

                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:

                        prev_state = state;
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        prev_state = state;
                        break;
                    case TelephonyManager.CALL_STATE_IDLE:
                        if ((prev_state == TelephonyManager.CALL_STATE_OFFHOOK)) {
                            Intent broadcast = new Intent(NOTIFICATION);
                            broadcast.putExtra("data", incomingNumber);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(broadcast);
                        }
                        if ((prev_state == TelephonyManager.CALL_STATE_RINGING)) {
                            Intent broadcast = new Intent(NOTIFICATION);
                            broadcast.putExtra("data", incomingNumber);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(broadcast);
                        }
                        break;

                }

            }
        }, PhoneStateListener.LISTEN_CALL_STATE);
    }

}
