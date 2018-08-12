package com.pheramor.registerationapp.presenters;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pheramor.registerationapp.models.Detail;
import com.pheramor.registerationapp.retrofit.APIClient;
import com.pheramor.registerationapp.retrofit.UserQuery;
import com.pheramor.registerationapp.retrofit.models.User;
import com.pheramor.registerationapp.utils.HashingUtil;
import com.pheramor.registerationapp.view_interfaces.OnTaskCompleted;
import com.pheramor.registerationapp.view_interfaces.SummaryFragmentInterface;
import com.pheramor.registerationapp.view_interfaces.SummaryFragmentPresenterInterface;
import com.pheramor.registerationapp.views.SummaryActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SummaryFragmentPresenter implements SummaryFragmentPresenterInterface {
    private SummaryFragmentInterface fragment;
    private User user;
    private byte[] imageBytes;
    private Gson gson;
    private List<Detail> contactDetails, infoDetails, interestedDetails, religionDetails;
    public static final String filename = "profile_image.jpg";
    private static final String TAG = SummaryFragmentPresenter.class.getSimpleName();

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
            imageBytes = bundle.getByteArray("image");
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
        Log.d(TAG, "submit() " );
        PostToFile postImage = new PostToFile(this);
        postImage.execute(imageBytes);
    }

    private void sendToFinal() {
        ((SummaryActivity) fragment.getContext()).getPresenter().complete();
    }

    @Override
    public void restart() {
        ((SummaryActivity) fragment.getContext()).getPresenter().restart();
    }

    @Override
    public void onTaskCompleted(File f) {
        if (f != null) {
            Log.d(TAG, "onTaskCompleted()");
            fragment.setProgress(true);
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), f);
            MultipartBody.Part body = MultipartBody.Part.createFormData("upload",filename, reqFile);

            user.setPassword(HashingUtil.getSecurePassword(user.getPassword()));
            UserQuery query = APIClient.createService(UserQuery.class);
            Call<Void> call = query.postUser(body, getMap(), "https://external.dev.pheramor.com/");

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    fragment.setProgress(false);
                    sendToFinal();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    fragment.setProgress(false);
                    Toasty.error(fragment.getContext(), "Could not send the data",
                            Toast.LENGTH_LONG, true).show();
                }
            });
        }
    }

    private HashMap<String, String> getMap() {
        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("email", user.getEmail());
        userMap.put("password", user.getPassword());
        userMap.put("fullName", user.getFullName());
        userMap.put("gender", user.getGender());
        userMap.put("zipCode", user.getZipCode());
        userMap.put("feetHeight", String.valueOf(user.getFeet_height()));
        userMap.put("inchesHeight", String.valueOf(user.getInches_height()));
        userMap.put("genderInterest", combine(user.getGenderInterest()));
        userMap.put("dob", user.getDob());
        userMap.put("race",  user.getRace());
        userMap.put("religion", user.getReligion());
        userMap.put("minRange", String.valueOf(user.getMin_range()));
        userMap.put("maxRange", String.valueOf(user.getMax_range()));

        return userMap;
    }

    @Override
    public void setProgress(boolean bool) {
        fragment.setProgress(bool);
    }

    private class PostToFile extends AsyncTask<byte[], Void, File> {
        private SummaryFragmentPresenterInterface listerner;
        public PostToFile(SummaryFragmentPresenterInterface listerner) {
            this.listerner = listerner;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listerner.setProgress(true);
        }

        @Override
        protected File doInBackground(byte[]... bytes) {
            try {
                File f = new File(fragment.getContext().getCacheDir(), filename);
                boolean sucess = f.createNewFile();

                //write the bytes in file
                if (sucess) {
                    FileOutputStream fos = new FileOutputStream(f);
                    fos.write(bytes[0]);
                    fos.flush();
                    fos.close();

                    return f;
                }
                return null;
            }
            catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(File f) {
            if (f != null) {
                Log.d(TAG, "onPostExecute called");
                super.onPostExecute(f);
                listerner.setProgress(false);
                listerner.onTaskCompleted(f);
            }
            Log.d(TAG, "onPostExecute failed");
        }
    }
}
