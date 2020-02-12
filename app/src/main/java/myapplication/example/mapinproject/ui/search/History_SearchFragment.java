package myapplication.example.mapinproject.ui.search;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import myapplication.example.mapinproject.R;

public class History_SearchFragment extends Fragment{

    public static History_SearchFragment newInstance(){

        History_SearchFragment fragment = new History_SearchFragment();
        return fragment;
    }

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_search, null, false);

        return view;
    }
}
