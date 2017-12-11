package com.example.duyhung.app_android.module;

/**
 * Created by thetainguyen on 10/12/17.
 */

public class CustomerAutocomplete {
    private String name;
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public CustomerAutocomplete(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
