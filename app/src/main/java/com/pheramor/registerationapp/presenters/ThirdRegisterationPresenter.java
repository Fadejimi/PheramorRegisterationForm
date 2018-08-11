package com.pheramor.registerationapp.presenters;

import android.content.Context;
import android.content.SharedPreferences;

import com.pheramor.registerationapp.retrofit.models.User;
import com.pheramor.registerationapp.utils.ImageUtil;
import com.pheramor.registerationapp.utils.PreferenceUtil;
import com.pheramor.registerationapp.view_interfaces.ThirdFragmentInterface;
import com.pheramor.registerationapp.view_interfaces.ThirdRegisterationInterface;
import com.pheramor.registerationapp.views.MainActivity;

public class ThirdRegisterationPresenter implements ThirdRegisterationInterface {
    ThirdFragmentInterface fragment;
    public ThirdRegisterationPresenter(ThirdFragmentInterface fragment) {
        this.fragment = fragment;
    }

    @Override
    public void setData(byte[] imageBytes) {
        User user = new User();
        user.setPictureString(ImageUtil.encodeImage(imageBytes));

        SharedPreferences preferences = fragment.getContext().getSharedPreferences(PreferenceUtil.PREF,
                Context.MODE_PRIVATE);
        PreferenceUtil.putUser(preferences, PreferenceUtil.THIRD_FORM, user);
    }
}
