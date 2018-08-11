package com.pheramor.registerationapp.views;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.pheramor.registerationapp.R;
import com.pheramor.registerationapp.presenters.FirstRegisterationPresenter;
import com.pheramor.registerationapp.retrofit.models.User;
import com.pheramor.registerationapp.view_interfaces.FirstFragmentInterface;
import com.pheramor.registerationapp.view_interfaces.FirstRegisterationInterface;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class FirstRegisterationFragment extends Fragment implements FirstFragmentInterface,
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {
    private TextInputEditText nameEditText, emailEditText, passwordEditText, confirmEditText,
            dobEditText, heightEditText, raceEditText, religionEditText;
    private EditText feetEditText, inchesEditText;
    private String name, email, password, confirm, dob, height, feet, inches, race, religion;
    private LinearLayout heightLayout;
    private Button nextButton;
    private FirstRegisterationInterface presenter;
    private static final int RELIGION_DIALOG = 1;
    private static final int RACE_DIALOG = 2;
    private static final String DATEPICKER_TAG = "Datepickertag";

    private String[] religions = {
            "Christianity", "Islam", "Atheist", "Hinduism", "Other"
    };

    private String[] races = {
            "African/Black American", "Caucasian", "Asian"
    };

    public FirstRegisterationFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        presenter = new FirstRegisterationPresenter(this);

        View view =  inflater.inflate(R.layout.first_registeration_view, container, false);
        nameEditText = view.findViewById(R.id.nameEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        confirmEditText = view.findViewById(R.id.confirmPasswordEditText);
        dobEditText = view.findViewById(R.id.dobEditText);
        heightEditText = view.findViewById(R.id.heightEditText);
        feetEditText = view.findViewById(R.id.feetEditText);
        inchesEditText = view.findViewById(R.id.inchesEditText);
        raceEditText = view.findViewById(R.id.raceEditText);
        religionEditText = view.findViewById(R.id.religionEditText);

        heightLayout = view.findViewById(R.id.heightLayout);
        nextButton = view.findViewById(R.id.nextButton);

        heightEditText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                toggleHeight();
            }
        });
        feetEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String st = editable.toString();
                updateFeet(st);
            }
        });
        inchesEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String st = editable.toString();
                updateInches(st);
            }
        });
        raceEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectRace();
            }
        });
        religionEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectReligion();
            }
        });

        dobEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        FirstRegisterationFragment.this,
                        now
                );
                //dpd.setDateRangeLimiter(new DateRangeSelector(Parcel.obtain()));
                dpd.setYearRange(1930, 2000);
                dpd.vibrate(false);
                dpd.show( getActivity().getFragmentManager(), DATEPICKER_TAG);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });

        User user = presenter.getUser();
        //display(user);
        return view;
    }


    private void display(User user) {
        if (user != null) {
            nameEditText.setText(user.getFullName());
            emailEditText.setText(user.getEmail());
            passwordEditText.setText(user.getPassword());
            confirmEditText.setText(user.getPassword());
            dobEditText.setText(user.getDob());
            height = user.getFeet_height() + "\'" + user.getInches_height();
            heightEditText.setText(height);
            raceEditText.setText(user.getRace());
            religionEditText.setText(user.getReligion());
            feetEditText.setText(user.getFeet_height());
            inchesEditText.setText(user.getInches_height());
        }
    }

    @Override
    public void toggleHeight() {
        if (heightLayout.getVisibility() == View.GONE) heightLayout.setVisibility(View.VISIBLE);
        else heightLayout.setVisibility(View.GONE);
    }

    @Override
    public void updateFeet(String feetString) {
        String height = heightEditText.getText().toString();
        height = presenter.setFeet(height, feetString);
        heightEditText.setText(height);
    }

    @Override
    public void updateInches(String inchesString) {
        String height = heightEditText.getText().toString();
        height = presenter.setInches(height, inchesString);
        heightEditText.setText(height);
    }

    @Override
    public void selectRace() {
        showDialog(RACE_DIALOG);
    }

    @Override
    public void selectReligion() {
        showDialog(RELIGION_DIALOG);
    }

    @Override
    public void next() {
        if (isValid()) {
            presenter.setData(name, email, password, confirm, race, religion, height, feet,
                    inches, dob);
            ((MainActivity) getActivity()).getMainPresenter().setSecondForm();
        }
    }

    public boolean isValid() {
        name = nameEditText.getText().toString();
        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();
        confirm = confirmEditText.getText().toString();
        race = raceEditText.getText().toString();
        religion = religionEditText.getText().toString();
        height = heightEditText.getText().toString();
        feet = feetEditText.getText().toString();
        inches = inchesEditText.getText().toString();
        dob = dobEditText.getText().toString();

        String errorString = presenter.setValid(name, email, password, confirm,
                race, religion, height, dob);

        if (errorString.equals("")) return true;

        Toasty.error(getContext(), errorString, Toast.LENGTH_LONG, true).show();
        return false;
    }

    private void showDialog(final int flag) {
        ArrayAdapter<String> adapter;
        String title = "";
        switch(flag) {
            case RACE_DIALOG:
                adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_dropdown_item, races);
                title = getResources().getString(R.string.race);
                break;
            default:
                adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_dropdown_item, religions);
                title = getResources().getString(R.string.religion);
                break;
        }

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.item_listview);
        dialog.setTitle(title);

        ListView listView = dialog.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (flag == RACE_DIALOG) {
                    String race = races[position];
                    raceEditText.setText(race);
                }
                else {
                    String religion = religions[position];
                    religionEditText.setText(religion);
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        // Create a Date variable/object with user chosen date
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
        Date chosenDate = cal.getTime();

        // Format the date using style and locale
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
        String formattedDate = df.format(chosenDate);

        dobEditText.setText(formattedDate);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        DatePickerDialog dpd = (DatePickerDialog) getActivity().getFragmentManager().findFragmentByTag(DATEPICKER_TAG);
        if(dpd != null) dpd.setOnDateSetListener(this);
    }

}
