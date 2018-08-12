package com.pheramor.registerationapp.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.pheramor.registerationapp.R;
import com.pheramor.registerationapp.adapter.SummaryAdapter;
import com.pheramor.registerationapp.presenters.SummaryFragmentPresenter;
import com.pheramor.registerationapp.view_interfaces.SummaryFragmentPresenterInterface;
import com.pheramor.registerationapp.view_interfaces.SummaryFragmentInterface;


public class SummaryFragment extends Fragment implements SummaryFragmentInterface {
    private SummaryFragmentPresenterInterface presenter;
    RecyclerView contact_rv, basic_info_rv, interested_info_rv, religious_rv;
    Button cancelButton;
    Button submitButton;
    SummaryAdapter contactAdapter, infoAdapter, interestedAdapter, religionsAdapter;
    private ProgressBar progressBar;
    public SummaryFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        presenter = new SummaryFragmentPresenter(this);

        View view = inflater.inflate(R.layout.summary_content, container, false);
        contact_rv = view.findViewById(R.id.contact_info_rv);
        basic_info_rv = view.findViewById(R.id.basic_info_rv);
        interested_info_rv = view.findViewById(R.id.interested_info_rv);
        religious_rv = view.findViewById(R.id.religious_info_rv);
        cancelButton = view.findViewById(R.id.cancel_button);
        submitButton = view.findViewById(R.id.submit_button);
        progressBar = view.findViewById(R.id.progressBar);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });

        RecyclerView.LayoutManager contentLayoutManager = new LinearLayoutManager(getContext());
        contact_rv.setLayoutManager(contentLayoutManager);

        RecyclerView.LayoutManager basicLayoutManager = new LinearLayoutManager(getContext());
        basic_info_rv.setLayoutManager(basicLayoutManager);

        RecyclerView.LayoutManager interestedLayoutManager = new LinearLayoutManager(getContext());
        interested_info_rv.setLayoutManager(interestedLayoutManager);

        RecyclerView.LayoutManager religiousLayoutManager = new LinearLayoutManager(getContext());
        religious_rv.setLayoutManager(religiousLayoutManager);

        contact_rv.setItemAnimator(new DefaultItemAnimator());
        basic_info_rv.setItemAnimator(new DefaultItemAnimator());
        interested_info_rv.setItemAnimator(new DefaultItemAnimator());
        religious_rv.setItemAnimator(new DefaultItemAnimator());

        contactAdapter = new SummaryAdapter(getContext());
        infoAdapter = new SummaryAdapter(getContext());
        interestedAdapter = new SummaryAdapter(getContext());
        religionsAdapter = new SummaryAdapter(getContext());

        contact_rv.setAdapter(contactAdapter);
        basic_info_rv.setAdapter(infoAdapter);
        interested_info_rv.setAdapter(interestedAdapter);
        religious_rv.setAdapter(religionsAdapter);

        updateData();
        return view;
    }

    private void updateData() {
        contactAdapter.setData(presenter.getContactDetails());
        infoAdapter.setData(presenter.getInfoDetails());
        interestedAdapter.setData(presenter.getInterestedDetails());
        religionsAdapter.setData(presenter.getReligionsDetails());

        contactAdapter.notifyDataSetChanged();
        infoAdapter.notifyDataSetChanged();
        interestedAdapter.notifyDataSetChanged();
        religionsAdapter.notifyDataSetChanged();
    }

    private void cancel() {
        presenter.restart();
    }

    private void submit() {
        presenter.submit();
    }

    @Override
    public void setProgress(boolean bool) {
        if (bool) {
            progressBar.setVisibility(View.VISIBLE);
        }
        else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
