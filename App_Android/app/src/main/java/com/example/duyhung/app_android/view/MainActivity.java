package com.example.duyhung.app_android.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.duyhung.app_android.R;
import com.example.duyhung.app_android.callback.CallBackAction;
import com.example.duyhung.app_android.callback.CallBackObject;
import com.example.duyhung.app_android.callback.CallbackConfilm;
import com.example.duyhung.app_android.conconler.Controler;
import com.example.duyhung.app_android.customzbleAdapter.AdapterContact;
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
    private EditText search;
    View viewLoadingFooter;
    private int prevItem = 0;

    private DrawerLayout mDrawerLayout;
    private ListView listViewCommingCall;
    private TextView noItem;
    private ImageView open;

    private AdapterCustomer adapterCustomer;
    private List<Customer> customerList;
    private List<Customer> listPhoneContact;
    private AdapterContact adapterContact;
    private List<String> stringListPhoneContact;

    private BroadcastReceiver receiver;
    private int MY_PERMISSIONS_REQUEST_SMS_RECEIVE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout_main_ativity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        listView = (ListView) findViewById(R.id.lvCustomer);
        open = findViewById(R.id.open_navigation);

        listPhoneContact = new ArrayList<>();

        customerList = new ArrayList<>();
        stringListPhoneContact = new ArrayList<>();
        adapterCustomer = new AdapterCustomer(this, R.layout.list_item_customer, customerList);
        listView.setAdapter(adapterCustomer);

        adapterContact = new AdapterContact(this, R.layout.item_contact, listPhoneContact);

        search = findViewById(R.id.search);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listViewCommingCall = findViewById(R.id.list_comming_call);
        noItem = findViewById(R.id.txt_no_item);

        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        viewLoadingFooter = inflater.inflate(R.layout.layout_loading, null);
        listView.addFooterView(viewLoadingFooter);

        listViewCommingCall.setAdapter(adapterContact);

        getData(LIMIT, 0, search.getText().toString().trim());

        displayItemNavigation();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCustomer(null);
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

                final Customer customer = (Customer) adapterView.getItemAtPosition(i);
                mDrawerLayout.closeDrawers();
                if (customer.getName() != null && customer.getDate() != null && customer.getDate() != null && customer.getCmt() != null
                        && customer.getAddress() != null) {
                    startTransferActivity(customer);
                } else {
                    new ConfilmDialog().newInstance("Số điện thoại chưa tồn tại. Bạn có muốn tạo mới không ?", new CallbackConfilm() {
                        @Override
                        public void confilm() {
                            addCustomer(customer.getPhone_number());
                        }
                    }).show(getFragmentManager(), "");
                }
            }
        });

        receiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                String str = replaceCharact(intent.getStringExtra("data"));
                if (str != null && !str.equals("")) {
                    if (checkListPhone(str)) {
                        getDataCustomer(str);
                        stringListPhoneContact.add(str);
                    }

                }
                displayItemNavigation();
            }
        };

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.START);
            }
        });

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_PHONE_STATE},
                MY_PERMISSIONS_REQUEST_SMS_RECEIVE);

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,
                new IntentFilter(ServiceReceiver.NOTIFICATION));

    }

    private void displayItemNavigation() {

        if (listPhoneContact.size() == 0) {
            noItem.setVisibility(View.VISIBLE);
            listViewCommingCall.setVisibility(View.GONE);
        } else {
            noItem.setVisibility(View.GONE);
            listViewCommingCall.setVisibility(View.VISIBLE);
        }
    }

    private void addCustomer(String phone) {
        AddCustomer addCustomer = null;
        if (phone == null)
            addCustomer = new AddCustomer().newInstance(this, new CallBackObject() {
                @Override
                public void returnObject(Object object) {
                    customerList.add(0, (Customer) object);
                    adapterCustomer.notifyDataSetChanged();
                }
            });
        else
            addCustomer = new AddCustomer().newInstance(this, new CallBackObject() {
                @Override
                public void returnObject(Object object) {
                    customerList.add(0, (Customer) object);
                    adapterCustomer.notifyDataSetChanged();
                }
            }, phone);
        addCustomer.show(getFragmentManager(), "");

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
                            Customer customer = new Customer();
                            customer.setPhone_number(phone);
                            if (myObjects.size() != 0) {
                                customer.setDate(myObjects.get(0).getDate());
                                customer.setName(myObjects.get(0).getName());
                                customer.setAddress(myObjects.get(0).getAddress());
                                customer.setCmt(myObjects.get(0).getCmt());
                                customer.setLateDateItem(myObjects.get(0).getLateDateItem());
                            }
                            listPhoneContact.add(0,customer);
                            adapterContact.notifyDataSetChanged();

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
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
            mDrawerLayout.closeDrawers();
        else if (search.getText().toString().trim().equals("")) {
            super.onBackPressed();
        } else {
            search.setText("");
        }
    }

    private boolean checkListPhone(String string) {
        for (String phone : stringListPhoneContact) {
            if (string.equals(phone)) {
                return false;
            }
        }
        return true;
    }

    private String replaceCharact(String txt) {
        txt = txt.replace(" ", "");
        txt = txt.replace("+84", "0");
        return txt;
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
