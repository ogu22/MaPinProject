package myapplication.example.mapinproject.ui.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import myapplication.example.mapinproject.R;
import myapplication.example.mapinproject.business.DatabaseManager;
import myapplication.example.mapinproject.business.UserCallBack;
import myapplication.example.mapinproject.business.activities.LoginActivity;
import myapplication.example.mapinproject.data.entities.Test;
import myapplication.example.mapinproject.data.entities.User;
import myapplication.example.mapinproject.ui.search.History_SearchFragment;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView textview;
    private Button pbutton7;
    private Button pbutton8;
    private Button pbutton9;
    private Button pbutton;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile, null, false);

        final DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.getUser("", new UserCallBack() {
            @Override
            public void getUserCallBack(HashMap<String, User> map) {
                for (User user : map.values()) {
                    String userName = user.getUserName();
                    String userImage = user.getImagePath();
                    databaseManager.getImage(userImage);
                }
            }
        });

        super.onCreate(savedInstanceState);
        textview =view.findViewById(R.id.ptextView12);
        pbutton7 = view.findViewById(R.id.pbutton7);
        pbutton8 = view.findViewById(R.id.pbutton8);
        pbutton9 = view.findViewById(R.id.pbutton9);
        pbutton = view.findViewById(R.id.pbutton);
        view.findViewById(R.id.pbutton7).setOnClickListener(this);
        view.findViewById(R.id.pbutton8).setOnClickListener(this);
        view.findViewById(R.id.pbutton9).setOnClickListener(this);
        view.findViewById(R.id.pbutton).setOnClickListener(this);

        return view;
    }
    public void onClick(View v) {
        if (v == pbutton7) {
            // プロフィール編集画面へ(未作成)
            Profile(v);
        }else if(v == pbutton8){
            // 自分の投稿を表示
        }else if(v == pbutton9){
            // お気に入りを表示
        }else if(v == pbutton){
            // さらに表示
        }
    }

    private void Profile(View v) {
        // プロフィール編集画面ログインへ
        FragmentTransaction pchange = getFragmentManager().beginTransaction();
        pchange.replace(R.id.nav_host_fragment, new ProfileChangeFragment());
        pchange.commit();
    }
}