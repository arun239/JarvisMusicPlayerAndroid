package com.future.jarvismusicplayerapp.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by arungu on 2/3/2016.
 */
public class UserRegistrationPojo {

    @JsonProperty("userName")
    private String userName;
    private String userEmail;

    public UserRegistrationPojo() {
    }

    public UserRegistrationPojo(String userName, String userEmail) {
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
