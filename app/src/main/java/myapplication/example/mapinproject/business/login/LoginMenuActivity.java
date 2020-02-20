package myapplication.example.mapinproject.business.login;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import myapplication.example.mapinproject.R;

public class LoginMenuActivity extends LoginManegerActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ログインチェック
        if (isLogin()){
            changeUI();
        }

        setContentView(R.layout.login_menu);
        findViewById(R.id.loginEmail).setOnClickListener((View.OnClickListener) this);
        findViewById(R.id.loginGoogle).setOnClickListener((View.OnClickListener) this);
        findViewById(R.id.loginTwitter).setOnClickListener((View.OnClickListener) this);
        findViewById(R.id.loginFacebook).setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginEmail:
                changeEmailLoginActivity();
                break;
            case R.id.loginGoogle:
                googleSignIn();
                break;
            case R.id.loginTwitter:
                twitterSignIn();
                break;
            case R.id.loginFacebook:
                facebookSignIn();
                break;
        }
    }


    private boolean isLogin() {
        FirebaseUser prevUser = FirebaseAuth.getInstance().getCurrentUser();
        return  (prevUser != null) ;
    }
}
