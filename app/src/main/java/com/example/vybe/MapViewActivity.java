package com.example.vybe;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.example.vybe.MapFragment;

import static com.example.vybe.R.id.map_view_fragment;
import static com.example.vybe.util.Constants.MAPVIEW_BUNDLE_KEY;
/**
 * Placeholder activity for where the map fragment is displayed.
 * In the future, this activity will allow for switching between
 * a user's personal vibe event history or their social vibe history
 */
public class MapViewActivity extends AppCompatActivity {

    private static final String TAG = "MapViewActivity";

    private MapFragment mapViewFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        mapViewFragment = (MapFragment) getSupportFragmentManager().findFragmentById(map_view_fragment);

        //inflateMapFragment(R.id.map_container);
        mapViewFragment.addVibeLocations();


        Log.d(TAG, "onCreate: showing map");
    }

}
