package com.example.duyhung.app_android.module;

/**
 * Created by thetainguyen on 28/11/17.
 */

public class Result {

    private int status;
    private String result;

    public Result() {
    }

    public Result(int status, String result) {
        this.status = status;
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
