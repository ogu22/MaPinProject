package myapplication.example.mapinproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginErorrorActivity extends AppCompatActivity {
    EditText password;
    EditText meleaddless;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_erorror);

        password = findViewById(R.id.neditText4);
        meleaddless = findViewById(R.id.neditText3);

        Button sendButton = findViewById(R.id.loginbutton4);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mele = meleaddless.getText().toString();
                ErrorEXP errorEXP = new ErrorEXP();
                String pass = password.getText().toString();
                if(errorEXP.Errorcheck(mele) && errorEXP.Errorcheck(pass) ) {
                    Intent intent = new Intent(getApplication(), HomeActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getApplication(), LoginErorrorActivity.class);
                    startActivity(intent);
                }
            }
        });


        Button sendButton2 = findViewById(R.id.tourokubutton5);
        sendButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), NewtourokuActivity.class);
                startActivity(intent);
            }
        });

    }
}

