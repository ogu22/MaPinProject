package myapplication.example.mapinproject.ui.search;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import myapplication.example.mapinproject.R;
import myapplication.example.mapinproject.ui.home.HomeFragment;

public class SearchFragment extends Fragment implements View.OnClickListener{

        private SearchView searchView;
        private Spinner spinner_time;
        private Spinner spinner_evaluation;
        private Spinner spinner_range;
        private TextView textView_tag;
        private Button button3;
        private Button button_search;

    public myapplication.example.mapinproject.ui.search.SearchFragment newInstance(){

        myapplication.example.mapinproject.ui.search.SearchFragment fragment = new myapplication.example.mapinproject.ui.search.SearchFragment();
        return fragment;
    }

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.search, null, false);

        searchView = view.findViewById(R.id.searchView);
        spinner_time = view.findViewById(R.id.spinner_time);
        spinner_evaluation = view.findViewById(R.id.spinner_evaluation);
        spinner_range = view.findViewById(R.id.spinner_range);
        textView_tag = view.findViewById(R.id.textView_tag);

        button_search = view.findViewById(R.id.button_search);
        button3 =view.findViewById(R.id.button3);

        view.findViewById(R.id.button_search).setOnClickListener(this);
        view.findViewById(R.id.button3).setOnClickListener(this);

        return view;
    }

    public void onClick(View v){
        if(v == button3){
            //History_SearchFragmentに遷移
            FragmentTransaction history = getFragmentManager().beginTransaction();
                history.replace(R.id.nav_host_fragment, new History_SearchFragment());
                history.commit();
        } else if (v == button_search){
            //検索メソッド呼び出し

        }
    }

    public void search(String text,String time,String evaluation,String range,String tag){



    }



}