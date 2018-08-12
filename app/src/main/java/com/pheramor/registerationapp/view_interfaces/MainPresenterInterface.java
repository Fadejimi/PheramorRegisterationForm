package com.pheramor.registerationapp.view_interfaces;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public interface MainPresenterInterface {
    void setFirstForm();
    void setSecondForm();
    void setThirdForm();
    void sendToSummary();
    void onActivityResult(Context context, int requestCode, int resultCode, Intent intent);
    void getImageFromGallery();
}
