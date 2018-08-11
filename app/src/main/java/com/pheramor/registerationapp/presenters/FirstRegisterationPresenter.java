package com.pheramor.registerationapp.presenters;

import android.content.Context;
import android.content.SharedPreferences;

import com.pheramor.registerationapp.retrofit.models.User;
import com.pheramor.registerationapp.utils.PreferenceUtil;
import com.pheramor.registerationapp.view_interfaces.FirstFragmentInterface;
import com.pheramor.registerationapp.view_interfaces.FirstRegisterationInterface;
import com.pheramor.registerationapp.views.FirstRegisterationFragment;

public class FirstRegisterationPresenter implements FirstRegisterationInterface {
    private FirstFragmentInterface fragment;
    public FirstRegisterationPresenter(FirstFragmentInterface fragment) {
        this.fragment = fragment;
    }

    @Override
    public String setFeet(String height, String feetString) {
        if (!height.contains("\'")) {
            height = feetString;
        }
        else {
            String [] heights = height.split("\'");
            height = feetString + "\'" + heights[1];
        }
        return height;
    }

    @Override
    public String setInches(String height, String inchesString) {
        if (!height.contains("\'")) {
            if (height.isEmpty()) {
                height = "0";
            }
            height = height + "\'" + inchesString;
        }
        else {
            String [] heights = height.split("\'");
            height = heights[0] + "\'" + inchesString;
        }
        return height;
    }

    @Override
    public String setValid(String name, String email, String password, String confirm, String race,
                           String religion, String height, String dob) {
        String errorString = "";
        if (name.isEmpty()) errorString = "Please enter the name";
        if (email.isEmpty()) errorString = "Please enter your email";
        if (password.isEmpty()) errorString = "Please enter your password";
        if (!password.equals(confirm)) errorString = "Please ensure password is the same as confirm password";
        if (race.isEmpty()) errorString = "Please enter the race";
        if (religion.isEmpty()) errorString = "Please enter the religion";
        if (height.isEmpty()) errorString = "Please enter your height";
        if (dob.isEmpty()) errorString = "Please enter your date of birth";

        return errorString;
    }

    @Override
    public void setData(String name, String email, String password, String confirm, String race,
                        String religion, String height, String feet, String inches, String dob) {
        User user = new User();
        user.setFullName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRace(race);
        user.setReligion(religion);
        user.setInches_height(Integer.parseInt(inches));
        user.setFeet_height(Integer.parseInt(feet));
        user.setDob(dob);

        SharedPreferences preferences = ((FirstRegisterationFragment) fragment).getActivity().getSharedPreferences(
                PreferenceUtil.PREF, Context.MODE_PRIVATE);
        PreferenceUtil.putUser(preferences, PreferenceUtil.FIRST_FORM, user);
    }

    @Override
    public User getUser() {
        SharedPreferences preferences = ((FirstRegisterationFragment) fragment).getActivity().getSharedPreferences(
                PreferenceUtil.PREF, Context.MODE_PRIVATE);
        return PreferenceUtil.getUser(preferences);
    }
}
