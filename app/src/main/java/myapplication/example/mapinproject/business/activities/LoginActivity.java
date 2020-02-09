package myapplication.example.mapinproject.business.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.AuthCredential;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthProvider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//import com.facebook.AccessToken;
//import com.facebook.login.widget.LoginButton;
//import com.facebook.login.LoginResult;
//import com.facebook.FacebookCallback;

import android.util.Log;

import myapplication.example.mapinproject.R;

import com.google.firebase.auth.FacebookAuthProvider;

public class LoginActivity extends BaseLoginActivity implements View.OnClickListener {

    private EditText mEmailField;
    private EditText mPassField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mEmailField = findViewById(R.id.emailField);
        mPassField = findViewById(R.id.passwdField);

        findViewById(R.id.registration_button).setOnClickListener(this);
        findViewById(R.id.emailSignUpButton).setOnClickListener(this);
        findViewById(R.id.nbutton2).setOnClickListener(this);

        //ログインチェック
        if (isLogin()){
            changeHomeActivity();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registration_button:
                //ログインボタン
                signIn(mEmailField.getText().toString(), mPassField.getText().toString());
                break;
            case R.id.emailSignUpButton:
                //会員登録ボタン
                changeRegistrationActivity();
                break;
            case R.id.facebookbutton:
                //Facebookログインボタン？
                break;
            case R.id.nbutton2:
                //Twitterログインボタン
                break;
        }
    }

    private boolean isLogin() {
        FirebaseUser prevUser = FirebaseAuth.getInstance().getCurrentUser();
        return  (prevUser != null) ;
    }
}