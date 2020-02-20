package myapplication.example.mapinproject.business.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.OAuthProvider;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import myapplication.example.mapinproject.R;
import myapplication.example.mapinproject.business.DatabaseManager;
import myapplication.example.mapinproject.business.UserCallBack;
import myapplication.example.mapinproject.business.activities.HomeActivity;
import myapplication.example.mapinproject.data.entities.User;

public class LoginManegerActivity extends AppCompatActivity {

    private static final String TAG = "GoogleActivity";
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    private User currentUser;

    //任意の数字
    private static final int RC_SIGN_IN = 9001;

    OAuthProvider.Builder provider = OAuthProvider.newBuilder("twitter.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        // Googleサインインのためのオプションを指定
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        FacebookSdk.sdkInitialize(getApplicationContext());

        provider.addCustomParameter("lang", "fr");

    }

    protected void googleSignIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        //Googleサインイン画面に遷移
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    //Googleサインイン画面から戻ってきたときに実行される。
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    //Googleアカウント情報を元に、firebaseで認証する。
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //認証成功！！
                            Log.d(TAG, "signInWithCredential:success");
                            createUser();
                            changeUI();
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

    protected void twitterSignIn(){
        mAuth
                .startActivityForSignInWithProvider(/* activity= */ this, provider.build())
                .addOnSuccessListener(
                        new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                // User is signed in.
                                // IdP data available in
                                // authResult.getAdditionalUserInfo().getProfile().
                                // The OAuth access token can also be retrieved:
                                // authResult.getCredential().getAccessToken().
                                // The OAuth secret can be retrieved by calling:
                                // authResult.getCredential().getSecret().
                                createUser();
                                changeUI();
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle failure.
                            }
                        });
    }

    protected void facebookSignIn(){

    }

    protected void emailSignIn(String email, String password) {
        if (!validateForm(email,password)) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //ログイン完了
                            changeUI();
                        } else {
                            //ログイン失敗
                            Toast.makeText(getBaseContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    protected void changeUI() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();
        DatabaseManager.getUser(uid, new UserCallBack() {
                    @Override
                    public void getUserCallBack(User user) {
                        currentUser = user;
                        changeHomeActivity();
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
                            changeEmailLoginActivity();
                        } else {
                            Toast.makeText(getBaseContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //ユーザ作成
    private void createUser() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();
        String userName = firebaseUser.getDisplayName();
        if(userName==null){
            userName="";
        }

        Uri imageUri = firebaseUser.getPhotoUrl();
        String imagePath;
        if (imageUri == null) {
            imagePath = "";
        } else {
            imagePath = imageUri.toString();
        }
        String comment = "";

        User user = new User(uid,userName,imagePath,comment);
        DatabaseManager.addUser(user);
    }

    private boolean validateForm(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            return false;
        }
        return true;
    }

    protected void changeHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("user",currentUser);
        startActivity(intent);
    }

    protected void changeEmailLoginActivity(){
        Intent intent = new Intent(this, EmailLoginActivity.class);
        startActivity(intent);
    }

    protected void changeSignUpActivity(){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

}
