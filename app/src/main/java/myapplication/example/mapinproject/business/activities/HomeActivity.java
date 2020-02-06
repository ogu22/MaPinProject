package myapplication.example.mapinproject.business.activities;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.MediaRouteButton;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import myapplication.example.mapinproject.R;
import myapplication.example.mapinproject.business.fragments.PostDoneDialog;
import myapplication.example.mapinproject.ui.home.HomeFragment;
import myapplication.example.mapinproject.ui.notice.NoticeFragment;
import myapplication.example.mapinproject.ui.post.PostFragment;
import myapplication.example.mapinproject.ui.postadd.PostAddFragment;
import myapplication.example.mapinproject.ui.profile.ProfileFragment;
import myapplication.example.mapinproject.ui.search.SearchFragment;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);

            // 押したら現在地がわかるように
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_home)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
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
        FloatingActionButton fab = findViewById(R.id.fab);
        switch (itemId) {
            // ログアウトタップ
            case R.id.action_logout:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.searchbutton:
                fab.setVisibility(View.GONE);
                FragmentTransaction post = getSupportFragmentManager().beginTransaction();
                post.replace(R.id.nav_host_fragment, new SearchFragment());
                post.commit();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        FloatingActionButton fab = findViewById(R.id.fab);
        switch (itemId) {
            case R.id.nav_home:
                fab.setVisibility(View.VISIBLE);
                // Activity内部での処理を想定
                FragmentTransaction home = getSupportFragmentManager().beginTransaction();
                home.replace(R.id.nav_host_fragment, new HomeFragment());
                home.commit();
                break;
            case R.id.nav_notice:
                fab.setVisibility(View.GONE);
                // Activity内部での処理を想定
                FragmentTransaction notice = getSupportFragmentManager().beginTransaction();
                notice.replace(R.id.nav_host_fragment, new NoticeFragment());
                notice.commit();
                break;
            case R.id.nav_profile:
                fab.setVisibility(View.GONE);
                // Activity内部での処理を想定
                FragmentTransaction profile = getSupportFragmentManager().beginTransaction();
                profile.replace(R.id.nav_host_fragment, new ProfileFragment());
                profile.commit();
                break;
            case R.id.nav_postadd:
                fab.setVisibility(View.GONE);
                // Activity内部での処理を想定
                FragmentTransaction postadd = getSupportFragmentManager().beginTransaction();
                postadd.replace(R.id.nav_host_fragment, new PostAddFragment());
                postadd.commit();
                break;
        }
        drawer.closeDrawer(Gravity.LEFT);
        return false;
    }
}