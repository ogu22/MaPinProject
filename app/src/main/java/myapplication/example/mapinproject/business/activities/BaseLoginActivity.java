package myapplication.example.mapinproject.business.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import myapplication.example.mapinproject.business.DatabaseManager;
import myapplication.example.mapinproject.data.entities.User;

public class BaseLoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
    }

    protected void signIn(String email, String password) {
        if (!validateForm(email,password)) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //ログイン完了
                            changeHomeActivity();
                        } else {
                            //ログイン失敗
                            Toast.makeText(getBaseContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    protected void createAccount(String email, String password) {
        if (!validateForm(email,password)) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            createUser();
                            changeHomeActivity();
                        } else {
                            Toast.makeText(getBaseContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void createUser() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();
        String dispName = firebaseUser.getDisplayName();
        String phoneNumber = firebaseUser.getPhoneNumber();
        String email = firebaseUser.getEmail();
        Uri imageUri = firebaseUser.getPhotoUrl();
        String imagePath;
        if (imageUri == null) {
            imagePath = "";
        } else {
            imagePath = imageUri.toString();
        }
        //TODO: コメントを入力させる画面つくらなきゃ。。
        String comment = "";

        User user = new User(uid,phoneNumber,dispName,email,imagePath,comment,false);
        DatabaseManager.addUser(user);
    }

    protected void changeHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    protected void changeRegistrationActivity() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    private boolean validateForm(String email,String password) {
        if (TextUtils.isEmpty(email)) {
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            return false;
        }
        return true;
    }
}
