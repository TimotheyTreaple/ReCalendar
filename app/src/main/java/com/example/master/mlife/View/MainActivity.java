package com.example.master.mlife.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.master.mlife.Fragments.CalendarFragment;
import com.example.master.mlife.Fragments.DayScheduleFragment;
import com.example.master.mlife.Fragments.FriendsListFragment;
import com.example.master.mlife.Fragments.MainScheduleFragment;
import com.example.master.mlife.Fragments.MyProfileFragment;
import com.example.master.mlife.Fragments.NewCreateFragment;
import com.example.master.mlife.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager fragmentManager = getSupportFragmentManager();

    LinearLayout mMondayLayout;
    LinearLayout mTuesdayLayout;
    LinearLayout mWednesdayLayout;
    LinearLayout mThursdayLayout;
    LinearLayout mFridayLayout;
    LinearLayout mSaturdayLayout;
    LinearLayout mSundayLayout;

    Fragment fragment = null;
    Class fragmentClass = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentClass=NewCreateFragment.class;
                setTitle("New Create");

                setFragment();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mMondayLayout=(LinearLayout)findViewById(R.id.monday_button_go);
        mTuesdayLayout=(LinearLayout)findViewById(R.id.tuesday_button_go);
        mWednesdayLayout=(LinearLayout)findViewById(R.id.wednesday_button_go);
        mThursdayLayout=(LinearLayout)findViewById(R.id.thursday_button_go);
        mFridayLayout=(LinearLayout)findViewById(R.id.friday_button_go);
        mSaturdayLayout=(LinearLayout)findViewById(R.id.saturday_button_go);
        mSundayLayout=(LinearLayout)findViewById(R.id.sunday_button_go);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==R.id.item_calendar) {
            fragmentClass=CalendarFragment.class;
        }
        else if (id==R.id.item_calendar_list) {
            fragmentClass=MainScheduleFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Вставляем фрагмент, заменяя текущий фрагмент
        fragmentManager.beginTransaction().replace(R.id.fragment_layout, fragment).commit();
        // Выделяем выбранный пункт меню в шторке
        item.setChecked(true);
        // Выводим выбранный пункт в заголовке
        setTitle(item.getTitle());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        //noinspection SimplifiableIfStatemen

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_profile) {
            fragmentClass = MyProfileFragment.class;
        } else if (id == R.id.nav_main_schedule) {
            fragmentClass = MainScheduleFragment.class;
        } else if (id == R.id.nav_friends_list) {
            fragmentClass=FriendsListFragment.class;
        }else if (id==R.id.nav_registration){
            Intent intent = new Intent(this, RegistrationMain.class);
            startActivity(intent);
            return true;
        }
        // Выделяем выбранный пункт меню в шторке
        item.setChecked(true);
        // Выводим выбранный пункт в заголовке
        setTitle(item.getTitle());

        setFragment();
        return true;
    }

    public void setFragment(){
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Вставляем фрагмент, заменяя текущий фрагмент
        fragmentManager.beginTransaction().replace(R.id.fragment_layout, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


    }
    public void onDayLayoutClick(View view){
        switch (view.getId()){
            case R.id.monday_button_go:
                fragmentClass = DayScheduleFragment.class;
                break;
            case R.id.tuesday_button_go:
                fragmentClass = DayScheduleFragment.class;
                break;
            case R.id.wednesday_button_go:
                fragmentClass = DayScheduleFragment.class;
                break;
            case R.id.thursday_button_go:
                fragmentClass = DayScheduleFragment.class;
                break;
            case R.id.friday_button_go:
                fragmentClass = DayScheduleFragment.class;
                break;
            case R.id.saturday_button_go:
                fragmentClass = DayScheduleFragment.class;
                break;
            case R.id.sunday_button_go:
                fragmentClass = DayScheduleFragment.class;
                break;
        }
        setFragment();
    }


}
