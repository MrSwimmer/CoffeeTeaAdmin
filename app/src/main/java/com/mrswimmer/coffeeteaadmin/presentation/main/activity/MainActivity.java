package com.mrswimmer.coffeeteaadmin.presentation.main.activity;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.mrswimmer.coffeeteaadmin.App;
import com.mrswimmer.coffeeteaadmin.R;
import com.mrswimmer.coffeeteaadmin.data.settings.Screens;
import com.mrswimmer.coffeeteaadmin.data.settings.Settings;
import com.mrswimmer.coffeeteaadmin.presentation.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements MainActivityView {

    @InjectPresenter
    MainActivityPresenter presenter;
    private ActionBarDrawerToggle drawerToggle;

    @Inject
    SharedPreferences settings;

    @ProvidePresenter
    public MainActivityPresenter presenter() {
        return new MainActivityPresenter();
    }

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navView)
    NavigationView navigationView;

    View headerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        presenter.setupDrawerContent(navigationView);
        headerLayout = navigationView.getHeaderView(0);
        drawerToggle = setupDrawerToggle();
        drawerLayout.addDrawerListener(drawerToggle);
        TextView username = headerLayout.findViewById(R.id.header_username);
        username.setText(settings.getString(Settings.USERNAME, "user"));
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    @Override
    protected int getContainerId() {
        return Screens.CONTAINER_MAIN;
    }

    @Override
    protected void injectDependencies() {
        App.getComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void checkDrawerItem(MenuItem menuItem) {
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


}
