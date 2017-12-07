package com.example.duyhung.app_android.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
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
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duyhung.app_android.R;
import com.example.duyhung.app_android.callback.CallBackAction;
import com.example.duyhung.app_android.callback.CallBackListObject;
import com.example.duyhung.app_android.callback.CallbackConfilm;
import com.example.duyhung.app_android.conconler.Controler;
import com.example.duyhung.app_android.customzbleAdapter.AdapterContact;
import com.example.duyhung.app_android.customzbleAdapter.AdapterCustomer;
import com.example.duyhung.app_android.event.ServiceReceiver;
import com.example.duyhung.app_android.module.Contact;
import com.example.duyhung.app_android.module.Customer;
import com.example.duyhung.app_android.module.Transfer;
import com.example.duyhung.app_android.service.modules.Result;
import com.example.duyhung.app_android.service.AsyncReadCallLog;
import com.example.duyhung.app_android.service.modules.ResultContact;
import com.example.duyhung.app_android.view.dialog.ConfilmDialog;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.duyhung.app_android.Config.GET_CUSTOMER_PHONE;
import static com.example.duyhung.app_android.Config.GET_LAZYLOAD;
import static com.example.duyhung.app_android.Config.GET_RESULT_NEW_CUSTOMER;
import static com.example.duyhung.app_android.Config.GET_RESULT_NEW_TRANSFER;
import static com.example.duyhung.app_android.Config.GET_RESULT_PHONE;
import static com.example.duyhung.app_android.Config.GET_UPDATE;
import static com.example.duyhung.app_android.Config.LIMIT;
import static com.example.duyhung.app_android.Config.URL;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private EditText search;
    View viewLoadingFooter;
    private int prevItem = 0;
    private int prevItemCall = 0;

    private DrawerLayout mDrawerLayout;
    private ListView listViewCommingCall;
    private TextView noItem;
    private ImageView open;
    private FloatingActionButton fab;
    private ProgressDialog progressDialog;

    private AdapterCustomer adapterCustomer;
    private List<Customer> customerList;
    private List<Contact> listContact;
    private AdapterContact adapterContact;
    private List<String> stringListPhoneContactl;

    private BroadcastReceiver receiver;
    private int MY_PERMISSIONS_REQUEST_SMS_RECEIVE = 10;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private static final int REQUEST_CODE_FOR_RESULT = 1;
    private long timegetNewContact = Long.parseLong(String.valueOf(System.currentTimeMillis()));
    private long timeLoadOLdContact = Long.parseLong(String.valueOf(System.currentTimeMillis()));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout_main_ativity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();
        displayItemNavigation();
        registerEvent();
        registerService();
        readHistoryCall(GET_LAZYLOAD,prevItemCall);

        getData(LIMIT, 0, search.getText().toString().trim());

    }

    private void init() {

        fab = findViewById(R.id.fab);
        listView = (ListView) findViewById(R.id.lvCustomer);
        open = findViewById(R.id.open_navigation);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listViewCommingCall = findViewById(R.id.list_comming_call);
        noItem = findViewById(R.id.txt_no_item);
        search = findViewById(R.id.search);

        customerList = new ArrayList<>();
        listContact = new ArrayList<>();
        stringListPhoneContactl = new ArrayList<>();

        adapterCustomer = new AdapterCustomer(this, R.layout.list_item_customer, customerList);
        adapterContact = new AdapterContact(this, R.layout.item_contact, listContact);

        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        viewLoadingFooter = inflater.inflate(R.layout.layout_loading, null);
        listView.addFooterView(viewLoadingFooter, null, false);

        listViewCommingCall.setAdapter(adapterContact);
        listView.setAdapter(adapterCustomer);

    }

    private void registerEvent() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ScreenSlidePagerActivity.class);
                startActivityForResult(i, REQUEST_CODE_FOR_RESULT);
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

                final Contact contact = (Contact) adapterView.getItemAtPosition(i);
                mDrawerLayout.closeDrawers();
                if (contact.getName() != null) {
                    getDataCustomer(contact.getPhNum());
                } else {
                    new ConfilmDialog().newInstance("Số điện thoại chưa tồn tại. Bạn có muốn tạo mới không ?", new CallbackConfilm() {
                        @Override
                        public void confilm() {
                            Intent intent = new Intent(MainActivity.this, ScreenSlidePagerActivity.class);
                            intent.putExtra(GET_RESULT_PHONE, contact.getPhNum());
                            startActivityForResult(intent, REQUEST_CODE_FOR_RESULT);
                        }
                    }).show(getFragmentManager(), "");
                }
            }
        });

        listViewCommingCall.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                switch (view.getId()) {
                    case R.id.list_comming_call:

                        final int lastItem = firstVisibleItem + visibleItemCount;

                        if (lastItem == totalItemCount) {

                            if (firstVisibleItem != 0 && prevItemCall != lastItem) {
                                readHistoryCall(GET_LAZYLOAD, lastItem);
                            }
                        }
                        break;
                }
            }
        });

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.START);
            }
        });

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void registerService() {

        receiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                String str = replaceCharact(intent.getStringExtra("data"));
                if (str != null && !str.equals("")) {
                    if (checkListPhone(str)) {
                        getDataCustomer(str);
                        stringListPhoneContactl.add(str);
                    }

                }
                displayItemNavigation();
            }
        };

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_PHONE_STATE},
                MY_PERMISSIONS_REQUEST_SMS_RECEIVE);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_CALL_LOG},
                PERMISSIONS_REQUEST_READ_CONTACTS);


        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,
                new IntentFilter(ServiceReceiver.NOTIFICATION));

    }

    private void displayItemNavigation() {

        if (listContact.size() == 0) {
            noItem.setVisibility(View.VISIBLE);
            listViewCommingCall.setVisibility(View.GONE);
        } else {
            noItem.setVisibility(View.GONE);
            listViewCommingCall.setVisibility(View.VISIBLE);
        }
    }

    private void notifyListContact(String name, String phone) {
        for (Contact contact : listContact) {
            if (contact.getPhNum().equals(phone))
                contact.setName(name);
        }
        adapterContact.notifyDataSetChanged();
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
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
            mDrawerLayout.closeDrawers();
        else if (search.getText().toString().trim().equals("")) {
            new ConfilmDialog().newInstance("Bạn có muốn thoát không", new CallbackConfilm() {
                @Override
                public void confilm() {
                    finish();
                }
            }).show(getFragmentManager(), "tag");

        } else {
            search.setText("");
        }
    }

    private boolean checkListPhone(String string) {
        for (String phone : stringListPhoneContactl) {
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

    private void readHistoryCall(final String type, final int lastItem) {
        Cursor managedCursor = getCursor(type);
        new AsyncReadCallLog(managedCursor, this, new CallBackListObject() {
            @Override
            public void getListObject(List list) {
                if (type.equals(GET_UPDATE))
                    listContact.addAll(0, list);
                else {
                    listContact.addAll(list);
                    Contact contact = (Contact) list.get(list.size() - 1);
                    timeLoadOLdContact = contact.getCallDate();
                }
                getListName(list);
                prevItemCall = lastItem;
                adapterContact.notifyDataSetChanged();
                displayItemNavigation();
            }
        }).execute();

    }

    private void getListName(final List<Contact> contactList) {

        List<String> stringList = new ArrayList<>();
        for (Contact contact : contactList) {
            stringList.add(contact.getPhNum());
        }
        Controler controler = new Controler(this, URL);
        controler.getListName(new CallBackAction() {
            @Override
            public void excute(Result result) {
                View view = getWindow().getDecorView().findViewById(android.R.id.content);
                if (result != null) {
                    if (result.getStatus() == 200) {
                        try {
                            Gson gson = new Gson();
                            ResultContact[] object = gson.fromJson(result.getResult(), ResultContact[].class);
                            List<ResultContact> myObjects = new ArrayList<>(Arrays.asList(object));

                            for (int i = prevItemCall; i < listContact.size(); i++) {
                                for (ResultContact myObject : myObjects) {
                                    if (contactList.get(i).getPhNum().equals(myObject.getPhone())) {
                                        contactList.get(i).setName(myObject.getName());
                                        break;
                                    }
                                }
                            }

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
        }, stringList);
        return;
    }

    private Cursor getCursor(String type) {
        String WHERE = null;
        String WHERE_CONDITION = null;
        String ORDER_BY = " date DESC";

        if (type.equals(GET_UPDATE)) {
            WHERE = "date > ?";
            WHERE_CONDITION = String.valueOf(this.timegetNewContact);
        } else {
            WHERE = "date < ?";
            ORDER_BY += " limit " + LIMIT;
            WHERE_CONDITION = String.valueOf(this.timeLoadOLdContact);
        }


        Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null,
                WHERE, new String[]{WHERE_CONDITION},
                ORDER_BY);
        return managedCursor;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_FOR_RESULT) {
            try {
                Customer customer = (Customer) data.getExtras().getSerializable(GET_RESULT_NEW_CUSTOMER);
                addCustomer(customer, null);
            } catch (NullPointerException e) {
            }

            try {
                Transfer transfer = (Transfer) data.getExtras().getSerializable(GET_RESULT_NEW_TRANSFER);
                String phone = data.getStringExtra(GET_CUSTOMER_PHONE);
                addCustomer(getCustomer(phone), transfer);

            } catch (NullPointerException e) {
            }

        }


    }

    private void addCustomer(final Customer customer, final Transfer transfer) {
        showDialog();
        Controler controler = new Controler(this, URL);
        controler.addCustomer(new CallBackAction() {
            @Override
            public void excute(Result result) {
                if (result != null) {
                    if (result.getStatus() == 200) {
                        Toast.makeText(getBaseContext(), "create successfully", Toast.LENGTH_SHORT).show();
                        if (transfer != null) {
                            addTransfer(transfer, customer.getPhone_number());
                        }
                        customerList.add(0, customer);
                        adapterCustomer.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getBaseContext(), "create fail", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getBaseContext(), "create fail", Toast.LENGTH_SHORT).show();
                }
                hideDialog();
            }
        }, customer);
    }

    private void addTransfer(Transfer transfer, String phoneNumber) {
        Controler controler = new Controler(this, URL);
        controler.addTrasfer(new CallBackAction() {

            @Override
            public void excute(Result result) {
                if (result != null) {
                    if (result.getStatus() == 200) {

                        Toast.makeText(getBaseContext(), "create successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getBaseContext(), "create fail", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getBaseContext(), "create fail", Toast.LENGTH_SHORT).show();
                }
                hideDialog();
            }
        }, transfer, phoneNumber);
    }

    private Customer getCustomer(String phone) {
        Customer customer = new Customer();
        customer.setPhone_number(phone);
        return customer;
    }


    private void showDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        if (!progressDialog.isShowing()) {
            progressDialog.setMessage("creating...");
            progressDialog.show();
        }

    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }

}
