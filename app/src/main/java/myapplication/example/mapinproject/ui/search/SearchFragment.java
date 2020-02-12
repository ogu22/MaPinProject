package myapplication.example.mapinproject.ui.search;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import myapplication.example.mapinproject.R;
import myapplication.example.mapinproject.ui.home.HomeFragment;

public class SearchFragment extends Fragment implements View.OnClickListener{

    private Button button3;

    public myapplication.example.mapinproject.ui.search.SearchFragment newInstance(){

        myapplication.example.mapinproject.ui.search.SearchFragment fragment = new myapplication.example.mapinproject.ui.search.SearchFragment();
        return fragment;
    }

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.search, null, false);

        button3 =view.findViewById(R.id.button3);
        view.findViewById(R.id.button3).setOnClickListener(this);

        return view;
    }
    public void onClick(View v){
        if(v == button3){
            //History_SearchFragmentに遷移
            FragmentTransaction history = getFragmentManager().beginTransaction();
                history.replace(R.id.nav_host_fragment, new History_SearchFragment());
                history.commit();
        }

    }

}