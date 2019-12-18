package myapplication.example.mapinproject;

import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import androidx.fragment.app.FragmentActivity;

import org.w3c.dom.Text;


public class LoginActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button sendButton = findViewById(R.id.ybutton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),Login2Activity.class);
                startActivity(intent);
            }
        });
    }
}


