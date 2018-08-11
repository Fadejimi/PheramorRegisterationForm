package com.pheramor.registerationapp.view_interfaces;

import android.text.TextWatcher;
import android.view.View;

import com.pheramor.registerationapp.retrofit.models.User;

public interface FirstRegisterationInterface {

    String setFeet(String height, String feetString);
    String setInches(String height, String inchesString);
    String setValid(String name, String email, String password, String confirm,
                           String race, String religion, String height, String dob);
    void setData(String name, String email, String password, String confirm,
                  String race, String religion, String height, String feet, String inches, String dob);

    User getUser();
}
