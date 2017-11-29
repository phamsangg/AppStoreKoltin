package com.example.duyhung.app_android;

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

    int TIMEOUT_MILLISEC = 30000;  // = 10 seconds
    int LIMIT = 20;
}
