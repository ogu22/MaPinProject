package myapplication.example.mapinproject.business.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import myapplication.example.mapinproject.R;

public class SignUpActivity extends LoginManegerActivity implements View.OnClickListener {

    private EditText input_email;
    private EditText input_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        input_email = findViewById(R.id.input_email);
        input_password = findViewById(R.id.input_password);
        findViewById(R.id.btn_signup).setOnClickListener(this);
        findViewById(R.id.link_login).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_signup:
                createAccount(input_email.getText().toString(),input_password.getText().toString());
                break;
            case R.id.link_login:
                changeEmailLoginActivity();
                break;
        }
    }
}
