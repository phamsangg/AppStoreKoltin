package com.example.duyhung.app_android.view;

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
import com.example.duyhung.app_android.customzbleAdapter.AdapterCustomer;
import com.example.duyhung.app_android.module.Customer;
import com.example.duyhung.app_android.view.dialog.AddCustomer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private AdapterCustomer adapterCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        listView = (ListView) findViewById(R.id.lvCustomer);

        List<Customer> customerList = new ArrayList<>();
        customerList.addAll(getList());

        adapterCustomer = new AdapterCustomer(this, R.layout.list_item_customer, customerList);
        listView.setAdapter(adapterCustomer);

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
                Intent nextActivity = new Intent(MainActivity.this,ActivityTransfer.class);

                nextActivity.putExtra("cutomer", (Serializable) customer);
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

    private List<Customer> getList() {
        List<Customer> customerList = new ArrayList<>();
        Customer customer = new Customer("01698499931", "nguyen the tai", "thanh hoa", "174511915");
        Customer customer1 = new Customer("01698499931", "nguyen the tai", "thanh hoa", "174511915");
        Customer customer2 = new Customer("01698499931", "nguyen the tai", "thanh hoa", "174511915");
        Customer customer3 = new Customer("01698499931", "nguyen the tai", "thanh hoa", "174511915");
        Customer customer4 = new Customer("01698499931", "nguyen the tai", "thanh hoa", "174511915");
        customerList.add(customer);
        customerList.add(customer1);
        customerList.add(customer2);
        customerList.add(customer3);
        customerList.add(customer4);
        return customerList;
    }
}
