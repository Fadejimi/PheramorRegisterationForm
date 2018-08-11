package com.pheramor.registerationapp.view_interfaces;

import android.content.ContentResolver;
import android.content.Intent;
import android.support.v4.app.FragmentManager;

public interface MainActivityInterface {
    MainPresenterInterface getMainPresenter();
    void startActivity(Intent intent);
    void startActivityForResult(Intent intent, int requestCode);
    ContentResolver getContentResolver();
    FragmentManager getSupportFragmentManager();
    void showToast(String message);
}
