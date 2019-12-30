package com.example.onlinefoodordering.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.onlinefoodordering.R;
import com.example.onlinefoodordering.ui.login.LoginViewModel;
import com.example.onlinefoodordering.utils.ViewModelFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
public class ProfileFragment extends DaggerFragment {

    private ImageView profileThumb;
    private TextView profileName, profileEmail;
    private ProgressBar progressBar;
    private View frame;

    @Inject
    ViewModelFactory viewModelFactory;
    private LoginViewModel loginViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        // Set ViewModel
        loginViewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel.class);

        // Set View
        profileThumb = v.findViewById(R.id.profile_thumb);
        profileName = v.findViewById(R.id.profile_name);
        profileEmail = v.findViewById(R.id.profile_email);
        progressBar = v.findViewById(R.id.progressBar);
        frame = v.findViewById(R.id.profile_frame);

        observableViewModel();
        return v;
    }

    private void observableViewModel() {
        loginViewModel.getLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                if (isLoading == true) {
                    frame.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    frame.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });

        loginViewModel.getUserMutable().observe(this, user -> {
            if (user != null) {
                Glide.with(getContext()).load(user.getProfileThumb()).placeholder(R.drawable.shadow_bottom_to_top)
                        .into(profileThumb);
                profileName.setText(user.getUserName());
                profileEmail.setText(user.getEmail());
            }
        });
    }

}
