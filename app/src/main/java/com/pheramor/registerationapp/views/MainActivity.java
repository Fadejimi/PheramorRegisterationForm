package com.pheramor.registerationapp.views;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.pheramor.registerationapp.R;
import com.pheramor.registerationapp.presenters.MainPresenter;
import com.pheramor.registerationapp.utils.PreferenceUtil;
import com.pheramor.registerationapp.view_interfaces.MainActivityInterface;
import com.pheramor.registerationapp.view_interfaces.MainPresenterInterface;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity implements MainActivityInterface{
    private MainPresenterInterface presenterInterface;
    private static final int MY_PERMISSIONS_REQUEST_READ_MEDIA = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenterInterface = new MainPresenter(this);

        checkStoragePermission();
    }

    private void checkStoragePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_MEDIA);
        } else {
            presenterInterface.setFirstForm();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_MEDIA:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    presenterInterface.setFirstForm();
                }
                break;

            default:
                break;
        }
    }
    @Override
    public MainPresenterInterface getMainPresenter() {
        return presenterInterface;
    }

    @Override
    public void showToast(String message) {
        Toasty.error(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences preferences = this.getSharedPreferences(PreferenceUtil.PREF, MODE_PRIVATE);
        PreferenceUtil.removeUser(preferences);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenterInterface.onActivityResult(MainActivity.this, requestCode, resultCode, data);
    }
}
