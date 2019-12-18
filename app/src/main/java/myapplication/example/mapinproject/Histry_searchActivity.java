package myapplication.example.mapinproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Histry_searchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_search);

        Button sendButton = findViewById(R.id.hisbuckbutton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),Login2Activity.class);
                startActivity(intent);
            }
        });
        Button sendButton2 = findViewById(R.id.hisbutton1);
        sendButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),Login2Activity.class);
                startActivity(intent);
            }
        });  Button sendButton3 = findViewById(R.id.hisbutton2);
        sendButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),Login2Activity.class);
                startActivity(intent);
            }
        });  Button sendButton4 = findViewById(R.id.hisbutton3);
        sendButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),Login2Activity.class);
                startActivity(intent);
            }
        });  Button sendButton5 = findViewById(R.id.hisbutton4);
        sendButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),Login2Activity.class);
                startActivity(intent);
            }
        });  Button sendButton6 = findViewById(R.id.hisbutton5);
        sendButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),Login2Activity.class);
                startActivity(intent);
            }
        });  Button sendButton7 = findViewById(R.id.hisbutton6);
        sendButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),Login2Activity.class);
                startActivity(intent);
            }
        });  Button sendButton8 = findViewById(R.id.hisbutton7);
        sendButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),Login2Activity.class);
                startActivity(intent);
            }
        });  Button sendButton9= findViewById(R.id.hisbutton8);
        sendButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),Login2Activity.class);
                startActivity(intent);
            }
        });  Button sendButton10 = findViewById(R.id.hisbutton9);
        sendButton10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),Login2Activity.class);
                startActivity(intent);
            }
        });  Button sendButton11 = findViewById(R.id.hisbutton10);
        sendButton11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),Login2Activity.class);
                startActivity(intent);
            }
        });  Button sendButton12 = findViewById(R.id.hisbutton11);
        sendButton12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),Login2Activity.class);
                startActivity(intent);
            }
        });
        Button sendButton13 = findViewById(R.id.hishomebutton);
        sendButton12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),HomeActivity.class);
                startActivity(intent);
            }
        });




    }
}
