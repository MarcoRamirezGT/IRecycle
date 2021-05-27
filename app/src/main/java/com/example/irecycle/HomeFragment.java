package com.example.irecycle;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.irecycle.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        Glide.with(this)
                .load(mUser.getPhotoUrl())
                .centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .fallback(R.drawable.profile)
                .into(binding.imageProfile);

        if (mUser.getPhotoUrl() != null) {
            Glide.with(this)
                    .load(mUser.getPhotoUrl())
                    .centerCrop()
                    .placeholder(R.drawable.profile)
                    .error(R.drawable.profile)
                    .fallback(R.drawable.profile)
                    .into(binding.imageProfile);

        }

        binding.imageProfile.setOnClickListener(view -> Navigation.findNavController(requireView())
        .navigate(R.id.action_home_fragment_to_profile_fragment));
    }

}