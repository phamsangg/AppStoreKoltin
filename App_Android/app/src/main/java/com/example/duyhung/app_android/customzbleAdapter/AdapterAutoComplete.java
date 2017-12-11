package com.example.duyhung.app_android.customzbleAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.duyhung.app_android.R;
import com.example.duyhung.app_android.module.CustomerAutocomplete;
import com.example.duyhung.app_android.module.ResultAutoComplete;
import com.example.duyhung.app_android.service.Search;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.example.duyhung.app_android.Config.URL;

/**
 * Created by thetainguyen on 10/12/17.
 */

public class AdapterAutoComplete extends BaseAdapter implements Filterable {

    List<CustomerAutocomplete> customerAutocompleteList;
    Context context;
    List<CustomerAutocomplete> customerAutocompletes = new ArrayList<>();
    List<String> searchHistory = new ArrayList<>();

    public AdapterAutoComplete(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public CustomerAutocomplete getItem(int position) {
        return customerAutocompleteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return customerAutocompleteList.size();
    }

    @SuppressLint("NewApi")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_view_customer_autocomplete, null);
        }

        TextView phoneNumber = (TextView) convertView.findViewById(R.id.phone_number);
        CustomerAutocomplete customerAutocomplete = getItem(position);
        phoneNumber.setText(customerAutocomplete.getPhoneNumber());

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if (charSequence != null) {
                    if (checkSearch(charSequence.toString())) {
                        searchHistory.add(charSequence.toString().toLowerCase().trim());
                        String content = "%" + charSequence.toString() + "%";
                        Search search = new Search(URL);
                        List<CustomerAutocomplete> list = search.getDataCustomer(20, content);
                        saveCache(list);
                        if (list != null) {
                            filterResults.values = list;
                            filterResults.count = list.size();
                        }
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filterResults != null && filterResults.count > 0) {

                    saveCache((List<CustomerAutocomplete>) filterResults.values);
                    customerAutocompleteList = customerAutocompletes;

                    notifyDataSetChanged();
                    EventBus.getDefault().post(new ResultAutoComplete(customerAutocompleteList));
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }

    private void saveCache(List<CustomerAutocomplete> list) {
        for (CustomerAutocomplete customerAutocomplete : list) {
            if (check(customerAutocomplete.getPhoneNumber()))
                customerAutocompletes.add(customerAutocomplete);
        }
    }

    private boolean check(String phone) {
        for (CustomerAutocomplete customerAutocomplete : customerAutocompletes) {
            if (phone.equals(customerAutocomplete.getPhoneNumber()))
                return false;
        }
        return true;
    }

    private boolean checkSearch(String string) {
        for (String s : searchHistory) {
            if (string.trim().toLowerCase().equals(s))
                return false;
        }
        return true;
    }
}
