package com.example.duyhung.app_android.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;

import com.example.duyhung.app_android.R;
import com.example.duyhung.app_android.callback.CallBackAction;
import com.example.duyhung.app_android.conconler.Controler;
import com.example.duyhung.app_android.customzbleAdapter.AdapterTranfer;
import com.example.duyhung.app_android.module.Transfer;
import com.example.duyhung.app_android.service.modules.Result;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.duyhung.app_android.Config.LIMIT;
import static com.example.duyhung.app_android.Config.URL;

public class StatisticalActivity extends AppCompatActivity {

    private Button beginDay;
    private Button endDay;
    private ListView listView;
    View viewLoadingFooter;
    AdapterTranfer adapterTranfer;
    List<Transfer> transferList;

    private Date beginDate;
    private Date endDate = new Date();

    SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");

    final int DATE_PICKER_TO = 0;
    final int DATE_PICKER_FROM = 1;

    private int prevItem = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_statistical);
        overridePendingTransition(R.anim.slide_right_to_left_in, R.anim.slide_right_to_left_out);

        init();
        register();
        getData(LIMIT, 0);
    }

    private void init() {
        beginDay = findViewById(R.id.begin);
        endDay = findViewById(R.id.end);
        listView = findViewById(R.id.list_transfer);
        transferList = new ArrayList<>();
        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        viewLoadingFooter = inflater.inflate(R.layout.layout_loading, null);
        listView.addFooterView(viewLoadingFooter, null, false);

        beginDate = getBeginDate();
        adapterTranfer = new AdapterTranfer(this, R.layout.list_item_transfer, transferList);
        listView.setAdapter(adapterTranfer);
    }

    private void register() {

        beginDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateDialog(DATE_PICKER_FROM).show();
            }
        });

        endDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateDialog(DATE_PICKER_TO).show();
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                switch (view.getId()) {
                    case R.id.list_transfer:

                        final int lastItem = firstVisibleItem + visibleItemCount;

                        if (lastItem == totalItemCount) {

                            if (firstVisibleItem != 0 && prevItem != lastItem) {
                                getData(LIMIT, prevItem);
                            }
                            prevItem = lastItem;
                        }
                        break;
                }
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        Calendar now = Calendar.getInstance();
        switch (id) {
            case DATE_PICKER_FROM:
                return new DatePickerDialog(this, from_dateListener, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE));
            case DATE_PICKER_TO:
                return new DatePickerDialog(this, to_dateListener, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE));
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener from_dateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            beginDate = setBeginDay(dayOfMonth - 1, monthOfYear, year - 1900);
            getData(LIMIT, 0);
        }

    };

    private DatePickerDialog.OnDateSetListener to_dateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            endDate = setEndDay(dayOfMonth - 1, monthOfYear, year - 1900);
            getData(LIMIT, 0);
        }

    };


    private Date setBeginDay(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.DATE, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        beginDay.setText(f.format(calendar.getTime()));
        return calendar.getTime();
    }

    private Date setEndDay(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.DATE, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        endDay.setText(f.format(calendar.getTime()));
        return calendar.getTime();
    }

    private Date getBeginDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate); // compute start of the day for the timestamp
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private void getData(final int limit, int offset) {
        if (viewLoadingFooter.getVisibility() == View.GONE)
            viewLoadingFooter.setVisibility(View.VISIBLE);

        Controler controler = new Controler(this, URL);
        controler.statisticalTransfer(beginDate.getTime(), endDate.getTime(), limit, offset, new CallBackAction() {
            @Override
            public void excute(Result result) {
                View view = getWindow().getDecorView().findViewById(android.R.id.content);
                if (result != null) {
                    if (result.getStatus() == 200) {
                        try {
                            Gson gson = new Gson();
                            Transfer[] object = gson.fromJson(result.getResult(), Transfer[].class);
                            List<Transfer> myObjects = new ArrayList<>(Arrays.asList(object));
                            transferList.addAll(myObjects);
                            if (myObjects.size() < limit)
                                viewLoadingFooter.setVisibility(View.GONE);
                            adapterTranfer.notifyDataSetChanged();

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else {
                        Snackbar.make(view, "Get data fail", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                } else {
                    Snackbar.make(view, "No intenet connection", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_left_to_right_in, R.anim.slide_left_to_right_out);
    }
}
