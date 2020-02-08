package myapplication.example.mapinproject.ui.postdetail;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
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
import android.text.Layout;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
import myapplication.example.mapinproject.business.Util;
import myapplication.example.mapinproject.data.entities.Location;
import myapplication.example.mapinproject.data.entities.Reply;
import myapplication.example.mapinproject.data.entities.Tag;
import myapplication.example.mapinproject.data.entities.Tweeit;
import myapplication.example.mapinproject.ui.postadd.PostAddFragment;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.INPUT_METHOD_SERVICE;

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

    private ImageView replyImageView;
    private EditText replayInputText;
    private Button replyImageAddButton;
    private Button replyPostButton;

    private Uri filePath;

    private Tweeit tweeit;
    private List<Reply> replies;
    private ReplyRecycleViewAdapter adapter;

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
        final View view = inflater.inflate(R.layout.post_details, null, false);
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
        tagText.setPaintFlags(tagText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        postComment = view.findViewById(R.id.post_comment);

        replyImageView = view.findViewById(R.id.reply_post_image_view);
        replyImageAddButton = view.findViewById(R.id.reply_image_add_button);
        replyImageAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, RESULT_PICK_IMAGEFILE);
            }
        });
        replayInputText = view.findViewById(R.id.reply_input_i_text);
        replyPostButton = view.findViewById(R.id.reply_post_button);
        replyPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String replyId = UUID.randomUUID().toString();
                String replayUserName = "ごんちゃん";
                String replayDate = Util.getDate();
                String content = replayInputText.getText().toString();
                //TODO:イメージのアップロード
                String imagePath = "";
                String myProfileImageUri = "";

                Reply reply = new Reply(replyId,replayUserName,myProfileImageUri,replayDate,content,imagePath);
                tweeit.addReplies(reply);
                DatabaseManager.addTweeit(tweeit);

                InputMethodManager inputMethodMgr = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                inputMethodMgr.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                int insertIndex = 0;
                replies.add(insertIndex,reply);

                replayInputText.setText("");
                view.findViewById(R.id.reply_input_detail_layout).setVisibility(View.GONE);
            }
        });
        replayInputText.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                view.findViewById(R.id.reply_input_detail_layout).setVisibility(View.VISIBLE);
            }
        });
        RecyclerView rv = view.findViewById(R.id.reply_recycler_view);
        replies = tweeit.getReplies();
        adapter = new ReplyRecycleViewAdapter(replies);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setHasFixedSize(true);
        rv.addItemDecoration(ReplyItemDecoration.createDefaultDecoration(getActivity()));
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);
        return view;
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
        replies = tweeit.getReplies();
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

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == RESULT_PICK_IMAGEFILE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),filePath);
                replyImageView.setImageBitmap(bitmap);
                replyImageView.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
