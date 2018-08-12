package com.pheramor.registerationapp.retrofit.models;

import java.util.List;

public class User {
    private String email;
    private String password;
    private String fullName;
    private String gender;
    private String zipCode;
    private int feet_height;
    private int inches_height;
    private List<String> genderInterest;
    private String dob;
    private String race;
    private String religion;
    private int min_range;
    private int max_range;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public int getFeet_height() {
        return feet_height;
    }

    public void setFeet_height(int feet_height) {
        this.feet_height = feet_height;
    }

    public int getInches_height() {
        return inches_height;
    }

    public void setInches_height(int inches_height) {
        this.inches_height = inches_height;
    }

    public List<String> getGenderInterest() {
        return genderInterest;
    }

    public void setGenderInterest(List<String> genderInterest) {
        this.genderInterest = genderInterest;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public int getMin_range() {
        return min_range;
    }

    public void setMin_range(int min_range) {
        this.min_range = min_range;
    }

    public int getMax_range() {
        return max_range;
    }

    public void setMax_range(int max_range) {
        this.max_range = max_range;
    }
}
