package myapplication.example.mapinproject.business.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import myapplication.example.mapinproject.R;
import myapplication.example.mapinproject.business.locset.Locset;
import myapplication.example.mapinproject.ui.home.HomeFragment;
import myapplication.example.mapinproject.ui.notice.NoticeFragment;
import myapplication.example.mapinproject.ui.postadd.PostAddFragment;
import myapplication.example.mapinproject.ui.profile.ProfileFragment;
import myapplication.example.mapinproject.ui.search.SearchFragment;

public class HomeActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView3);
        //パーミッション、位置情報h許可Activityの呼び出し
        Locset.request(HomeActivity.this, Locset.SettingPriority.HIGH_ACCURACY, REQUEST_CODE);

        //ナビゲーションドロワー
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_home)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_logout:
                // ログアウトタップ
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.searchbutton:
                // 検索タップ
                FragmentTransaction search = getSupportFragmentManager().beginTransaction();
                search.replace(R.id.nav_host_fragment, new SearchFragment());
                search.addToBackStack(null);
                search.commit();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            Log.d("LocsetSample", "locset result: " + resultCode);
            switch (resultCode) {
                case Locset.ResultCode.SUCCESS:
                    // setting ok
                    Toast.makeText(this, "locset success", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    // setting ng
                    Toast.makeText(this, "locset failure", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}