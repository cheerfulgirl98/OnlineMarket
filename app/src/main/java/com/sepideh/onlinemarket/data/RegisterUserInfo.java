package com.sepideh.onlinemarket.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by pc on 5/3/2019.
 */

public class RegisterUserInfo implements Serializable {

    @SerializedName("user_name")
    private String userName;

    @SerializedName("phone_number")
    private String phoneNumber;

    @SerializedName("password")
    private String password;


    public String getUserName() {
        return userName;
    }

    public RegisterUserInfo setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public RegisterUserInfo setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterUserInfo setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "RegisterUserInfo{" +
                "userName='" + userName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
