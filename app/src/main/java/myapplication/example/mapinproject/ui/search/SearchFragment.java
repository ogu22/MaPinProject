package myapplication.example.mapinproject.ui.search;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import myapplication.example.mapinproject.R;
import myapplication.example.mapinproject.business.activities.HomeActivity;

public class SearchFragment extends Fragment implements View.OnClickListener{

    public myapplication.example.mapinproject.ui.search.SearchFragment newInstance(){

        myapplication.example.mapinproject.ui.search.SearchFragment fragment = new myapplication.example.mapinproject.ui.search.SearchFragment();
        return fragment;
    }

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.search, null, false);

        return view;
    }

    public void onClick(View v) {
    }

}