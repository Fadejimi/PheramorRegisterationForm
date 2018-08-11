package com.pheramor.registerationapp.view_interfaces;

import com.pheramor.registerationapp.retrofit.models.User;

public interface SecondRegisterationInterface {
    String getAgeValue(int value, int thumbIndex, String ageText);
    String setValid(String gender, boolean men, boolean female, int minAge, int maxAge, String zipCode);
    void sendData(String gender, boolean men, boolean female, int minAge, int maxAge, String zipCode);
    User getUser();
}
