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
    @Override
    public void onReceive(final Context context, Intent intent) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        telephony.listen(new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);
                Intent broadcast = new Intent(NOTIFICATION);
                broadcast.putExtra("data", incomingNumber);
                LocalBroadcastManager.getInstance(context).sendBroadcast(broadcast);
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);
    }


}
