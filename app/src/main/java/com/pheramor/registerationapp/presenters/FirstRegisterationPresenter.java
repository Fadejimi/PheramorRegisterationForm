package com.pheramor.registerationapp.presenters;

import android.content.Context;
import android.content.SharedPreferences;

import com.pheramor.registerationapp.retrofit.models.User;
import com.pheramor.registerationapp.utils.PreferenceUtil;
import com.pheramor.registerationapp.view_interfaces.FirstFragmentInterface;
import com.pheramor.registerationapp.view_interfaces.FirstRegisterationInterface;
import com.pheramor.registerationapp.views.FirstRegisterationFragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FirstRegisterationPresenter implements FirstRegisterationInterface {
    private FirstFragmentInterface fragment;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

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
        if (name != null && name.isEmpty()) return "Please enter the name";
        if (email != null && email.isEmpty()) return "Please enter your email";
        if (email != null && !validateEmail(email)) return "Email is not valid";
        if (password != null && password.isEmpty()) return "Please enter your password";
        if (password != null && !password.equals(confirm)) return "Please ensure password is the same as confirm password";
        if (race != null && race.isEmpty()) return "Please enter the race";
        if (religion != null && religion.isEmpty()) return "Please enter the religion";
        if (height != null && height.isEmpty()) return "Please enter your height";
        if (dob != null && dob.isEmpty()) return "Please enter your date of birth";

        return "";
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

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    @Override
    public User getUser() {
        SharedPreferences preferences = ((FirstRegisterationFragment) fragment).getActivity().getSharedPreferences(
                PreferenceUtil.PREF, Context.MODE_PRIVATE);
        return PreferenceUtil.getUser(preferences);
    }
}
