package com.idejie.android.light;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.idejie.android.light.fragment.CameraFragment;
import com.idejie.android.light.fragment.IndexFragment;
import com.idejie.android.light.fragment.LightFragment;
import com.idejie.android.light.fragment.MeFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private SimpleDateFormat formater = new SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss");
    public static final String POSITION = "position";
    IndexFragment indexFragment;
    MeFragment meFragment;
    LightFragment lightFragment;
    CameraFragment cameraFragment;
    Toolbar toolbar;
    int postion;
    final int INDEX=0,LIGHT=1,CAMERA=2,ME=3;
    FragmentManager fm ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "欢迎前往github进行star", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent data=new Intent(Intent.ACTION_SENDTO);
                data.setData(Uri.parse("mailto:i@idejie.com"));
                data.putExtra(Intent.EXTRA_SUBJECT, "关于Light的意见反馈");
                data.putExtra(Intent.EXTRA_TEXT, "我在"+formater.format(new Date())+"发现了一个Bug：\n");
                startActivity(data);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fm = getFragmentManager();
        showFragment(1);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(POSITION,postion);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        showFragment(savedInstanceState.getInt("position"));
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void showFragment(int i) {

        FragmentTransaction transaction =fm.beginTransaction();
        hideFragment(transaction);
        postion =i;
        switch (postion){
            case INDEX:
                toolbar.setTitle("首页");
                if (indexFragment==null){
                    indexFragment=new IndexFragment();
                    transaction.add(R.id.fragment_main,indexFragment);
                }else {
                    transaction.show(indexFragment);
                }
                break;
            case LIGHT:
                toolbar.setTitle("亮度");
                if (lightFragment==null){
                    lightFragment=new LightFragment();
                    transaction.add(R.id.fragment_main,lightFragment);
                }else {
                    transaction.show(lightFragment);
                }
                break;
            case ME:
                toolbar.setTitle("我的");
                if (meFragment==null){
                    meFragment=new MeFragment();
                    transaction.add(R.id.fragment_main,meFragment);
                }else {
                    transaction.show(meFragment);
                }
                break;
            case CAMERA:
                toolbar.setTitle("识别");
                if (cameraFragment==null){
                    cameraFragment=new CameraFragment();
                    transaction.add(R.id.fragment_main,cameraFragment);
                }else {
                    transaction.show(cameraFragment);
                }
        }

        transaction.commit();

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            showFragment(INDEX);
        } else if (id == R.id.nav_gallery) {
            showFragment(LIGHT);

        } else if (id == R.id.nav_slideshow) {

            showFragment(CAMERA);
            Toast.makeText(this,""+CAMERA,Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_setting) {
            showFragment(ME);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void hideFragment(FragmentTransaction ft) {
        if (meFragment!=null) ft.hide(meFragment);
        if (lightFragment!=null)ft.hide(lightFragment);
        if (indexFragment!=null) ft.hide(indexFragment);
        if (cameraFragment!=null) ft.hide(cameraFragment);

    }


}
