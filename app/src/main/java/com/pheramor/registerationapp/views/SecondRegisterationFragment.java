package com.pheramor.registerationapp.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.pheramor.registerationapp.R;
import com.pheramor.registerationapp.presenters.MainPresenter;
import com.pheramor.registerationapp.presenters.SecondRegisterPresentation;
import com.pheramor.registerationapp.retrofit.models.User;
import com.pheramor.registerationapp.view_interfaces.SecondFragmentInterface;
import com.pheramor.registerationapp.view_interfaces.SecondRegisterationInterface;

import java.util.List;

import co.ceryle.radiorealbutton.RadioRealButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;
import es.dmoral.toasty.Toasty;
import io.apptik.widget.MultiSlider;

public class SecondRegisterationFragment extends Fragment implements SecondFragmentInterface {
    private RadioButton womanRadioButton, manRadioButton;
    private RadioGroup genderRadioButtonGroup;
    private Switch menSwitcher, womenSwitcher;
    private MultiSlider ageRangeSlider;
    private TextInputEditText zipCodeEditText;
    private Button nextButton;
    private TextView ageRangeTextView;

    private static final String TAG = SecondRegisterationFragment.class.getSimpleName();
    private SecondRegisterationInterface presenter;

    private String zipCode, gender = "Men";
    private int minAge = 18, maxAge = 60;
    private boolean men = false;
    private boolean women = false;

    public SecondRegisterationFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        presenter = new SecondRegisterPresentation(this);

        View view = inflater.inflate(R.layout.second_registeration_view, container, false);
        womanRadioButton = view.findViewById(R.id.womanRadioButton);
        manRadioButton = view.findViewById(R.id.manRadioButton);
        genderRadioButtonGroup = view.findViewById(R.id.genderRadioGroup);
        menSwitcher = view.findViewById(R.id.manSwitcher);
        womenSwitcher = view.findViewById(R.id.womanSwitcher);
        ageRangeSlider = view.findViewById(R.id.ageRangeSlider);
        zipCodeEditText = view.findViewById(R.id.zipEditText);
        nextButton = view.findViewById(R.id.nextButton);
        ageRangeTextView = view.findViewById(R.id.ageTextView);
        ageRangeTextView.setText("Age Range ( 18 - 60+ )");

        ageRangeSlider.setMin(18);
        ageRangeSlider.setMax(60);

        menSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                men = menSwitcher.isChecked();
            }
        });

        womenSwitcher.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                women = womenSwitcher.isChecked();
            }
        });

        ageRangeSlider.setOnThumbValueChangeListener(new MultiSlider.OnThumbValueChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {
                setAgeRange(value, thumbIndex);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToNext();
            }
        });

        //setInitialValues();

        return view;
    }

    private void setInitialValues() {
        User user = presenter.getUser();
        if (user != null) {
            List<String> interests = user.getGenderInterest();
            zipCode = user.getZipCode();
            minAge = user.getMin_range();
            maxAge = user.getMax_range();
            gender = user.getGender();

            if (interests.contains("Men")) men = true;
            if (interests.contains("Women")) women = true;

            zipCodeEditText.setText(zipCode);
            ageRangeSlider.setMin(minAge, true);
            ageRangeSlider.setMax(maxAge, true);

            setAgeRange(minAge, 0);
            setAgeRange(maxAge, 1);

            switch(gender) {
                case "Male":
                    manRadioButton.setChecked(true);
                    break;
                case "Female":
                    womanRadioButton.setChecked(true);
                    break;
            }
        }
    }
    private void setAgeRange(int value, int thumbIndex) {
        String range = ageRangeTextView.getText().toString();
        if (thumbIndex == 0) minAge = value;
        else maxAge = value;
        String ageText = presenter.getAgeValue(value, thumbIndex, range);
        ageRangeTextView.setText(ageText);
    }

    private void sendToNext() {
        setGender();
        zipCode = zipCodeEditText.getText().toString();
        String errorString = presenter.setValid(gender, men, women, minAge, maxAge, zipCode);
        if (errorString.equals("")) {
            presenter.sendData(gender, men, women, minAge, maxAge, zipCode);
            ((MainActivity) getActivity()).getMainPresenter().setThirdForm();
        }
        else {
            Toasty.error(getContext(), errorString, Toast.LENGTH_LONG, true).show();
        }
    }

    private void setGender() {
        int selectedId = genderRadioButtonGroup.getCheckedRadioButtonId();

        if (selectedId == manRadioButton.getId()) gender = "Male";
        else gender = "Female";

        Log.d(TAG, "setGender(): " + gender);
    }

}
