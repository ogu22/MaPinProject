package myapplication.example.mapinproject.business.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import myapplication.example.mapinproject.R;
import myapplication.example.mapinproject.business.fragments.PostDoneDialog;
import myapplication.example.mapinproject.data.storage.PostStorage;

public class Post_OriginalActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int RESULT_PICK_IMAGEFILE = 1000;
    private ImageView imageView;

    private FirebaseDatabase database;

    private EditText locationText;
    private EditText contentText;
    private EditText categoryText;
    private RatingBar assessmentNumber;
    private DatabaseReference ref;
    private DatabaseReference testRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_original);

        locationText = findViewById(R.id.post_location_text);
        contentText = findViewById(R.id.post_content_text);
        categoryText = findViewById(R.id.post_category_text);
        assessmentNumber =findViewById(R.id.ratingBar);
        RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        int num =ratingBar.getNumStars();
        findViewById(R.id.post_image_button).setOnClickListener(this);
        findViewById(R.id.post_send_button).setOnClickListener(this);


        ImageButton imageButton = findViewById(R.id.post_image_button);


    }

    @Override
    public void onClick(View v) {
        PostStorage storage = new PostStorage();
        int i = v.getId();
        if (i == R.id.post_image_button) {
            ImageVive();
        } else if (i == R.id.post_send_button) {
            storage.newCreate(categoryText.getText().toString(), contentText.getText().toString(), locationText.getText().toString(),assessmentNumber.getRating());

        }
        if (i == R.id.post_send_button) {
            profiledone();
        }
    }

    public void Pin_infoActivity() {
        Intent intent = new Intent(Post_OriginalActivity.this, Pin_infoActivity.class);
        startActivity(intent);
    }

    public void ImageVive() {
        findViewById(R.id.post_image_button).setOnClickListener(new View.OnClickListener() {
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
    public void profiledone(){
    }
}