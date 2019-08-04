package com.example.ilibrary.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;

import com.example.ilibrary.R;
import com.example.ilibrary.databinding.AdminActivityBinding;
import com.example.ilibrary.databinding.SidebarHeaderBinding;
import com.example.ilibrary.interfaces.Admin;
import com.example.ilibrary.views.fragments.AboutUsFragment;
import com.example.ilibrary.views.fragments.BlockUnBlockUserFragment;
import com.example.ilibrary.views.fragments.MyCartFragment;
import com.example.ilibrary.views.fragments.MyProfile;
import com.example.ilibrary.views.fragments.ReservedBooksAdminFragment;
import com.example.ilibrary.views.fragments.SearchForBooksFragment;
import com.example.ilibrary.views.fragments.ViewBooksFragment;
import com.example.ilibrary.views.fragments.WelcomeFragment;

public class AdminActivity extends AppCompatActivity implements Admin.AdminView {


    private AdminActivityBinding ad;
    private SidebarHeaderBinding sidebarHeaderBinding;
    private View sidebar_header;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ad = DataBindingUtil.setContentView(this, R.layout.admin_activity);
        sidebar_header = ad.sidebar.inflateHeaderView(R.layout.sidebar_header);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragments, new WelcomeFragment()).commitNow();

        setSupportActionBar(ad.toolbar);
        initMenuButton();
        initMenuItems();

    }

    @Override
    public void initMenuButton() {

        ad.menu.setOnClickListener(e -> {

            TextView view = sidebar_header.findViewById(R.id.header_username);
            view.setText(SignInActivity.username);
            ad.drawer.openDrawer(GravityCompat.START);

        });
    }

    @Override
    public void onBackPressed() {

        if (ad.drawer.isDrawerOpen(GravityCompat.START)) {
            ad.drawer.closeDrawer(GravityCompat.START);
        } else {
            SignInActivity.username = "";
            super.onBackPressed();
        }
    }

    @Override
    public void initMenuItems() {

        ad.sidebar.setNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {

                case R.id.reserved_books_admin: {
                    ad.drawer.closeDrawer(GravityCompat.START);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragments, new ReservedBooksAdminFragment()).commitNow();
                    break;
                }

                case R.id.block_unblock_users: {
                    ad.drawer.closeDrawer(GravityCompat.START);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragments, new BlockUnBlockUserFragment()).commitNow();
                    break;
                }

                case R.id.logout_admin: {
                    ad.drawer.closeDrawer(GravityCompat.START);
                    this.getIntent().setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    SignInActivity.username = "";
                    finish();
                    overridePendingTransition(0, 0);
                    break;
                }


            }
            return true;
        });
    }
}
