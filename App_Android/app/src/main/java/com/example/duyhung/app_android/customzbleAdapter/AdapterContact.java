package com.example.duyhung.app_android.customzbleAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duyhung.app_android.R;
import com.example.duyhung.app_android.module.Contact;
import com.example.duyhung.app_android.module.Customer;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.List;

import static com.example.duyhung.app_android.Config.ft;

/**
 * Created by thetainguyen on 30/11/17.
 */

public class AdapterContact extends ArrayAdapter<Contact> {

    private int resource;

    public AdapterContact(@NonNull Context context, int resource, @NonNull List<Contact> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @SuppressLint("NewApi")
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
            viewHolder.date= (TextView) convertView.findViewById(R.id.date_call);
            viewHolder.type = convertView.findViewById(R.id.type_call);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Contact contact = getItem(position);

        if (contact.getName() != null)
            viewHolder.name.setText(contact.getName());
        else
            viewHolder.name.setText(getContext().getResources().getText(R.string.not_name));

        viewHolder.phone_number.setText(contact.getPhNum());
        viewHolder.date.setText(ft.format(new Date(contact.getCallDate())));
        viewHolder.type.setImageResource(contact.getCallType());

        viewHolder.phone_number.setTextColor(getContext().getResources().getColor(R.color.white));
        viewHolder.name.setTextColor(getContext().getResources().getColor(R.color.white));
        viewHolder.date.setTextColor(getContext().getResources().getColor(R.color.white));

        return convertView;
    }

    class ViewHolder {
        TextView name;
        TextView phone_number;
        TextView date;
        ImageView type;
    }
}
