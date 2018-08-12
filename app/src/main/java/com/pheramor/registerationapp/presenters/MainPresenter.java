package com.pheramor.registerationapp.presenters;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.pheramor.registerationapp.R;
import com.pheramor.registerationapp.utils.ImageUtil;
import com.pheramor.registerationapp.view_interfaces.MainActivityInterface;
import com.pheramor.registerationapp.view_interfaces.MainPresenterInterface;
import com.pheramor.registerationapp.views.FirstRegisterationFragment;
import com.pheramor.registerationapp.views.MainActivity;
import com.pheramor.registerationapp.views.SecondRegisterationFragment;
import com.pheramor.registerationapp.views.SummaryActivity;
import com.pheramor.registerationapp.views.ThirdRegisterationFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class MainPresenter implements MainPresenterInterface {
    private MainActivityInterface activityInterface;
    public static final String FIRST_FRAGMENT_TAG = "first";
    public static final String SECOND_FRAGMENT_TAG = "second";
    public static final String THIRD_FRAGMENT_TAG = "third";
    private static final int PICK_IMAGE = 1;
    private Bitmap bitmap;

    public MainPresenter(MainActivityInterface activityInterface) {
        this.activityInterface = activityInterface;
    }

    @Override
    public void setFirstForm() {
        Fragment fragment = new FirstRegisterationFragment();
        FragmentTransaction transaction = activityInterface.getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.container, fragment, FIRST_FRAGMENT_TAG);
        transaction.commit();
    }

    @Override
    public void setSecondForm() {
        Fragment fragment = new SecondRegisterationFragment();
        FragmentTransaction transaction = activityInterface.getSupportFragmentManager()
                .beginTransaction();
        transaction.setCustomAnimations(R.animator.fragment_slide_left_enter,
                R.animator.fragment_slide_left_exit,
                R.animator.fragment_slide_right_enter,
                R.animator.fragment_slide_right_exit);
        transaction.replace(R.id.container, fragment, SECOND_FRAGMENT_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void setThirdForm() {
        Fragment fragment = new ThirdRegisterationFragment();
        FragmentTransaction transaction = activityInterface.getSupportFragmentManager()
                .beginTransaction();
        transaction.setCustomAnimations(R.animator.fragment_slide_left_enter,
                R.animator.fragment_slide_left_exit,
                R.animator.fragment_slide_right_enter,
                R.animator.fragment_slide_right_exit);
        transaction.replace(R.id.container, fragment, THIRD_FRAGMENT_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void sendToSummary() {
        Intent intent = new Intent(((MainActivity) activityInterface), SummaryActivity.class);
        activityInterface.startActivity(intent);
    }

    @Override
    public void onActivityResult(Context context, int requestCode, int resultCode, Intent data) {
        InputStream stream = null;
        if (requestCode == PICK_IMAGE) {
            try {
                if (bitmap != null) {
                    bitmap.recycle();
                }
                stream = activityInterface.getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(stream);
                sendImage();
                //imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (stream != null)
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    @Override
    public void getImageFromGallery() {
        try {
            Intent gintent = new Intent();
            gintent.setType("image/*");
            gintent.setAction(Intent.ACTION_GET_CONTENT);
            activityInterface.startActivityForResult(
                    Intent.createChooser(gintent, "Select Picture"),
                    PICK_IMAGE);
        } catch (Exception e) {
            activityInterface.showToast("Could not find gallery");
            Log.e(e.getClass().getName(), e.getMessage(), e);
        }
    }

    private void sendImage() {
        if (bitmap != null) {
            byte[] bitmapBytes = ImageUtil.getBitmapByte(bitmap);
            Bundle bundle = new Bundle();
            bundle.putByteArray("image", bitmapBytes);

            Fragment fragment = activityInterface.getSupportFragmentManager().findFragmentByTag(THIRD_FRAGMENT_TAG);
            ((ThirdRegisterationFragment) fragment).sendData(bundle);
        }
    }
}
