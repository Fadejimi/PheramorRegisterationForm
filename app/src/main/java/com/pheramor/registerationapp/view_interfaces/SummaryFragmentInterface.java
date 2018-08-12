package com.pheramor.registerationapp.view_interfaces;

import android.content.Context;
import android.os.Bundle;

public interface SummaryFragmentInterface {
    Bundle getArguments();
    Context getContext();
    void setProgress(boolean bool);
}
