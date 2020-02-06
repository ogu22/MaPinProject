package myapplication.example.mapinproject.ui.postadd;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import myapplication.example.mapinproject.R;
import myapplication.example.mapinproject.business.DatabaseManager;
import myapplication.example.mapinproject.business.activities.HomeActivity;
import myapplication.example.mapinproject.business.activities.Post_OriginalActivity;
import myapplication.example.mapinproject.business.fragments.PostDoneDialog;
import myapplication.example.mapinproject.business.fragments.Post_DeleteDialog;
import myapplication.example.mapinproject.data.entities.Location;
import myapplication.example.mapinproject.data.entities.Reply;
import myapplication.example.mapinproject.data.entities.Tag;
import myapplication.example.mapinproject.data.entities.Tweeit;
import myapplication.example.mapinproject.data.storage.PostStorage;

import static android.app.Activity.RESULT_OK;

public class PostAddFragment extends Fragment {

    private static final int RESULT_PICK_IMAGEFILE = 1000;
    private ImageView imageView;
    private EditText locationText;
    private EditText contentText;
    private EditText categoryText;
    private RatingBar ratingBar;

    private Uri filePath;

    private String latitude;
    private String longitude;

    public static PostAddFragment newInstance() {
        PostAddFragment fragment = new PostAddFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            latitude = String.valueOf(bundle.getDouble("latitude"));
            longitude =String.valueOf(bundle.getDouble("longitude"));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.post_original, null, false);

        locationText = view.findViewById(R.id.post_location_text);
        contentText = view.findViewById(R.id.post_content_text);
        categoryText = view.findViewById(R.id.post_category_text);
        imageView = view.findViewById(R.id.post_image_button);
        ratingBar = view.findViewById(R.id.ratingBar);

        view.findViewById(R.id.post_image_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, RESULT_PICK_IMAGEFILE);
            }
        });
        view.findViewById(R.id.post_send_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManager.addImage(filePath, new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        test((UploadTask.TaskSnapshot) o);
                    }
                }, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        test(null);
                    }
                });

            }

            private void test(UploadTask.TaskSnapshot o) {
                //TODO: ここらへんリファクタリングしたい...
                String uploadedUrl = "";
                if (o != null) {
                    UploadTask.TaskSnapshot taskSnapshot = o;
                    uploadedUrl = taskSnapshot.getUploadSessionUri().toString();
                }
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String uuid = UUID.randomUUID().toString();
                String userId = currentUser.getUid();

                Tag tag = new Tag(UUID.randomUUID().toString(), categoryText.getText().toString());
                ArrayList<Tag> tags = new ArrayList<>();
                tags.add(tag);
                for (Tag t : tags) {
                    DatabaseManager.addTag(t);
                }

                if (latitude == null) {
                    return;
                }
                Location location = new Location(latitude, longitude);
                ArrayList<Reply> replies = new ArrayList<>();
                String title = locationText.getText().toString();
                String content = contentText.getText().toString();
                Date date = new Date();
                String dateFormat = new SimpleDateFormat("yyyy年MM月dd日 hh時mm分ss秒").format(date);
                int num = ratingBar.getNumStars();

                Tweeit tweeit = new Tweeit(uuid, userId, tags, location, replies, title, content, uploadedUrl, dateFormat, num);
                DatabaseManager.addTweeit(tweeit);

                FragmentManager fragmentManager = getParentFragmentManager();
                if (fragmentManager != null) {
                    fragmentManager.popBackStack();
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == RESULT_PICK_IMAGEFILE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}