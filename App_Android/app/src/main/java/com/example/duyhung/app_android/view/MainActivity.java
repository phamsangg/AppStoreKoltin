package com.example.duyhung.app_android.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.duyhung.app_android.R;
import com.example.duyhung.app_android.callback.CallBackGetListCustomer;
import com.example.duyhung.app_android.conconler.Controler;
import com.example.duyhung.app_android.customzbleAdapter.AdapterCustomer;
import com.example.duyhung.app_android.module.Customer;
import com.example.duyhung.app_android.view.dialog.AddCustomer;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.duyhung.app_android.Config.URL;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private AdapterCustomer adapterCustomer;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        listView = (ListView) findViewById(R.id.lvCustomer);

        final List<Customer> customerList = new ArrayList<>();
        adapterCustomer = new AdapterCustomer(this, R.layout.list_item_customer, customerList);
        listView.setAdapter(adapterCustomer);

        showDialog();

        Controler controler = new Controler(this, URL);
        controler.getListCustomer(50, 0, new CallBackGetListCustomer() {
            @Override
            public void excute(String data) {
                try {
                    Gson gson = new Gson();
                    Customer[] object = gson.fromJson(data, Customer[].class);
                    List<Customer> myObjects = new ArrayList<>(Arrays.asList(object));
                    customerList.addAll(myObjects);
                    adapterCustomer.notifyDataSetChanged();
                    hideDialog();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });


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
                Intent nextActivity = new Intent(MainActivity.this, ActivityTransfer.class);

                nextActivity.putExtra("customer", (Serializable) customer);
                startActivity(nextActivity);
            }
        });
    }

    private void addCustomer() {
        new AddCustomer().show(getFragmentManager(), "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void showDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        if (!progressDialog.isShowing()) {
            progressDialog.setMessage("watting...");
            progressDialog.show();
        }

    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }


}
