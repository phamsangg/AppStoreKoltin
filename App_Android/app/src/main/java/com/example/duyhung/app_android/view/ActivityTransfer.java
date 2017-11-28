package com.example.duyhung.app_android.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.duyhung.app_android.R;
import com.example.duyhung.app_android.callback.CallBackAction;
import com.example.duyhung.app_android.conconler.Controler;
import com.example.duyhung.app_android.customzbleAdapter.AdapterTranfer;
import com.example.duyhung.app_android.module.Customer;
import com.example.duyhung.app_android.module.Transfer;
import com.example.duyhung.app_android.module.Result;
import com.example.duyhung.app_android.view.dialog.AddTransfer;
import com.google.gson.Gson;

import static com.example.duyhung.app_android.Config.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActivityTransfer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private AdapterTranfer adapterTranfer;
    private ListView listView;
    private Customer customer;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        customer = (Customer) getIntent().getSerializableExtra("customer");

        final List<Transfer> transferList = new ArrayList<>();
        ListView listView = (ListView) findViewById(R.id.list_transfer);
        adapterTranfer = new AdapterTranfer(this, R.layout.list_item_transfer, transferList);
        listView.setAdapter(adapterTranfer);
        showDialog();
        Controler controler = new Controler(this, URL);
        controler.getListTransfer(10, 0, customer.getPhone_number(), new CallBackAction() {

            @Override
            public void excute(Result result) {
                if (result != null) {
                    if (result.getStatus() == 200) {
                        try {
                            Gson gson = new Gson();
                            Transfer[] object = gson.fromJson(result.getResult(), Transfer[].class);
                            List<Transfer> myObjects = new ArrayList<>(Arrays.asList(object));
                            transferList.addAll(myObjects);
                            adapterTranfer.notifyDataSetChanged();
                            hideDialog();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else {
                        hideDialog();
                        Toast.makeText(ActivityTransfer.this, "Get data fail", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    hideDialog();
                    Toast.makeText(ActivityTransfer.this, "Get data fail", Toast.LENGTH_SHORT).show();
                }

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newTransfer();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void newTransfer() {
        new AddTransfer().newInstance(customer.getPhone_number(), this).show(getFragmentManager(), "");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        if (!progressDialog.isShowing()) {
            progressDialog.setMessage("Loadding...");
            progressDialog.show();
        }

    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }
}
