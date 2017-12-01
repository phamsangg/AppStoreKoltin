package com.example.duyhung.app_android.module;

/**
 * Created by thetainguyen on 30/11/17.
 */

public class Contact {

    private String phNum;
    private int callType;
    private Long callDate;
    private String callDuration;
    private String name;

    public Contact() {
    }

    public Contact(String phNum, int callType, Long callDate, String callDuration) {
        this.phNum = phNum;
        this.callType = callType;
        this.callDate = callDate;
        this.callDuration = callDuration;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhNum() {
        return phNum;
    }

    public void setPhNum(String phNum) {
        this.phNum = phNum;
    }

    public int getCallType() {
        return callType;
    }

    public void setCallType(int callType) {
        this.callType = callType;
    }

    public Long getCallDate() {
        return callDate;
    }

    public void setCallDate(Long callDate) {
        this.callDate = callDate;
    }

    public String getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }

}
