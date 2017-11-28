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

import java.text.SimpleDateFormat;
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

        Transfer transfer = getItem(position);

        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy  hh:mm:ss");

        viewHolder.nameProduct.setText(transfer.getItem());
        viewHolder.date.setText(ft.format(transfer.getDate_transfer()).toString());
        viewHolder.money.setText(printMoney(transfer.getMoney() + ""));

        return convertView;
    }

    private String printMoney(String moneys) {
        StringBuilder builder = new StringBuilder();
        int leng = moneys.length();
        int begin = leng % 3;
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


    class ViewHolder {
        TextView nameProduct;
        TextView date;
        TextView money;
    }
}
