package com.sepideh.onlinemarket.data;

public class Customer {

    private String name;
    private String family;
    private String mobile;
    private String address;

    public Customer() {
    }

    public String getName() {
        return name;
    }

    public Customer setName(String name) {
        this.name = name;
        return this;
    }

    public String getFamily() {
        return family;
    }

    public Customer setFamily(String family) {
        this.family = family;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public Customer setAddress(String address) {
        this.address = address;
        return this;
    }
}
