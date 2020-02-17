package myapplication.example.mapinproject.ui.search;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import myapplication.example.mapinproject.R;
import myapplication.example.mapinproject.ui.profile.ProfileFragment;

public class History_SearchFragment extends Fragment implements View.OnClickListener {

    private Button buckbutton;

    public static History_SearchFragment newInstance(){

        History_SearchFragment fragment = new History_SearchFragment();
        return fragment;
    }

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_search, null, false);

        buckbutton = view.findViewById(R.id.buckbutton);
        view.findViewById(R.id.buckbutton).setOnClickListener(this);

        return view;
    }

    public void onClick(View v){
        if(v == buckbutton){
            FragmentTransaction pchange = getFragmentManager().beginTransaction();
            pchange.replace(R.id.nav_host_fragment, new SearchFragment());
            pchange.commit();
        }
    }
}
