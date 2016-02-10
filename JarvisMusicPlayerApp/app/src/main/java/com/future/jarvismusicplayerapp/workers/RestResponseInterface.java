package com.future.jarvismusicplayerapp.workers;

/**
 * Created by arungu on 2/4/2016.
 */
public interface RestResponseInterface {
    public String SUCCESS = "SUCCESS";
    public String FAILURE = "FAILURE";
    public void responseReceived(String responseCode, String data);

}
