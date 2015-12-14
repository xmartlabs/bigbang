package com.scottruth.timeoffandroid.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.annimon.stream.Objects;
import com.f2prateek.dart.InjectExtra;
import com.scottruth.timeoffandroid.R;
import com.scottruth.timeoffandroid.controller.SessionController;
import com.scottruth.timeoffandroid.helper.UiHelper;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseAppCompatActivity implements ListView.OnItemClickListener {
    @Nullable
    @InjectExtra("initialDrawerItem")
    DrawerItem initialDrawerItem;

    @Bind(R.id.drawer)
    View drawer;
    @Bind(R.id.drawer_listView)
    ListView drawerListView;
    @Bind(R.id.activity_main_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_tabLayout)
    TabLayout toolbarTabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    @Inject
    SessionController sessionController;

    private DrawerAdapter drawerAdapter;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerItem selectedDrawerItem = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        toolbarTabLayout.setupWithViewPager(viewPager);

        TabLayout.ViewPagerOnTabSelectedListener onTabSelectedListener = new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                DrawerItem drawerItem = DrawerItem.fromInteger(tab.getPosition());
                selectDrawerItemIfSelectable(drawerItem);
                if (drawerItem.getDrawableResId() != null) {
                    tab.setIcon(drawerItem.getDrawableResId());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
                DrawerItem drawerItem = DrawerItem.fromInteger(tab.getPosition());
                if (drawerItem.getInactiveDrawableResId() != null) {
                    tab.setIcon(drawerItem.getInactiveDrawableResId());
                }
            }
        };
        toolbarTabLayout.setOnTabSelectedListener(onTabSelectedListener);

        drawerAdapter = new DrawerAdapter();
        drawerListView.setAdapter(drawerAdapter);

        drawerListView.setOnItemClickListener(this);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                hideKeyboard();
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);

        if (initialDrawerItem == null) {
            initialDrawerItem = DrawerItem.HOME;
        }
        displayDrawerView(initialDrawerItem);

        for (int i = 0; i < toolbarTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = toolbarTabLayout.getTabAt(i);
            if (tab != null) {
                DrawerItem drawerItem = DrawerItem.fromInteger(i);
                if (drawerItem == initialDrawerItem) {
                    onTabSelectedListener.onTabSelected(tab);
                } else {
                    onTabSelectedListener.onTabUnselected(tab);
                }
            }
        }

        UiHelper.checkGooglePlayServicesAndShowAlertIfNeeded(this);

        preloadViewPager();
    }

    private void preloadViewPager() {
        viewPager.setOffscreenPageLimit(2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                displayDrawerView(DrawerItem.SETTINGS);
                return true;
            default:
                return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
        }
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

    @Override
    public void onItemClick(@Nullable AdapterView<?> adapterView, @Nullable View view, int position, long id) {
        DrawerItem item = drawerAdapter.getItem(position);
        displayDrawerView(item);
    }

    public void displayDrawerView(@NonNull DrawerItem drawerItem) {
        if (drawerItem.getDrawerItemType() != DrawerItemType.DIVIDER) {
            if (Objects.equals(selectedDrawerItem, drawerItem)) {
                drawerLayout.closeDrawer(drawer);
            } else {
                switch (drawerItem) {
                    case HOME: {
                        viewPager.setCurrentItem(drawerItem.getValue());
                        break;
                    }
                    case REPOS: {
                        viewPager.setCurrentItem(drawerItem.getValue());
                        break;
                    }
                    case SETTINGS:
                        Intent intent = Henson.with(getContext()).gotoSettingsActivity()
                                .build();
                        startActivity(intent);
                        break;
                }

                selectDrawerItemIfSelectable(drawerItem);

                drawerLayout.closeDrawer(drawer);
            }
        }
    }

    private void selectDrawerItemIfSelectable(@NonNull DrawerItem drawerItem) {
        if (drawerItem.getSelectable()) {
            drawerListView.setItemChecked(drawerAdapter.getItemPosition(drawerItem), true);

            selectedDrawerItem = drawerItem;
        } else {
            drawerListView.setItemChecked(drawerAdapter.getItemPosition(selectedDrawerItem), true);
        }
    }
}
