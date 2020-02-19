package myapplication.example.mapinproject.business.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import myapplication.example.mapinproject.R;

public class EmailLoginActivity extends LoginManegerActivity implements View.OnClickListener {

    private static final String TAG = "EmailLoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private EditText input_email;
    private EditText input_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_email);

        input_email = findViewById(R.id.input_email);
        input_password = findViewById(R.id.input_password);
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.link_signup).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                emailSignIn(input_email.getText().toString(),input_password.getText().toString());
                break;
            case R.id.link_signup:
                changeSignUpActivity();
                break;
        }
    }
}
