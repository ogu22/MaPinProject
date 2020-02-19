package myapplication.example.mapinproject.ui.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.IOException;
import java.io.InputStream;

import myapplication.example.mapinproject.R;
import myapplication.example.mapinproject.business.DatabaseManager;
import myapplication.example.mapinproject.data.entities.User;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private User user;
    private static final int RESULT_PICK_IMAGEFILE = 1000;

    private TextView display_comment;
    private TextView display_name;
    private ImageView display_image;

    private Uri filePath;

    private Button pbutton7;
    private Button pbutton8;
    private Button pbutton9;
    private Button pbutton;



    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        user = (User) bundle.getSerializable("user");
    }

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile, null, false);
        super.onCreate(savedInstanceState);
        display_comment =view.findViewById(R.id.display_comment);
        display_name = view.findViewById(R.id.display_name);
        display_image = view.findViewById(R.id.display_image);
        pbutton7 = view.findViewById(R.id.pbutton_fixed);
        view.findViewById(R.id.pbutton_fixed).setOnClickListener(this);

        final DatabaseManager databaseManager = new DatabaseManager();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = currentUser.getUid();
        return view;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        new DownloadImageTask(display_image).execute(user.getImagePath());
        display_name.setText(user.getUserName());
        display_comment.setText(user.getComment());
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == RESULT_PICK_IMAGEFILE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),filePath);
                display_image.setImageBitmap(bitmap);
                display_image.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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