package myapplication.example.mapinproject.ui.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import myapplication.example.mapinproject.R;
import myapplication.example.mapinproject.business.DatabaseManager;
import myapplication.example.mapinproject.data.entities.User;

import static android.app.Activity.RESULT_OK;

public class ProfileChangeFragment extends Fragment {

    private static final int RESULT_PICK_IMAGEFILE = 1000;

    private User user;

    private Button pbutton_fixed;
    private ImageView post_image;

    private EditText edit_name;
    private EditText edit_comment;

    private Uri filePath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        user = (User) bundle.getSerializable("user");
    }

    public static ProfileChangeFragment newInstance() {
        ProfileChangeFragment fragment = new ProfileChangeFragment();
        return fragment;
    }

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_change, null, false);

        pbutton_fixed = view.findViewById(R.id.btn_edit);
        edit_name = view.findViewById(R.id.display_name);
        edit_comment = view.findViewById(R.id.edit_comment);
        post_image = view.findViewById(R.id.display_image);

        //プロフィール画像
        view.findViewById(R.id.display_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, RESULT_PICK_IMAGEFILE);
            }
        });

        //変更確定
        view.findViewById(R.id.btn_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManager.addImage(filePath, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //画像がない場合
                        userUpdate(null);
                    }
                }, new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            userUpdate(downloadUri);
                        } else {
                            // Handle failures
                            // ...
                        }
                    }
                });
                // プロフィール画面へ
                Profile(v);
            }

            //ユーザ情報更新
            private void userUpdate(Uri o) {
                String uploadedUrl = "";
                if (o != null) {
                    uploadedUrl = o.toString();
                }

                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String userId = currentUser.getUid();
                User user = new User(userId, edit_name.getText().toString(), uploadedUrl, edit_comment.getText().toString());
                DatabaseManager.addUser(user);
            }

        });

        return view;
    }

    private void Profile(View v) {
        // プロフィール画面ログインへ
        ProfileFragment profileFragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        profileFragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, profileFragment);
        transaction.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_PICK_IMAGEFILE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                post_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
