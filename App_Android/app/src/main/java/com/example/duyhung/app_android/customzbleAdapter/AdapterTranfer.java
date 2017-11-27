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
import com.example.duyhung.app_android.module.Transfer;

import java.util.List;

/**
 * Created by thetainguyen on 26/11/17.
 */

public class AdapterTranfer extends ArrayAdapter<Transfer> {

    private int resource;

    public AdapterTranfer(@NonNull Context context, int resource, @NonNull List<Transfer> objects) {
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
            viewHolder.nameProduct = (TextView) convertView.findViewById(R.id.name_product);
            viewHolder.money = (TextView) convertView.findViewById(R.id.money);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Transfer transfer  = getItem(position);

        viewHolder.nameProduct.setText(transfer.getItem());
        viewHolder.date.setText(transfer.getDate_transfer().toString());
        viewHolder.money.setText(transfer.getMoney());

        return convertView;
    }

    class ViewHolder{
        TextView nameProduct ;
        TextView date;
        TextView money;
    }
}
