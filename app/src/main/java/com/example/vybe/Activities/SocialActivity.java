package com.example.vybe.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.vybe.Adapters.MyVibesAdapter;
import com.example.vybe.Models.VibeEvent;
import com.example.vybe.R;
import com.example.vybe.Controllers.VibeEventListController;

import java.util.ArrayList;

/**
 * SocialActivity displays the screen for a user to view a list of their followers' vibe events
 */
public class SocialActivity extends AppCompatActivity implements VibeEventListController.OnSocialVibeEventsUpdatedListener {

    private static final String TAG = "SocialActivity";

    private ArrayList<VibeEvent> vibeEventList = VibeEventListController.setOnSocialVibeEventsUpdatedListener(this);
    private MyVibesAdapter socialVibesAdapter;

    private ImageButton myVibesBtn;
    private ImageButton mapBtn;
    private ImageButton searchBtn;
    private RecyclerView socialVibesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);
        Log.d(TAG, "onCreate: In social");

        myVibesBtn = findViewById(R.id.my_vibes_btn);
        mapBtn = findViewById(R.id.social_map_btn);
        searchBtn = findViewById(R.id.search_btn);
        socialVibesRecyclerView = findViewById(R.id.social_vibe_list);

        buildRecyclerView();

        myVibesBtn.setOnClickListener((View v) -> {
            finish();
        });

        mapBtn.setOnClickListener((View v) -> {
            Intent MapViewIntent = new Intent(SocialActivity.this, MapViewActivity.class);
            MapViewIntent.putExtra("MapViewMode", "Social");
            startActivity(MapViewIntent);
        });

        searchBtn.setOnClickListener((View v) -> {
            startActivity(new Intent(SocialActivity.this, SearchProfilesActivity.class));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Update the vibe event list every time the user enters this activity
        VibeEventListController.updateSocialVibeEvents();
    }

    @Override
    public void onSocialVibeEventsUpdated() {
        socialVibesAdapter.notifyDataSetChanged();
    }

    private void buildRecyclerView() {
        socialVibesAdapter = new MyVibesAdapter(this, R.layout.social_vibe_event_item, vibeEventList);
        socialVibesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        socialVibesRecyclerView.setAdapter(socialVibesAdapter);

        DividerItemDecoration itemDecor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        socialVibesRecyclerView.addItemDecoration(itemDecor);
    }

}
