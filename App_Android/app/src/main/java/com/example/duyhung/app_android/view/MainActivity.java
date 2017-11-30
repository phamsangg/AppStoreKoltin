package com.example.duyhung.app_android.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.duyhung.app_android.R;
import com.example.duyhung.app_android.callback.CallBackAction;
import com.example.duyhung.app_android.callback.CallBackObject;
import com.example.duyhung.app_android.callback.CallbackConfilm;
import com.example.duyhung.app_android.conconler.Controler;
import com.example.duyhung.app_android.customzbleAdapter.AdapterCustomer;
import com.example.duyhung.app_android.event.ServiceReceiver;
import com.example.duyhung.app_android.module.Customer;
import com.example.duyhung.app_android.module.Result;
import com.example.duyhung.app_android.view.dialog.AddCustomer;
import com.example.duyhung.app_android.view.dialog.ConfilmDialog;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.duyhung.app_android.Config.LIMIT;
import static com.example.duyhung.app_android.Config.URL;

;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private AdapterCustomer adapterCustomer;
    private List<Customer> customerList;
    private EditText search;
    View viewLoadingFooter;
    private int prevItem = 0;

    private DrawerLayout mDrawerLayout;
    private ListView listViewCommingCall;
    private List<String> listPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout_main_ativity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        listView = (ListView) findViewById(R.id.lvCustomer);

        listPhoneNumber = new ArrayList<>();

        customerList = new ArrayList<>();
        adapterCustomer = new AdapterCustomer(this, R.layout.list_item_customer, customerList);
        listView.setAdapter(adapterCustomer);

        search = findViewById(R.id.search);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listViewCommingCall = findViewById(R.id.list_comming_call);

        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        viewLoadingFooter = inflater.inflate(R.layout.layout_loading, null);
        listView.addFooterView(viewLoadingFooter);

        listViewCommingCall.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listPhoneNumber));

        getData(LIMIT, 0, search.getText().toString().trim());


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCustomer();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Customer customer = (Customer) adapterView.getItemAtPosition(i);
                startTransferActivity(customer);
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                search(search.getText().toString().trim().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                switch (view.getId()) {
                    case R.id.lvCustomer:

                        final int lastItem = firstVisibleItem + visibleItemCount;

                        if (lastItem == totalItemCount) {

                            if (firstVisibleItem != 0 && prevItem != lastItem) {
                                getData(LIMIT, prevItem, search.getText().toString().trim());
                            }
                            prevItem = lastItem;
                        }
                        break;
                }
            }
        });

        listViewCommingCall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String string = String.valueOf(adapterView.getItemAtPosition(i));
                getDataCustomer(string);
                mDrawerLayout.closeDrawers();
            }
        });

        Intent i = new Intent(this, ServiceReceiver.class);
        this.startService(i);

    }

    private void addCustomer() {
        new AddCustomer().newInstance(this, new CallBackObject() {
            @Override
            public void returnObject(Object object) {
                customerList.add(0, (Customer) object);
                adapterCustomer.notifyDataSetChanged();
            }
        }).show(getFragmentManager(), "");
    }

    private void search(String keyWord) {
        keyWord = '%' + keyWord + '%';
        if (customerList.size() != 0)
            customerList.clear();
        getData(LIMIT, 0, keyWord);
        adapterCustomer.notifyDataSetChanged();
    }


    private void getData(final int limit, int offset, String like) {

        if (viewLoadingFooter.getVisibility() == View.GONE)
            viewLoadingFooter.setVisibility(View.VISIBLE);

        Controler controler = new Controler(this, URL);
        controler.getListCustomer(limit, offset, like, new CallBackAction() {
            @SuppressLint("ResourceType")
            @Override
            public void excute(Result result) {
                View view = getWindow().getDecorView().findViewById(android.R.id.content);
                if (result != null) {
                    if (result.getStatus() == 200) {
                        try {
                            Gson gson = new Gson();
                            Customer[] object = gson.fromJson(result.getResult(), Customer[].class);
                            List<Customer> myObjects = new ArrayList<>(Arrays.asList(object));
                            customerList.addAll(myObjects);
                            if (myObjects.size() < limit)
                                viewLoadingFooter.setVisibility(View.GONE);
                            adapterCustomer.notifyDataSetChanged();
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

    private void getDataCustomer(final String phone) {
        Controler controler = new Controler(this, URL);
        controler.getCutomer(new CallBackAction() {
            @SuppressLint("ResourceType")
            @Override
            public void excute(Result result) {
                View view = getWindow().getDecorView().findViewById(android.R.id.content);
                if (result != null) {
                    if (result.getStatus() == 200) {
                        try {
                            Gson gson = new Gson();
                            Customer[] object = gson.fromJson(result.getResult(), Customer[].class);
                            List<Customer> myObjects = new ArrayList<>(Arrays.asList(object));
                            if (myObjects.size() == 0) {
                                new ConfilmDialog().newInstance("Số điện thoại chưa tồn tại. Bạn có muốn tạo mới không ?", new CallbackConfilm() {
                                    @Override
                                    public void confilm() {
                                        new AddCustomer().newInstance(getParent(), new CallBackObject() {
                                            @Override
                                            public void returnObject(Object object) {
                                                customerList.add(0, (Customer) object);
                                                adapterCustomer.notifyDataSetChanged();
                                            }
                                        }, phone).show(getFragmentManager(), "");
                                    }
                                }).show(getFragmentManager(), "");
                            } else {
                                Customer customer = myObjects.get(0);
                                startTransferActivity(customer);
                            }

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } else {
                    Snackbar.make(view, "No intenet connection", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        }, phone);
    }

    @Override
    public void onBackPressed() {
        if (search.getText().toString().trim().equals("")) {
            super.onBackPressed();
        } else {
            search.setText("");
        }
    }

    public BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String str = intent.getStringExtra("data");
            if (str != null && !str.equals("")) {
                if (checkListPhone(str)) {
                    listPhoneNumber.add(0, str);
                    ((BaseAdapter) listViewCommingCall.getAdapter()).notifyDataSetChanged();
                }

            }
        }
    };

    private boolean checkListPhone(String string) {
        for (String s : listPhoneNumber) {
            if (string.equals(s)) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,
                new IntentFilter(ServiceReceiver.NOTIFICATION));
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onDestroy();
    }

    private void startTransferActivity(Customer customer) {
        Intent nextActivity = new Intent(MainActivity.this, ActivityTransfer.class);
        nextActivity.putExtra("customer", (Serializable) customer);
        startActivity(nextActivity);
    }
}
