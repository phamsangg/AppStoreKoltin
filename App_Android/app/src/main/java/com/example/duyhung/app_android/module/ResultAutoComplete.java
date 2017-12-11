package com.example.duyhung.app_android.module;

import java.util.List;

/**
 * Created by thetainguyen on 11/12/17.
 */

public class ResultAutoComplete {

    private List<CustomerAutocomplete>  customerAutocompleteList;

    public ResultAutoComplete(List<CustomerAutocomplete> customerAutocompleteList) {
        this.customerAutocompleteList = customerAutocompleteList;
    }

    public List<CustomerAutocomplete> getCustomerAutocompleteList() {
        return customerAutocompleteList;
    }

    public void setCustomerAutocompleteList(List<CustomerAutocomplete> customerAutocompleteList) {
        this.customerAutocompleteList = customerAutocompleteList;
    }
}
