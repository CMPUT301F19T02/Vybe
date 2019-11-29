package com.example.vybe.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vybe.Controllers.RequestController;
import com.example.vybe.Models.User;
import com.example.vybe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Locale;

/**
 * This activity still does nothing but will be fixed later
 */
public class ViewProfileActivity extends AppCompatActivity {

    private TextView usernameTextView;
    private TextView emailTextView;
    private Button logoutBtn;
    private Button sendRequestBtn;

    private LinearLayout followersLayout;
    private LinearLayout followingLayout;
    private LinearLayout statisticsLayout;
    private TextView vibeCount;
    private TextView followerCount;
    private TextView followingCount;

    private User user;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser mUser = mAuth.getCurrentUser();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RequestController requestController = RequestController.getInstance(ViewProfileActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        usernameTextView = findViewById(R.id.username_profile);
        emailTextView = findViewById(R.id.email_profile);
        logoutBtn = findViewById(R.id.logout_btn);
        sendRequestBtn = findViewById(R.id.send_request_btn);
        followersLayout = findViewById(R.id.profile_followers_layout);
        followingLayout = findViewById(R.id.profile_following_layout);
        vibeCount = findViewById(R.id.vibe_count);
        followerCount = findViewById(R.id.follower_count);
        followingCount = findViewById(R.id.following_count);
        statisticsLayout = findViewById(R.id.statistics_layout);

        Bundle extras = getIntent().getExtras();

        if (extras.containsKey("user")) {
            // TODO: move this logic to a profile controller
            user = (User) extras.getSerializable("user");

            if (user.getEmail().equals(mUser.getEmail())) {
                sendRequestBtn.setVisibility(View.GONE);
                statisticsLayout.setVisibility(View.VISIBLE);
            } else {
                logoutBtn.setVisibility(View.GONE);


                if (user.getFollowers() != null) {
                    if (user.getFollowers().contains(mUser.getUid())) {
                        sendRequestBtn.setVisibility(View.GONE);
                        statisticsLayout.setVisibility(View.VISIBLE);
                    }
                }
            }

            Integer followerSize = 0;
            Integer followingSize = 0;

            if (user.getFollowers() != null) {
                followerSize = user.getFollowers().size();
            }

            if (user.getFollowing() != null) {
                followingSize = user.getFollowing().size();
            }

            db.collection("Users/" + user.getUserID() + "/VibeEvents")
                    .get()
                    .addOnSuccessListener((QuerySnapshot queryDocumentSnapshots) -> {
                        String vibeCountText = String.format(Locale.CANADA,"%d", queryDocumentSnapshots.size());
                        vibeCount.setText(vibeCountText);
                    });

            usernameTextView.setText(user.getUsername());
            emailTextView.setText(user.getEmail());

            followerCount.setText(followerSize.toString());
            followingCount.setText(followingSize.toString());
        }

        logoutBtn.setOnClickListener(view -> {
            mAuth.signOut();
            Intent restart = new Intent(this, MainActivity.class);
            // Finishes all background activities that may be running
            restart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(restart);
        });

        sendRequestBtn.setOnClickListener((view)
                -> requestController.sendFollowRequest(user));

        followersLayout.setOnClickListener(view -> {
            Intent followersIntent = new Intent(this, ConnectionsActivity.class);
            followersIntent.putExtra("user", user);
            startActivity(followersIntent);
        });

        followingLayout.setOnClickListener(view -> {
            Intent followingIntent = new Intent(this, ConnectionsActivity.class);
            followingIntent.putExtra("user", user);
            startActivity(followingIntent);
        });
    }
}
