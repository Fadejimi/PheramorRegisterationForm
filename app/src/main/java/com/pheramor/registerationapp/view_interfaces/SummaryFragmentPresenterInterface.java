package com.pheramor.registerationapp.view_interfaces;

import com.pheramor.registerationapp.models.Detail;

import java.io.File;
import java.util.List;

public interface SummaryFragmentPresenterInterface {
    List<Detail> getContactDetails();
    List<Detail> getInfoDetails();
    List<Detail> getInterestedDetails();
    List<Detail> getReligionsDetails();
    void onTaskCompleted(File file);
    void setProgress(boolean bool);
    void submit();
    void restart();
}
