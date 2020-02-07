package myapplication.example.mapinproject.business.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import myapplication.example.mapinproject.R;
import myapplication.example.mapinproject.data.entities.Test;
import myapplication.example.mapinproject.data.storage.TestStorage;

public class TestActivity extends FragmentActivity implements View.OnClickListener {

    private FirebaseDatabase database;
    private EditText editText;
    private EditText editTitle;
    private EditText editSearch;
    private TextView searchView;
    private DatabaseReference ref;
    private DatabaseReference testRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        editText = findViewById(R.id.edit_text);
        editTitle = findViewById(R.id.edit_title);
        editSearch = findViewById(R.id.edit_search);
        searchView = findViewById(R.id.search_view);
        findViewById(R.id.test_create).setOnClickListener(this);
        findViewById(R.id.test_search).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        TestStorage storage = new TestStorage();
        int i = v.getId();
        if (i == R.id.test_create) {
            storage.newCreate(editText.getText().toString(), editTitle.getText().toString());
        } else if (i == R.id.test_search) {
            search(editSearch.getText().toString());
        }
    }

    public void search(String fstr) {
        database = FirebaseDatabase.getInstance();
        DatabaseReference testRef = database.getReference("test");
        Query findRef = testRef.orderByChild("name").startAt(fstr).endAt(fstr + "\uf8ff");

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<HashMap<String, Test>> gt_indicator = new GenericTypeIndicator<HashMap<String, Test>>() {
                };
                HashMap<String, Test> values = snapshot.getValue(gt_indicator);
                String res = "";
                if (values != null) {
                    for (Object p : values.values()) {
                        res += p + "\n";
                    }
                } else {
                    res = "no data.";
                }
                searchView.setText(res);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        };
        findRef.addListenerForSingleValueEvent(listener);
    }


}