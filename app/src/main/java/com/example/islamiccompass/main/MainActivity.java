package com.example.islamiccompass.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.example.islamiccompass.R;
import com.example.islamiccompass.fragments.BooksFragment;
import com.example.islamiccompass.fragments.CompassFragment;
import com.example.islamiccompass.fragments.SettingsFragment;
import com.example.islamiccompass.helper.DataPassListener;
import com.example.islamiccompass.fragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener, CompassFragment.OnFragmentInteractionListener, DataPassListener{

//    private DrawerLayout drawerLayout;
    private boolean isMorningMode;

    @Override
    protected void attachBaseContext(Context baseContext) {
        super.attachBaseContext(baseContext);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(baseContext);
        isMorningMode = prefs.getBoolean("isMorningMode", false);

        if (isMorningMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListner);

        Fragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_view, homeFragment)
                .commit();

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(findViewById(R.id.toolbar));
//        drawerLayout = findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
//                drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
//
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//
//        NavigationView navigationView = findViewById(R.id.navigation_view);
//        navigationView.setNavigationItemSelectedListener(this);

    }

//    @Override
//    public void onBackPressed() {
//
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

    private BottomNavigationView.OnItemSelectedListener navListner =
            new BottomNavigationView.OnItemSelectedListener(){

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_books:
                            selectedFragment = new BooksFragment();
                            break;
                        case R.id.nav_settings:
                            selectedFragment = new SettingsFragment();
                            break;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view,
                            selectedFragment).commit();
                    return true;
                }
            };

    public void passPref(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean isMorningMode = pref.getBoolean("isMorningMode", false);

        if(isMorningMode){
            System.out.println("Night Mode ------> Light Mode");
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        }else {
            System.out.println("Light Mode ------> Night Mode");
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }

        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public void messageFromChildFragment(Uri uri) {

    }

    @Override
    public void messageFromParentFragment(Uri uri) {

    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        Fragment selectedFragment = null;
//
//        switch (item.getItemId()) {
//            case R.id.nav_home:
//                selectedFragment = new HomeFragment();
//                break;
//            case R.id.nav_books:
//                selectedFragment = new BooksFragment();
//                break;
//            case R.id.nav_settings:
//                selectedFragment = new SettingsFragment();
//                break;
//            default:
//                throw new IllegalArgumentException("menu option not implemented!!");
//        }
//
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view,
//                selectedFragment).commit();
//        drawerLayout.closeDrawer(GravityCompat.START);
//
//        return true;
//    }

}
