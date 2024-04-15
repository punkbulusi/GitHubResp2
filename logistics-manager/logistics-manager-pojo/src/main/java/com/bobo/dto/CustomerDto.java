package com.bobo.dto;

import com.bobo.pojo.Customer;

public class CustomerDto extends Customer {
    //业务员名称
    private String userName;
    //常用区间名称
    private String intervalName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIntervalName() {
        return intervalName;
    }

    public void setIntervalName(String intervalName) {
        this.intervalName = intervalName;
    }
}
