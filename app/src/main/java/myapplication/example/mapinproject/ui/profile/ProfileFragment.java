package myapplication.example.mapinproject.ui.profile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;

import myapplication.example.mapinproject.R;
import myapplication.example.mapinproject.data.entities.User;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private User user;
    private TextView textview12;
    private TextView ptextview12;
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

//        final DatabaseManager databaseManager = new DatabaseManager();
//        databaseManager.getUser("", new UserCallBack() {
//            @Override
//            public void getUserCallBack(HashMap<String, User> map) {
//                for (User user : map.values()) {
//                    String userName = user.getUserName();
//                    String userImage = user.getImagePath();
//                    databaseManager.getImage(userImage);
//                }
//            }
//        });

        super.onCreate(savedInstanceState);
        textview12 =view.findViewById(R.id.textView12);
        ptextview12 = view.findViewById(R.id.edittext1);
        pbutton7 = view.findViewById(R.id.pbutton7);
        view.findViewById(R.id.pbutton7).setOnClickListener(this);
//        String name = user.getUserName();
//        ptextview12.setText(name);

        return view;
    }

    public void onClick(View v) {
        if (v == pbutton7) {
            // プロフィール編集画面へ
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
        // プロフィール編集画面へ
        FragmentTransaction pchange = getFragmentManager().beginTransaction();
        pchange.replace(R.id.nav_host_fragment, new ProfileChangeFragment());
        pchange.commit();
    }
}