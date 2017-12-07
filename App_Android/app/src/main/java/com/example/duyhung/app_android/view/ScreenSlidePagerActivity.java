package com.example.duyhung.app_android.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;

import com.example.duyhung.app_android.R;
import com.example.duyhung.app_android.callback.CallBackNewTransferNonCustomer;
import com.example.duyhung.app_android.callback.CallBackObject;
import com.example.duyhung.app_android.callback.CallbackConfilm;
import com.example.duyhung.app_android.module.Transfer;
import com.example.duyhung.app_android.screen_slide_viewpager.ScreenSlidePagerAdapter;

import java.io.Serializable;

import static com.example.duyhung.app_android.Config.GET_CUSTOMER_PHONE;
import static com.example.duyhung.app_android.Config.GET_RESULT_NEW_CUSTOMER;
import static com.example.duyhung.app_android.Config.GET_RESULT_NEW_TRANSFER;
import static com.example.duyhung.app_android.Config.GET_RESULT_PHONE;

/**
 * Created by thetainguyen on 06/12/17.
 */

public class ScreenSlidePagerActivity extends AppCompatActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;
    private TabLayout tabLayout;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;
    private String phone ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_activity_screen_slide);
        overridePendingTransition(R.anim.slide_right_to_left_in, R.anim.slide_right_to_left_out);

        mPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mPager);
        phone = getIntent().getStringExtra(GET_RESULT_PHONE);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), new CallBackObject() {
            @Override
            public void returnObject(Object object) {
                Intent intent = new Intent();
                intent.putExtra(GET_RESULT_NEW_CUSTOMER, (Serializable) object);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        }, new CallBackNewTransferNonCustomer() {
            @Override
            public void excute(Transfer transfer, String phone) {
                Intent intent = new Intent();
                intent.putExtra(GET_RESULT_NEW_TRANSFER, (Serializable) transfer);
                intent.putExtra(GET_CUSTOMER_PHONE, phone);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        },phone);
        mPager.setAdapter(mPagerAdapter);
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.slide_left_to_right_in, R.anim.slide_left_to_right_out);
        super.onBackPressed();
    }
}
