package com.example.duyhung.app_android.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.duyhung.app_android.R;
import com.example.duyhung.app_android.callback.CallBackAction;
import com.example.duyhung.app_android.callback.CallBackObject;
import com.example.duyhung.app_android.conconler.Controler;
import com.example.duyhung.app_android.customzbleAdapter.AdapterTranfer;
import com.example.duyhung.app_android.module.Customer;
import com.example.duyhung.app_android.module.Result;
import com.example.duyhung.app_android.module.Sum;
import com.example.duyhung.app_android.module.Transfer;
import com.example.duyhung.app_android.view.dialog.AddTransfer;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.duyhung.app_android.Config.LIMIT;
import static com.example.duyhung.app_android.Config.URL;

public class ActivityTransfer extends AppCompatActivity {


    private AdapterTranfer adapterTranfer;
    private ListView listView;
    private Customer customer;
    private List<Transfer> transferList;
    View viewLoadingFooter;
    private int prevItem = 0;
    private long numberMoney = 0L;

    private TextView name;
    private TextView phoneNumber;
    private TextView sumMoney;
    private TextView address;
    private TextView date;
    private TextView cmt;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        overridePendingTransition(R.anim.slide_right_to_left_in, R.anim.slide_right_to_left_out);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        customer = (Customer) getIntent().getSerializableExtra("customer");
        init();
        registerEvent();
        setText();
        getData(LIMIT, prevItem);

    }

    private void registerEvent() {

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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newTransfer();
            }
        });

    }

    private void newTransfer() {
        new AddTransfer().newInstance(customer.getPhone_number(), this, new CallBackObject() {
            @Override
            public void returnObject(Object object) {
                transferList.add(0, (Transfer) object);

                numberMoney += ((Transfer) object).getMoney();
                sumMoney.setText(printMoney(String.valueOf(numberMoney)));
                adapterTranfer.notifyDataSetChanged();
            }
        }).show(getFragmentManager(), "");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_left_to_right_in, R.anim.slide_left_to_right_out);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_transfer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void init() {
        name = findViewById(R.id.name_customer);
        phoneNumber = findViewById(R.id.phone_number);
        sumMoney = findViewById(R.id.sum_money);
        address = findViewById(R.id.address);
        date = findViewById(R.id.date);
        cmt = findViewById(R.id.cmt);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        transferList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list_transfer);
        adapterTranfer = new AdapterTranfer(this, R.layout.list_item_transfer, transferList);
        listView.setAdapter(adapterTranfer);

        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        viewLoadingFooter = inflater.inflate(R.layout.layout_loading, null);
        listView.addFooterView(viewLoadingFooter);

    }

    private void setText() {
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy  hh:mm:ss");
        name.setText(customer.getName());
        phoneNumber.setText(customer.getPhone_number());
        sumMoney.setText("");
        address.setText(customer.getAddress());
        date.setText(ft.format(customer.getDate()).toString());
        cmt.setText(customer.getCmt().toString());
        getSumMoney();
    }

    private String printMoney(String moneys) {
        if (moneys.equals("0")) {
            return "0đ";
        } else {
            StringBuilder builder = new StringBuilder();
            int leng = moneys.length();
            int begin = leng % 3;
            if (begin != 0)
                builder.append(moneys.substring(0, begin)).append(".");
            moneys = moneys.substring(begin);
            while (moneys.length() != 0) {
                builder.append(moneys.substring(0, 3));
                if (moneys.length() != 3)
                    builder.append(".");
                else
                    builder.append("đ");
                moneys = moneys.substring(3);

            }
            return builder.toString();
        }
    }

    private void getData(final int limit, int offset) {

        if (viewLoadingFooter.getVisibility() == View.GONE)
            viewLoadingFooter.setVisibility(View.VISIBLE);

        Controler controler = new Controler(this, URL);
        controler.getListTransfer(limit, offset, customer.getPhone_number(), new CallBackAction() {

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

    private void getSumMoney() {
        Controler controler = new Controler(this, URL);
        controler.getSumMoney(customer.getPhone_number(), new CallBackAction() {

            @Override
            public void excute(Result result) {
                if (result != null) {
                    if (result.getStatus() == 200) {
                        try {
                            Gson gson = new Gson();
                            Sum[] sums = gson.fromJson(result.getResult(), Sum[].class);
                            List<Sum> myObjects = new ArrayList<>(Arrays.asList(sums));
                            sumMoney.setText(printMoney(String.valueOf(myObjects.get(0).getSum())));
                            numberMoney = myObjects.get(0).getSum();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}
