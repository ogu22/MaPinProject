package myapplication.example.mapinproject.business.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import myapplication.example.mapinproject.R;

public class RegistrationActivity extends BaseLoginActivity implements View.OnClickListener {

    private EditText mEmailField;
    private EditText mPassField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_user);

        mEmailField = findViewById(R.id.register_email_field);
        mPassField = findViewById(R.id.register_passwd_field);

        findViewById(R.id.registration_user_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registration_user_button:
                createAccount(mEmailField.getText().toString(), mPassField.getText().toString());
                break;
        }
    }
}
