package com.pheramor.registerationapp.presenters;

import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pheramor.registerationapp.models.Detail;
import com.pheramor.registerationapp.retrofit.APIClient;
import com.pheramor.registerationapp.retrofit.UserQuery;
import com.pheramor.registerationapp.retrofit.models.User;
import com.pheramor.registerationapp.view_interfaces.SummaryFragmentInterface;
import com.pheramor.registerationapp.view_interfaces.SummaryFragmentPresenterInterface;
import com.pheramor.registerationapp.views.SummaryActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SummaryFragmentPresenter implements SummaryFragmentPresenterInterface {
    private SummaryFragmentInterface fragment;
    private User user;
    private Gson gson;
    private List<Detail> contactDetails, infoDetails, interestedDetails, religionDetails;

    public SummaryFragmentPresenter(SummaryFragmentInterface fragment) {
        this.fragment = fragment;
        gson = new Gson();

        contactDetails = new ArrayList<>();
        infoDetails = new ArrayList<>();
        interestedDetails = new ArrayList<>();
        religionDetails = new ArrayList<>();

        setUser();
    }

    private void setUser() {
        Bundle bundle = fragment.getArguments();
        if (bundle != null && bundle.getString("user") != null) {
            user = gson.fromJson(bundle.getString("user"), User.class);
            setDetails();
        }
    }

    private void setDetails() {
        contactDetails.add(new Detail("Email", user.getEmail()));

        infoDetails.add(new Detail("Gender", user.getGender()));
        infoDetails.add(new Detail("Birthday", user.getDob()));
        infoDetails.add(new Detail("Height", user.getFeet_height() + "\'" + user.getInches_height()));
        infoDetails.add(new Detail("Race", user.getRace()));

        interestedDetails.add(new Detail("Interested In", combine(user.getGenderInterest())));
        interestedDetails.add(new Detail("Age Range", user.getMin_range() + " TO " + user.getMax_range()));

        religionDetails.add(new Detail("Religion", user.getReligion()));
    }

    private String combine(List<String> list) {
        if (list.size() == 1) return list.get(0);
        else if (list.size() == 2) return list.get(0) + " and " + list.get(1);
        else return "";
    }

    @Override
    public List<Detail> getContactDetails() {
        return contactDetails;
    }

    @Override
    public List<Detail> getInfoDetails() {
        return infoDetails;
    }

    @Override
    public List<Detail> getInterestedDetails() {
        return interestedDetails;
    }

    @Override
    public List<Detail> getReligionsDetails() {
        return religionDetails;
    }

    @Override
    public void submit() {
        UserQuery query = APIClient.createService(UserQuery.class);
        Call<Void> call = query.postUser(user);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                sendToFinal();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toasty.error(fragment.getContext(), "Could not send the data",
                        Toast.LENGTH_SHORT, true);
            }
        });
    }

    private void sendToFinal() {
        ((SummaryActivity) fragment.getContext()).getPresenter().complete();
    }

    @Override
    public void restart() {
        ((SummaryActivity) fragment.getContext()).getPresenter().restart();
    }
}
