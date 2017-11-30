package com.example.duyhung.app_android.customzbleAdapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.duyhung.app_android.R;
import com.example.duyhung.app_android.module.Contact;
import com.example.duyhung.app_android.module.Customer;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by thetainguyen on 30/11/17.
 */

public class AdapterContact extends ArrayAdapter<Customer> {

    private int resource;

    public AdapterContact(@NonNull Context context, int resource, @NonNull List<Customer> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, null);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name_contact);
            viewHolder.phone_number = (TextView) convertView.findViewById(R.id.number_contact);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Customer customer = getItem(position);

        if (customer.getName() != null)
            viewHolder.name.setText(customer.getName());
        else
            viewHolder.name.setText(getContext().getResources().getText(R.string.not_name));
        if (customer.getPhone_number() != null)
            viewHolder.phone_number.setText(customer.getPhone_number());

        return convertView;
    }

    class ViewHolder {
        TextView name;
        TextView phone_number;
    }
}
