package com.example.ilibrary.views;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.ilibrary.R;
import com.example.ilibrary.databinding.UserActivityBinding;
import com.example.ilibrary.views.fragments.MyCartFragment;
import com.example.ilibrary.views.fragments.ViewBooksFragment;

public class UserActivity extends AppCompatActivity implements com.example.ilibrary.interfaces.UserActivity.UserActivityView {

    private UserActivityBinding userActivityBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userActivityBinding = DataBindingUtil.setContentView(this, R.layout.user_activity);
        initMenuButton();
        setSupportActionBar(userActivityBinding.toolbar);


        initMenuItems();

    }

    @Override
    public void initMenuButton() {
        userActivityBinding.menu.setOnClickListener(e -> {
            userActivityBinding.sidebar.setVisibility(View.VISIBLE);
            userActivityBinding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            userActivityBinding.drawer.openDrawer(GravityCompat.START);
        });
    }

    @Override
    public void onBackPressed() {

        if (userActivityBinding.drawer.isDrawerOpen(GravityCompat.START)) {

            userActivityBinding.drawer.closeDrawer(GravityCompat.START);
            userActivityBinding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }


    }

    @Override
    public void switchFragments(int fragmentNum) {

    }

    @Override
    public void initMenuItems() {

        userActivityBinding.sidebar.setNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {

                case R.id.browse_books: {
                    userActivityBinding.drawer.closeDrawer(GravityCompat.START);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragments, new ViewBooksFragment()).commitNow();
                    break;
                }

                case R.id.cart: {
                    userActivityBinding.drawer.closeDrawer(GravityCompat.START);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragments, new MyCartFragment()).commitNow();
                    break;
                }

            }

            return true;
        });
    }
}
