package com.strang6.tinkofffintechschool;

import com.google.gson.annotations.Expose;

/**
 * Created by Strang6 on 05.11.2017.
 */

public class User {
    @Expose
    private String firstName, lastName;
    private String phone;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String firstName, String lastName, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
