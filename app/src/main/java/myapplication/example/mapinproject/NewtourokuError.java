package myapplication.example.mapinproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewtourokuError extends AppCompatActivity {
    EditText password;
    EditText meleaddless;
    EditText username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newtouroku_error);
        username  =findViewById(R.id.editText);
        password = findViewById(R.id.editText3);
        meleaddless = findViewById(R.id.editText2);

        Button sendButton = findViewById(R.id.button12);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mele = meleaddless.getText().toString();

                String user = username.getText().toString();
                String pass = password.getText().toString();
                ErrorEXP errorEXP = new ErrorEXP();
                if(errorEXP.Errorcheck(mele) && errorEXP.Errorcheck(pass)&& errorEXP.Errorcheck(user) ) {
                    Intent intent = new Intent(getApplication(), HomeActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getApplication(), NewtourokuError.class);
                    startActivity(intent);
                }
                }

        });
    }
}
