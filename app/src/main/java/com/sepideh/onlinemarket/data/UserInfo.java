
package com.sepideh.onlinemarket.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserInfo implements Serializable{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_name")
    @Expose
    private String userName;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("family")
    @Expose
    private String family;

    @SerializedName("tell")
    @Expose
    private String tell;

    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;

    @SerializedName("jensiat")
    @Expose
    private String jensiat;

    @SerializedName("password")
    @Expose
    private String password;


    public String getId() {
        return id;
    }

    public UserInfo setId(String id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserInfo setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getFamily() {
        return family;
    }

    public UserInfo setFamily(String family) {
        this.family = family;
        return this;
    }

    public String getTell() {
        return tell;
    }

    public UserInfo setTell(String tell) {
        this.tell = tell;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserInfo setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getJensiat() {
        return jensiat;
    }

    public UserInfo setJensiat(String jensiat) {
        this.jensiat = jensiat;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserInfo setPassword(String password) {
        this.password = password;
        return this;
    }


    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", family='" + family + '\'' +
                ", tell='" + tell + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", jensiat='" + jensiat + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
