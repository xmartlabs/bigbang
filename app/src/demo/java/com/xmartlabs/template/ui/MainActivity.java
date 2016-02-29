package com.xmartlabs.template.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.f2prateek.dart.InjectExtra;
import com.xmartlabs.template.R;
import com.xmartlabs.template.controller.SessionController;
import com.xmartlabs.template.helper.UiHelper;
import com.xmartlabs.template.ui.repo.list.RepoListFragmentBuilder;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseAppCompatActivity {
  @Nullable
  @InjectExtra
  DrawerItem initialDrawerItem;

  @Bind(R.id.toolbar)
  Toolbar toolbar;
  @Bind(R.id.drawer)
  View drawer;
  @Bind(R.id.website_textView)
  TextView websiteTextView;
  @Bind(R.id.drawer_recyclerView)
  RecyclerView drawerRecyclerView;
  @Bind(R.id.activity_main_layout)
  DrawerLayout drawerLayout;

  @Inject
  SessionController sessionController;

  private DrawerAdapter drawerAdapter;
  private ActionBarDrawerToggle drawerToggle;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    setSupportActionBar(toolbar);

    drawerAdapter = new DrawerAdapter(this::onItemClick);
    drawerRecyclerView.setAdapter(drawerAdapter);
    drawerRecyclerView.setHasFixedSize(true);

    final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    drawerRecyclerView.setLayoutManager(layoutManager);

    drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close) {
      @Override
      public void onDrawerSlide(View drawerView, float slideOffset) {
        super.onDrawerSlide(drawerView, slideOffset);
        hideKeyboard();
      }
    };

    drawerLayout.addDrawerListener(drawerToggle);

    if (initialDrawerItem == null) {
      initialDrawerItem = DrawerItem.HOME;
    }
    displayDrawerItemView(initialDrawerItem);

    UiHelper.checkGooglePlayServicesAndShowAlertIfNeeded(this);
  }

  @OnClick(R.id.website_textView)
  @SuppressWarnings("unused")
  void visitWebsite() {
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setData(Uri.parse(websiteTextView.getText().toString()));
    startActivity(intent);
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
        displayDrawerItemView(DrawerItem.SETTINGS);
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

  public void onItemClick(@NonNull DrawerItem drawerItem) {
    displayDrawerItemView(drawerItem);
  }

  public void displayDrawerItemView(@NonNull DrawerItem drawerItem) {
    if (drawerItem.getDrawerItemType() != DrawerItemType.DIVIDER) {
      if (!drawerAdapter.isSelected(drawerItem)) {
        FragmentWithDrawer fragmentWithDrawer = null;
        switch (drawerItem) {
          // TODO: change fragments according to selected drawer item
          case HOME: {
            fragmentWithDrawer = new HomeFragmentBuilder().build();
            break;
          }
          case REPOS: {
            fragmentWithDrawer = new RepoListFragmentBuilder().build();
            break;
          }
          case SETTINGS:
            Intent intent = Henson.with(getContext()).gotoSettingsActivity()
                .build();
            startActivity(intent);
            break;
          default:
        }

        if (fragmentWithDrawer != null) {
          FragmentManager fragmentManager = getSupportFragmentManager();
          fragmentManager
              .beginTransaction()
              .replace(R.id.fragment_container, fragmentWithDrawer)
              .commit();
          toolbar.setTitle(fragmentWithDrawer.getTitle());
        }

        drawerAdapter.selectItemIfSelectable(drawerItem);
      }
      drawerLayout.closeDrawer(drawer);
    }
  }
}
