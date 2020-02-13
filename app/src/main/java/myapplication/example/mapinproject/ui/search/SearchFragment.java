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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import myapplication.example.mapinproject.R;
import myapplication.example.mapinproject.data.entities.DateRange;
import myapplication.example.mapinproject.data.entities.Params;
import myapplication.example.mapinproject.data.entities.SearchConditions;
import myapplication.example.mapinproject.data.entities.Tag;
import myapplication.example.mapinproject.ui.home.HomeFragment;

public class SearchFragment extends Fragment implements View.OnClickListener{

        private SearchView searchView;
        private Spinner spinner_time;
        private Spinner spinner_evaluation;
        private Spinner spinner_range;
        private TextView edit_tag;
        private Button button3;
        private Button button_search;

    public SearchFragment newInstance(){

        SearchFragment fragment = new SearchFragment();
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
        edit_tag = view.findViewById(R.id.edit_tag);

        button_search = view.findViewById(R.id.button_search);
        button3 =view.findViewById(R.id.button3);

        view.findViewById(R.id.button_search).setOnClickListener(this);
        view.findViewById(R.id.button3).setOnClickListener(this);

        return view;
    }

    public void onClick(View v){
        if(v == button3){
            //History_SearchFragmentに遷移
            FragmentTransaction history = getParentFragmentManager().beginTransaction();
                history.replace(R.id.nav_host_fragment, new History_SearchFragment());
                history.commit();
        } else if (v == button_search){
            //検索メソッド呼び出し
            Params.searchConditions = makeSearchCondition();
            FragmentManager fragmentManager = getParentFragmentManager();
            if (fragmentManager != null) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.nav_host_fragment,new HomeFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        }
    }

    public SearchConditions makeSearchCondition(){
        String searchWord = searchView.getQuery().toString();
        Calendar dispLimit = convertTime((String) spinner_time.getSelectedItem());
        DateRange dateRange = new DateRange(dispLimit);
        Tag dispTag = convertTag(edit_tag.getText().toString());
        int dispEvaluation = convertEvaluation((String) spinner_evaluation.getSelectedItem());
        int dispRange = convertRange((String) spinner_range.getSelectedItem());
        return new SearchConditions(searchWord,dateRange,dispTag,dispEvaluation,dispRange);
    }

    private Calendar convertTime(String dispLimit) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        switch (dispLimit) {
            case "一時間以内":
                calendar.add(Calendar.HOUR, -1);
                return calendar;
            case "今日":
                calendar.add(Calendar.DATE, -1);
                return calendar;
            case "今週":
                calendar.add(Calendar.WEEK_OF_YEAR, -1);
                return calendar;
            case "今月":
                calendar.add(Calendar.MONTH, -1);
                return calendar;
            case "今年":
                calendar.add(Calendar.YEAR, -1);
                return calendar;
                default:
                    calendar.add(Calendar.DATE, -2);
                    return calendar;
        }
    }

    private int convertEvaluation(String dispEvaluation) {
        switch (dispEvaluation) {
            case "星５":
                return 5;
            case "星４以上":
                return 4;
            case "星３以上":
                return 3;
            case "星２以上":
                return 2;
            case "星１":
                return 1;
            default:
                return 0;
        }
    }

    private int convertRange(String dispRange) {
        switch (dispRange) {
            case "１キロ以内":
                return 1000;
            case "２キロ以内":
                return 2000;
            case "３キロ以内":
                return 3000;
            default:
                return 500;
        }
    }

    private Tag convertTag(String dispTag) {
        return  new Tag();
    }
}