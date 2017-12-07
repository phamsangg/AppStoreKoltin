package com.example.duyhung.app_android;

import java.text.SimpleDateFormat;

/**
 * Created by thetainguyen on 26/11/17.
 */

public interface Config {

//    String URL = "http://192.168.1.241:8080/accumulat";
    String URL = "http://192.168.0.104:8080/accumulat";

    String GET_CUSTOMER = "/get/customer";
    String GET_TRANSFER = "/get/transfer";
    String INSERT_TRANSFER = "/insert/transfer";
    String INSERT_CUSTOMER = "/insert/customer";
    String GET_SUM = "/get/transfer/sum";
    String GET_CUSTOMER_PHONE = "/get/customer/phone";
    String GET_CUSTOMER_LIST_NAME = "/get/customer/listname";
    String GET_UPDATE = "UPDATE";
    String GET_LAZYLOAD = "LAZYLOAD";
    String GET_RESULT_NEW_TRANSFER = "NEWTRANSFER";
    String GET_RESULT_PHONE = "PHONENUMBERTRANSFER";
    String GET_RESULT_NEW_CUSTOMER = "NEWCUSTOMER";

    int TIMEOUT_MILLISEC = 30000;  // = 10 seconds
    int LIMIT = 20;

    SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy  hh:mm:ss");
}
