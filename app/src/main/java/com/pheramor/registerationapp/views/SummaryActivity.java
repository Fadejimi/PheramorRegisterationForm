package com.pheramor.registerationapp.views;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pheramor.registerationapp.R;
import com.pheramor.registerationapp.presenters.SummaryPresenter;
import com.pheramor.registerationapp.utils.ImageUtil;
import com.pheramor.registerationapp.view_interfaces.SummaryActivityInterface;
import com.pheramor.registerationapp.view_interfaces.SummaryPresenterInterface;

import de.hdodenhof.circleimageview.CircleImageView;

public class SummaryActivity extends AppCompatActivity implements SummaryActivityInterface {
    private SummaryPresenterInterface presenter;
    private CircleImageView imageView;
    private TextView nameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        presenter = new SummaryPresenter(this);
        imageView = findViewById(R.id.profile_image);
        nameTextView = findViewById(R.id.nameTextView);

        Glide.with(SummaryActivity.this).load(presenter.getImageByte())
                .into(imageView);
        nameTextView.setText(presenter.getName());

        presenter.setSummaryView();
    }

    @Override
    public SummaryPresenterInterface getPresenter() {
        return presenter;
    }

    @Override
    public Context getContext() {
        return this;
    }


}
