package com.example.duyhung.app_android.screen_slide_viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.duyhung.app_android.callback.CallBackNewTransferNonCustomer;
import com.example.duyhung.app_android.callback.CallBackObject;
import com.example.duyhung.app_android.callback.CallbackConfilm;
import com.example.duyhung.app_android.view.fragment.ScreenSlidePageFragmentAddCustomer;
import com.example.duyhung.app_android.view.fragment.ScreenSlidePageFragmentAddTransfer;

/**
 * Created by thetainguyen on 06/12/17.
 */

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUM_PAGES = 2;
    private static final String PAGES_CUSTOMER = "ADD CUSTOMER";
    private static final String PAGES_TRANSFER = "ADD TRANSFER";
    CallBackObject callBackObject;
    CallBackNewTransferNonCustomer callBackNewTransferNonCustomer;
    private String phone;

    public ScreenSlidePagerAdapter(FragmentManager fm, CallBackObject callBackObject,
                                   CallBackNewTransferNonCustomer callBackNewTransferNonCustomer,
                                   String phone) {
        super(fm);
        this.callBackObject = callBackObject;
        this.callBackNewTransferNonCustomer = callBackNewTransferNonCustomer;
        this.phone = phone;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        switch (position) {
            case 0:
                fragment = new ScreenSlidePageFragmentAddCustomer().newInstance(phone,callBackObject);
                break;
            case 1:
                fragment = new ScreenSlidePageFragmentAddTransfer().newInstance(phone,callBackNewTransferNonCustomer);
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return PAGES_CUSTOMER;
            case 1:
                return PAGES_TRANSFER;
        }
        return null;
    }
}
