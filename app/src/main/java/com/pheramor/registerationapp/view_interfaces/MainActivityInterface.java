package com.pheramor.registerationapp.view_interfaces;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.FragmentManager;

import java.io.File;

public interface MainActivityInterface {
    MainPresenterInterface getMainPresenter();
    void startActivity(Intent intent);
    void startActivityForResult(Intent intent, int requestCode);
    ContentResolver getContentResolver();
    FragmentManager getSupportFragmentManager();
    void showToast(String message);
    Context getContext();
    PackageManager getPackageManager();
}
