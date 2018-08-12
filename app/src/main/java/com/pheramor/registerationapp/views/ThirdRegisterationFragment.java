package com.pheramor.registerationapp.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pheramor.registerationapp.R;
import com.pheramor.registerationapp.presenters.ThirdRegisterationPresenter;
import com.pheramor.registerationapp.view_interfaces.ThirdFragmentInterface;
import com.pheramor.registerationapp.view_interfaces.ThirdRegisterationInterface;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class ThirdRegisterationFragment extends Fragment implements ThirdFragmentInterface {
    private CircleImageView imageView;
    private Button uploadButton, galleryButton;
    private ThirdRegisterationInterface presenter;
    String imagePath = null;

    public ThirdRegisterationFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        presenter = new ThirdRegisterationPresenter(this);
        View view = inflater.inflate(R.layout.third_registeration_view, container, false);
        imageView = view.findViewById(R.id.profile_image);
        uploadButton = view.findViewById(R.id.upload_button);
        galleryButton = view.findViewById(R.id.gallery_button);

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gallery();
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });

        return view;
    }

    private void gallery() {
        ((MainActivity) getActivity()).getMainPresenter().getImageFromGallery();
    }

    private void upload() {
        if (isValid()) {
            presenter.setData(imagePath);
            ((MainActivity) getActivity()).getMainPresenter().sendToSummary();
        }
    }

    private boolean isValid() {
        if (imagePath == null) {
            Toasty.error(getContext(), "Please select an image from gallery or take a picture",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void sendData(Bundle bundle) {
        //imageBytes = bundle.getByteArray("image");
        imagePath = bundle.getString("imagePath");
        Glide.with(getContext()).load(imagePath).into(imageView);
    }
}
