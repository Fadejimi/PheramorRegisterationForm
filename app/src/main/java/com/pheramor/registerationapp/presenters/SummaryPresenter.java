package com.pheramor.registerationapp.presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.google.gson.Gson;
import com.pheramor.registerationapp.R;
import com.pheramor.registerationapp.retrofit.models.User;
import com.pheramor.registerationapp.utils.ImageUtil;
import com.pheramor.registerationapp.utils.PreferenceUtil;
import com.pheramor.registerationapp.view_interfaces.SummaryActivityInterface;
import com.pheramor.registerationapp.view_interfaces.SummaryPresenterInterface;
import com.pheramor.registerationapp.views.SummaryFragment;

public class SummaryPresenter implements SummaryPresenterInterface {
    private SummaryActivityInterface activityInterface;
    public static final String SUMMARY_FRAGMENT_TAG = "summary_fragment";
    private User user;
    private Gson gson;

    public SummaryPresenter(SummaryActivityInterface activityInterface) {
        this.activityInterface = activityInterface;
        setUser();
        gson = new Gson();
    }

    private void setUser() {
        SharedPreferences preferences = activityInterface.getContext().getSharedPreferences(
                PreferenceUtil.PREF, Context.MODE_PRIVATE);
        user = PreferenceUtil.getUser(preferences);
    }

    @Override
    public byte[] getImageByte() {
        if (user != null) {
            return ImageUtil.decodeBytes(user.getPictureString());
        }
        return null;
    }

    @Override
    public String getName() {
        if (user != null) {
            return user.getFullName();
        }
        return null;
    }

    @Override
    public void setSummaryView() {
        Fragment fragment = new SummaryFragment();
        if (user != null) {
            String userString = gson.toJson(user, User.class);
            Bundle bundle = new Bundle();
            bundle.putString("user", userString);
            fragment.setArguments(bundle);
        }
        FragmentTransaction transaction =  activityInterface.getSupportFragmentManager()
                .beginTransaction();

        transaction.setCustomAnimations(R.animator.fragment_slide_left_enter,
                R.animator.fragment_slide_left_exit,
                R.animator.fragment_slide_right_enter,
                R.animator.fragment_slide_right_exit);
        transaction.replace(R.id.container, fragment, SUMMARY_FRAGMENT_TAG);
        transaction.commit();
    }
}