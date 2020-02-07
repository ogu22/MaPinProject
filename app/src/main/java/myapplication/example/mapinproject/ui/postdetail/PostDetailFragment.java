package myapplication.example.mapinproject.ui.postdetail;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import myapplication.example.mapinproject.R;
import myapplication.example.mapinproject.business.DatabaseManager;
import myapplication.example.mapinproject.business.ReplyItemDecoration;
import myapplication.example.mapinproject.business.ReplyRecycleViewAdapter;
import myapplication.example.mapinproject.business.TweeitCallback;
import myapplication.example.mapinproject.data.entities.Location;
import myapplication.example.mapinproject.data.entities.Reply;
import myapplication.example.mapinproject.data.entities.Tag;
import myapplication.example.mapinproject.data.entities.Tweeit;
import myapplication.example.mapinproject.ui.postadd.PostAddFragment;

import static android.app.Activity.RESULT_OK;

public class PostDetailFragment extends Fragment {

    private static final int RESULT_PICK_IMAGEFILE = 1000;
    private Button backButton;
    private ImageView postUserImage;
    private TextView pinName;
    private TextView postRating;
    private TextView postDate;
    private ImageView postImage;
    private TextView tagText;
    private TextView postComment;

    private Tweeit tweeit;
    private ProgressDialog progressDialog;

    public static PostDetailFragment newInstance() {
        PostDetailFragment fragment = new PostDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        tweeit = (Tweeit) bundle.getSerializable("tweeit");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.post_details, null, false);
        backButton = view.findViewById(R.id.post_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //戻る処理
                FragmentManager fragmentManager = getParentFragmentManager();
                if (fragmentManager != null) {
                    fragmentManager.popBackStack();
                }
            }
        });
        postUserImage = view.findViewById(R.id.post_user_image);
        pinName = view.findViewById(R.id.pin_name);
        postRating = view.findViewById(R.id.post_rating);
        postDate = view.findViewById(R.id.post_date);
        postImage = view.findViewById(R.id.post_image);
        tagText = view.findViewById(R.id.tag_text);
        postComment = view.findViewById(R.id.post_comment);
        RecyclerView rv = view.findViewById(R.id.reply_recycler_view);
        ReplyRecycleViewAdapter adapter = new ReplyRecycleViewAdapter(this.createDataset());
        //ReplyRecycleViewAdapter adapter = new ReplyRecycleViewAdapter(tweeit.getReplies());
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setHasFixedSize(true);
        rv.addItemDecoration(ReplyItemDecoration.createDefaultDecoration(getActivity()));
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);
        return view;
    }

    //テストデータ
    private List<Reply> createDataset() {
        List<Reply> dataset = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Uri uri = Uri.parse("https://cdn.iconscout.com/icon/free/png-256/cat-154-450078.png");
            Reply data = new Reply(String.valueOf(i),"gonchan",uri,"2020年12月24日 12時22分34秒","ほげほげ",uri);

            dataset.add(data);
        }
        return dataset;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        //FIXME ぬルポ
        new DownloadImageTask(postUserImage).execute(tweeit.getImagePath());
        pinName.setText(tweeit.getTweeitTitle());
        postRating.setText(tweeit.getRating());
        postDate.setText(tweeit.getTimestamp());
        tagText.setText(tweeit.getTagString());
        //FIXME ぬルポ
        new DownloadImageTask(postImage).execute(tweeit.getImagePath());
        postComment.setText(tweeit.getTweeit());
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
}
