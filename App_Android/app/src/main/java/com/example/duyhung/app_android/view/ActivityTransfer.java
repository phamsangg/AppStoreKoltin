package com.example.duyhung.app_android.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duyhung.app_android.R;
import com.example.duyhung.app_android.callback.CallBackAction;
import com.example.duyhung.app_android.callback.CallBackObject;
import com.example.duyhung.app_android.conconler.Controler;
import com.example.duyhung.app_android.customzbleAdapter.AdapterTranfer;
import com.example.duyhung.app_android.module.Customer;
import com.example.duyhung.app_android.module.Transfer;
import com.example.duyhung.app_android.service.modules.Result;
import com.example.duyhung.app_android.service.modules.Sum;
import com.example.duyhung.app_android.view.dialog.AddTransfer;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.duyhung.app_android.Config.GET_RESULT_EDITPROFILE_CUSTOMER;
import static com.example.duyhung.app_android.Config.LIMIT;
import static com.example.duyhung.app_android.Config.URL;
import static com.example.duyhung.app_android.Config.ft;

public class ActivityTransfer extends AppCompatActivity {


    private AdapterTranfer adapterTranfer;
    private ListView listView;
    private Customer customer;
    private Customer customerUpdate;
    private List<Transfer> transferList;
    View viewLoadingFooter;
    private int prevItem = 0;
    private long numberMoney = 0L;
    private int solution;

    private EditText name;
    private EditText phoneNumber;
    private EditText sumMoney;
    private EditText address;
    private EditText date;
    private EditText cmt;
    private FloatingActionButton fab;
    private TextView update;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        overridePendingTransition(R.anim.slide_right_to_left_in, R.anim.slide_right_to_left_out);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        customer = (Customer) getIntent().getSerializableExtra("customer");
        customerUpdate = (Customer) getIntent().getSerializableExtra("customer");
        solution = getIntent().getIntExtra("solution", -1);
        init();
        registerEvent();
        setText(customer);
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

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("thetai: " + R.string.title_edit);
                if (update.getText().toString().equals(getResources().getString(R.string.title_edit)))
                    edit();
                else
                    update();
            }
        });

    }

    private void edit() {
        update.setText(getText(R.string.title_update));
        setEnable(true);
        setTextNull();
    }

    private void update() {
        update.setText(getText(R.string.title_edit));
        setEnable(false);
        boolean cancel = true;
        if (customer.getCmt() == null && !TextUtils.isEmpty(cmt.getText()))
            cancel = false;
        if (customer.getName() == null && !TextUtils.isEmpty(name.getText()))
            cancel = false;
        if (customer.getAddress() == null && !TextUtils.isEmpty(address.getText()))
            cancel = false;
        if (customer.getCmt() != null && !customer.getCmt().equals(cmt.getText().toString().trim()))
            cancel = false;
        if (customer.getName() != null && !customer.getName().equals(name.getText().toString().trim()))
            cancel = false;
        if (customer.getAddress() != null && !customer.getAddress().equals(address.getText().toString().trim()))
            cancel = false;

        if (!cancel) {

            if (!TextUtils.isEmpty(name.getText()))
                customerUpdate.setName(name.getText().toString().trim());
            if (!TextUtils.isEmpty(address.getText()))
                customerUpdate.setAddress(address.getText().toString().trim());
            if (!TextUtils.isEmpty(cmt.getText()))
                customerUpdate.setCmt(cmt.getText().toString().trim());

            setUpdate(customerUpdate);
        }

    }

    private void setEnable(boolean enable) {
        name.setEnabled(enable);
        address.setEnabled(enable);
        cmt.setEnabled(enable);
    }

    private void setTextNull() {
        if (customer.getName() == null)
            name.setText("");
        else
            name.setText(customer.getName());
        if (customer.getAddress() == null)
            address.setText("");
        else address.setText(customer.getAddress());
        if (customer.getCmt() == null)
            cmt.setText("");
        else
            cmt.setText(customer.getCmt());
    }

    private void newTransfer() {
        new AddTransfer().newInstance(customer.getPhone_number(), this, new CallBackObject() {
            @Override
            public void returnObject(Object object) {
                transferList.add(0, (Transfer) object);

                numberMoney += ((Transfer) object).getMoney();
                sumMoney.setText(printMoney(String.valueOf(numberMoney)));
                customerUpdate.setLateDateItem(((Transfer) object).getDate_transfer());
                adapterTranfer.notifyDataSetChanged();
            }
        }).show(getFragmentManager(), "");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(GET_RESULT_EDITPROFILE_CUSTOMER, (Serializable) customerUpdate);
        intent.putExtra("solution", solution);
        setResult(Activity.RESULT_OK,intent);
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_left_to_right_in, R.anim.slide_left_to_right_out);
    }

    private void init() {
        name = findViewById(R.id.name_customer);
        phoneNumber = findViewById(R.id.phone_number);
        sumMoney = findViewById(R.id.sum_money);
        address = findViewById(R.id.address);
        date = findViewById(R.id.date);
        cmt = findViewById(R.id.cmt);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        update = findViewById(R.id.update);

        transferList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list_transfer);
        adapterTranfer = new AdapterTranfer(this, R.layout.list_item_transfer, transferList);
        listView.setAdapter(adapterTranfer);

        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        viewLoadingFooter = inflater.inflate(R.layout.layout_loading, null);
        listView.addFooterView(viewLoadingFooter, null, false);

    }

    private void setText(Customer customer) {
        if (customer.getName() != null)
            name.setText(customer.getName());
        else
            name.setText(getText(R.string.not_info));
        if (customer.getPhone_number() != null)
            phoneNumber.setText(customer.getPhone_number());
        else
            phoneNumber.setText(getText(R.string.not_info));
        if (customer.getAddress() != null)
            address.setText(customer.getAddress());
        else
            address.setText(getText(R.string.not_info));
        if (customer.getDate() != null)
            date.setText(ft.format(customer.getDate()).toString());
        else
            date.setText(getText(R.string.not_info));
        if (customer.getCmt() != null)
            cmt.setText(customer.getCmt().toString());
        else
            cmt.setText(getText(R.string.not_info));
        getSumMoney();
    }

    public static String printMoney(String moneys) {
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

    private void setUpdate(final Customer customer) {
        showDialog();
        Controler controler = new Controler(this, URL);
        controler.updateCustomer(new CallBackAction() {
            @Override
            public void excute(Result result) {
                if (result != null) {
                    if (result.getStatus() == 200) {
                        setText(customer);
                        Toast.makeText(getBaseContext(), "update successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getBaseContext(), "update fail", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getBaseContext(), "update fail", Toast.LENGTH_SHORT).show();
                }
                hideDialog();
            }
        }, customer);
    }

    private void showDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        if (!progressDialog.isShowing()) {
            progressDialog.setMessage("updating...");
            progressDialog.show();
        }

    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }
}
