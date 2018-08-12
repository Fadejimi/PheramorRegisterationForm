package com.pheramor.registerationapp.view_interfaces;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;

public interface SummaryActivityInterface {
    SummaryPresenterInterface getPresenter();
    Context getContext();
    FragmentManager getSupportFragmentManager();
    void startActivity(Intent intent);
}
