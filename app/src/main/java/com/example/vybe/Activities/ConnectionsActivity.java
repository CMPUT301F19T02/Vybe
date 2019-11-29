package com.example.vybe.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.example.vybe.Adapters.ConnectionsPageAdapter;
import com.example.vybe.Models.User;
import com.example.vybe.R;
import com.google.android.material.tabs.TabLayout;

/**
 * ConnectionsActivity displays the follower and following lists
 */
public class ConnectionsActivity extends AppCompatActivity {

    private TabLayout connectionsTabLayout;
    private ViewPager connectionsViewPager;

    private ConnectionsPageAdapter pageAdapter;
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connections);

        connectionsTabLayout = findViewById(R.id.connections_tab_layout);
        connectionsViewPager = findViewById(R.id.connections_view_pager);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras.containsKey("user")) {
            user = (User) extras.getSerializable("user");
        }

        pageAdapter = new ConnectionsPageAdapter(
                getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                this,
                user
        );
        connectionsViewPager.setAdapter(pageAdapter);
        connectionsTabLayout.setupWithViewPager(connectionsViewPager);
    }
}
