package com.malpo.dogfeeder;

import com.metova.slim.Slim;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainFragment.OnFoodGoneListener {

    private static final String HOME_TAG = "home_fragment";

    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Slim.injectExtras(getIntent().getExtras(), this);

        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle(null);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mToolbarLayout.setTitle("");

        replaceFragment(new MainFragment(), HOME_TAG, R.id.nav_home);

//        onNavigationItemSelected(navigationView.getMenu().getItem(0));

        //testing rebase shtuffs

        //Added this on develop

        //Added this on develop 1

        //Added this on develop again

        //Added this on develop 2

    }

    @OnClick(R.id.fab)
    public void onClick(View view){
        showDialog();
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Feed your dog now?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                feedDog();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void feedDog(){
        MainFragment fragment;
        if(getSupportFragmentManager().findFragmentByTag(HOME_TAG) != null) {
            fragment = (MainFragment) getSupportFragmentManager().findFragmentByTag(HOME_TAG);
            if(fragment != null) {
                fragment.feedDog();
            }
        } else {
            fragment = new MainFragment();
            replaceFragment(fragment, HOME_TAG, R.id.nav_home);
            fragment.feedDog();
        }
    }

    @Override
    public void onFoodGone() {
        Snackbar.make(mToolbarLayout, "You are out of food!", Snackbar.LENGTH_LONG)
                .setAction("Ok", null).show();
    }

    private void replaceFragment(Fragment fragment, @Nullable String tag, int menuId){
        //TODO use tags to see if fragment is already there before making a new one.
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(tag == null)
            ft.replace(R.id.frame, fragment);
        else
            ft.replace(R.id.frame, fragment, tag);
        ft.commit();
        navigationView.setCheckedItem(menuId);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            replaceFragment(new MainFragment(), HOME_TAG, id);
        }else if (id == R.id.nav_schedule) {
            replaceFragment(new ScheduleFragment(), null, id);
        }else if (id == R.id.nav_settings) {
            replaceFragment(new SettingsFragment(), null, id);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
