package com.example.chargingmap;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.example.chargingmap.models.AddressInfo;
import com.example.chargingmap.models.ChargePoint;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.chargingmap.databinding.ActivityMapsBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapsActivity";
    private GoogleMap map;
    private ActivityMapsBinding binding;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    private List<ChargePoint> getPoints(LatLng point) {
        try {
            URL url = new URL("https://api.openchargemap.io/v3/poi/?output=json&key="
                    + getString(R.string.opencharge_key)
                    + "&latitude=" + point.latitude
                    + "&longitude=" + point.longitude + "&distance=5");

            String s = new BufferedReader(
                    new InputStreamReader(url.openConnection().getInputStream()))
                    .lines().collect(Collectors.joining("\n")
                    );

            JSONArray array = new JSONArray(s);
            ArrayList<ChargePoint> points = new ArrayList<ChargePoint>();

            for (int i = 0; i < array.length(); i++) {
                JSONObject p = array.getJSONObject(i).getJSONObject("AddressInfo");
                points.add(new ChargePoint(
                    new AddressInfo(
                            p.getString("Title"),
                            p.getDouble("Latitude"),
                            p.getDouble("Longitude")
                    )
                ));
            }

            return points;
        } catch (Exception e) {
            Log.e(TAG, "Error", e);
            return Collections.emptyList();
        }
    }

    private List<ChargePoint> getPointsRetrofit(LatLng point) {
        try {
            OpenchargeMapClient client = OpenchargeMapClient.build(getString(R.string.opencharge_key));
            return client.list(point.latitude, point.longitude, 5).execute().body();
        } catch (Exception e) {
            Log.e(TAG, "Error", e);
            return Collections.emptyList();
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION }, 1);
        }

        map.setMyLocationEnabled(
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        );

        map.setOnCameraMoveListener(() -> {
            Log.i(TAG, "Move");
        });

        map.setOnMapClickListener(clickPoint -> {
            Log.i(TAG, "Map clicked");
            executor.submit(() -> {
                List<ChargePoint> chargers = getPointsRetrofit(clickPoint);

                MapsActivity.this.runOnUiThread(() -> {
                    Log.i(TAG, "Found " + chargers.size() + " chargers");
                    for (ChargePoint c: chargers) {
                        Marker m = map.addMarker(new MarkerOptions()
                                .position(c.getAddressInfo().getPoint())
                                .title(c.getAddressInfo().getTitle())
                                .snippet("Charging station")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.charger))
                        );

                        m.setTag(c);
                    }
                });
            });
        });

        map.setOnMarkerClickListener(marker -> {
            marker.showInfoWindow();
            return false; // event not consumed
        });

        map.setOnInfoWindowClickListener(marker -> {
            marker.hideInfoWindow();
        });
    }
}