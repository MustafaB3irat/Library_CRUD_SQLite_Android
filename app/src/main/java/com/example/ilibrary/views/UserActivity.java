package com.example.ilibrary.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.ilibrary.R;
import com.example.ilibrary.databinding.SidebarHeaderBinding;
import com.example.ilibrary.databinding.UserActivityBinding;
import com.example.ilibrary.views.fragments.AboutUsFragment;
import com.example.ilibrary.views.fragments.MyCartFragment;
import com.example.ilibrary.views.fragments.MyProfile;
import com.example.ilibrary.views.fragments.SearchForBooksFragment;
import com.example.ilibrary.views.fragments.ViewBooksFragment;
import com.example.ilibrary.views.fragments.WelcomeFragment;

public class UserActivity extends AppCompatActivity implements com.example.ilibrary.interfaces.UserActivity.UserActivityView {

    private UserActivityBinding userActivityBinding;
    private SidebarHeaderBinding sidebarHeaderBinding;
    private View sidebar_header;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userActivityBinding = DataBindingUtil.setContentView(this, R.layout.user_activity);
        sidebar_header = userActivityBinding.sidebar.inflateHeaderView(R.layout.sidebar_header);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragments, new WelcomeFragment()).commitNow();

        setSupportActionBar(userActivityBinding.toolbar);
        initMenuButton();
        initMenuItems();

    }

    @Override
    public void initMenuButton() {

        userActivityBinding.menu.setOnClickListener(e -> {

            TextView view = sidebar_header.findViewById(R.id.header_username);
            view.setText(SignInActivity.username);
            userActivityBinding.drawer.openDrawer(GravityCompat.START);

        });
    }

    @Override
    public void onBackPressed() {

        if (userActivityBinding.drawer.isDrawerOpen(GravityCompat.START)) {
            userActivityBinding.drawer.closeDrawer(GravityCompat.START);
        } else {
            SignInActivity.username = "";
            super.onBackPressed();
        }
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

                case R.id.logout: {
                    userActivityBinding.drawer.closeDrawer(GravityCompat.START);
                    this.getIntent().setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    SignInActivity.username = "";
                    finish();
                    overridePendingTransition(0, 0);
                    break;
                }

                case R.id.about_us: {
                    userActivityBinding.drawer.closeDrawer(GravityCompat.START);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragments, new AboutUsFragment()).commitNow();
                    break;
                }

                case R.id.search_book: {
                    userActivityBinding.drawer.closeDrawer(GravityCompat.START);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragments, new SearchForBooksFragment()).commitNow();
                    break;
                }

                case R.id.my_profile: {
                    userActivityBinding.drawer.closeDrawer(GravityCompat.START);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragments, new MyProfile()).commitNow();
                    break;
                }


            }

            return true;
        });
    }


}
