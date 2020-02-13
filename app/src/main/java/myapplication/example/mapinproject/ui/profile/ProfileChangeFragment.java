package myapplication.example.mapinproject.ui.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.FirebaseDatabase;

import myapplication.example.mapinproject.R;

import static myapplication.example.mapinproject.business.activities.ReplyActivity.RESULT_PICK_IMAGEFILE;

public class ProfileChangeFragment extends Fragment implements View.OnClickListener{

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Button pbutton7;
    private ImageView imageView;
    private EditText edittext1;
    private EditText editText2;

    public static ProfileChangeFragment newInstance(){
        ProfileChangeFragment fragment = new ProfileChangeFragment();
        return fragment;
    }

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_change, null, false);

        pbutton7 = view.findViewById(R.id.pbutton7);
        edittext1 = view.findViewById(R.id.edittext1);
        editText2 = view.findViewById(R.id.edittext2);
        imageView = view.findViewById(R.id.post_image_button);
        view.findViewById(R.id.pbutton7).setOnClickListener(this);
        view.findViewById(R.id.post_image_button).setOnClickListener(this);

        return view;
    }

    public void onClick(View v) {
        if (v == pbutton7) {
            // 入力内容を更新

            // プロフィール画面へ
            Profile(v);
        }
    }

    public void ImageVive(View v) {
        v.findViewById(R.id.post_image_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, RESULT_PICK_IMAGEFILE);
            }
        });
    }

    private void Profile(View v) {
        // プロフィール画面ログインへ
        FragmentTransaction pchange = getFragmentManager().beginTransaction();
        pchange.replace(R.id.nav_host_fragment, new ProfileFragment());
        pchange.commit();
    }

}
