package myapplication.example.mapinproject.ui.profile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import myapplication.example.mapinproject.R;
import myapplication.example.mapinproject.ui.profile.ProfileChangeFragment;

public class ProfileChangeFragment extends Fragment {
    public static ProfileChangeFragment newInstance(){

        ProfileChangeFragment fragment = new ProfileChangeFragment();
        return fragment;
    }

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_change, null, false);

        return view;
    }
}
